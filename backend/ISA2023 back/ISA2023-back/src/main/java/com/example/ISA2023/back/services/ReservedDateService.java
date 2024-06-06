package com.example.ISA2023.back.services;

import com.example.ISA2023.back.dtos.TrackingOrderDto;
import com.example.ISA2023.back.dtos.ReservedDatesDto;
import com.example.ISA2023.back.dtos.ReservedDatesForCalendarDto;
import com.example.ISA2023.back.models.Company;
import com.example.ISA2023.back.models.Equipment;
import com.example.ISA2023.back.models.QRCodeStatus;
import com.example.ISA2023.back.models.ReservedDate;
import com.example.ISA2023.back.models.irepositories.CompanyRepository;
import com.example.ISA2023.back.models.irepositories.IEquipmentRepository;
import com.example.ISA2023.back.models.irepositories.IReservedDateRepository;
import com.example.ISA2023.back.user.IUserRepository;
import com.example.ISA2023.back.user.User;
import com.example.ISA2023.back.utils.QRCodeGenerator;
import com.google.zxing.NotFoundException;
import com.google.zxing.WriterException;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.persistence.LockModeType;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.time.Clock;
import java.time.LocalDate;
import java.util.*;

@Service
public class ReservedDateService {
    private final IReservedDateRepository reservedDateRepository;
    private final IEquipmentRepository equipmentRepository;
    private final CompanyService companyService;
    private final CompanyRepository companyRepository;
    private final IUserRepository userRepository;
    @Autowired
    private JavaMailSender javaMailSender;
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    public ReservedDateService(IReservedDateRepository reservedDateRepository,IEquipmentRepository equipmentRepository,CompanyRepository companyRepository, IUserRepository userRepository, CompanyService companyService) {
        this.reservedDateRepository = reservedDateRepository;
        this.equipmentRepository=equipmentRepository;
        this.companyRepository=companyRepository;
        this.userRepository=userRepository;
        this.companyService=companyService;
    }

    public List<ReservedDate> getAll() { return reservedDateRepository.findAll();}
    @Transactional(propagation = Propagation.REQUIRED)
    public ReservedDate create(ReservedDate reservedDate){
        for (var equipId:reservedDate.getEquipments()) {
            if(equipmentRepository.GetEquipmentByIdTransactional(equipId).getQuantity()<=0){
                return null;
            }
        }

        List<ReservedDate>dates=reservedDateRepository.findAllByCompanyIdTransactional(reservedDate.getCompanyId());
        Long start1=reservedDate.getDateTimeInMS();
        Long end1=start1+reservedDate.getDuration()*60000;

        for (var date:dates) {
            Long start2=date.getDateTimeInMS();
            Long end2=start2+date.getDuration()*60000;
            if((start1 <= end2) && (end1 >= start2)){
                return  null;
            }
        }
        reservedDate.setQrCodeStatus(QRCodeStatus.NEW);
        return reservedDateRepository.save(reservedDate);
    }

    public List<TrackingOrderDto> getReservedDatesByCompanyId(Long id){
        List<ReservedDate> reservedDatesOfCompany = reservedDateRepository.findAll()
                .stream()
                .filter(equipment -> equipment.getCompanyId().equals((id))).toList();

        List<TrackingOrderDto> trackingOrders = new ArrayList<TrackingOrderDto>();

        for(var date : reservedDatesOfCompany){
            var companyAdmin = userRepository.findById(date.getCompanyAdminId());
            var user = userRepository.findById(date.getUserId());

            List<Long> equipmentIds = new ArrayList<Long>();
            List<String> equipmentNames = new ArrayList<String>();

            for(var equipment : date.getEquipments()){
                var eq = equipmentRepository.findById(equipment);
                equipmentIds.add(equipment);
                equipmentNames.add(eq.get().getName() + " - " + eq.get().getType().toString());
            }

            TrackingOrderDto order = new TrackingOrderDto(date.getId(),
                    date.getUserId(),
                    user.get().getFirst_name() + " " + user.get().getLast_name(),
                    companyAdmin.get().getFirst_name() + " " + companyAdmin.get().getLast_name(),
                    date.getPickedUp(),
                    date.getDateTimeInMS(),
                    date.getDuration(),
                    equipmentNames,
                    equipmentIds);

            trackingOrders.add(order);
        }
        return trackingOrders;
    }

    public ReservedDate updatePickedUpStatus(Long id, Boolean status){
        Optional<ReservedDate> optionalReservedDate = reservedDateRepository.findById(id);
        if(optionalReservedDate.isPresent()){
            var existingReservedDate = optionalReservedDate.get();
            existingReservedDate.setPickedUp(status);
            if(existingReservedDate.getPickedUp())
                existingReservedDate.setQrCodeStatus(QRCodeStatus.ACCEPTED);
            else
                existingReservedDate.setQrCodeStatus(QRCodeStatus.DENIED);
            reservedDateRepository.save(existingReservedDate);

            String equipmentNames = "";

            List<Long> equipments = existingReservedDate.getEquipments();
            for(var equipment : equipments){
                var eq = equipmentRepository.findById(equipment);

                equipmentNames += eq.get().getName() + " - " + eq.get().getType().toString() + "\n";
            }

            sendEquipmentsPickedUpEmail(userRepository.getUserById(existingReservedDate.getUserId()), equipmentNames);
            return existingReservedDate;
        }

        return null;
    }

    public void sendEquipmentsPickedUpEmail(User user,String equimentNames) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(user.getEmail());
        message.setSubject("Equipments pickup");
        message.setText("Dear " + user.getFirst_name() + ",\n\n"
                + "Thank you ordering: \n"
                + equimentNames
                + " \nfrom our company.");

        javaMailSender.send(message);
    }
    public List<Equipment> findEquipmentByReservationDateId(Long id){
        List<Long> equipmentIds=reservedDateRepository.findById(id).get().getEquipments();
        return GetAllEquipments(equipmentIds);
    }

    public List<Equipment> GetAllEquipments(List<Long> equipmentsId){
        List<Equipment> equipments=new ArrayList<>();
        for (Long id:equipmentsId ) {
            equipments.add(equipmentRepository.findById(id).get());
        }
        return equipments;
    }
    public void sendConfirmationEmail(String email,ReservedDate order){
        String medium="http://localhost:3000/qrcode-equipment/"+order.getId();
        byte[] image = new byte[0];
        try {
            image = QRCodeGenerator.getQRCodeImage(medium,250,250);
        } catch (WriterException | IOException e) {
            e.printStackTrace();
        }
        //String qrcode = Base64.getEncoder().encodeToString(image);
        try {

            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setTo(email);
            helper.setSubject("Confirmation");


            StringBuilder body= new StringBuilder("Dear user,\nwe are sending you your order:\n");
            List<Equipment> equipmentList=GetAllEquipments(order.getEquipments());
            int i=1;
            for (Equipment eqipment:equipmentList) {
                body.append("\n").append(i).append(".Name: ").append(eqipment.getName()).append("\nDescription: ").append(eqipment.getDescription()).append("\nType: ").append(eqipment.getType()).append("\n");
                i++;
            }
            body.append("\n\nYour confirmation is in attached file.");
            helper.setText(body.toString());


            ByteArrayResource imageResource = new ByteArrayResource(image);
            helper.addAttachment("image.png", imageResource);
            order.setLinkToOrder(medium);
            reservedDateRepository.save(order);
            javaMailSender.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }

    }
    public ReservedDate FindById(Long id){
        return reservedDateRepository.findById(id).get();
    }

    public List<TrackingOrderDto> getTrackingsByEquipmentId(Long id){
        var reservedDates = reservedDateRepository.findAll()
                .stream()
                .filter(d -> d.getEquipments()
                        .stream()
                        .anyMatch(eq -> eq.equals(id))).toList();

        List<TrackingOrderDto> trackingOrders = new ArrayList<TrackingOrderDto>();

        for(ReservedDate date : reservedDates){
            var companyAdmin = userRepository.findById(date.getCompanyAdminId());
            var user = userRepository.findById(date.getUserId());

            List<Long> equipmentIds = new ArrayList<Long>();
            List<String> equipmentNames = new ArrayList<String>();

            for(var equipment : date.getEquipments()){
                var eq = equipmentRepository.findById(equipment);
                equipmentIds.add(equipment);
                equipmentNames.add(eq.get().getName() + " - " + eq.get().getType().toString());
            }

            TrackingOrderDto order = new TrackingOrderDto(date.getId(),
                    date.getUserId(),
                    user.get().getFirst_name() + " " + user.get().getLast_name(),
                    companyAdmin.isPresent() ? companyAdmin.get().getFirst_name() + " " + companyAdmin.get().getLast_name() : "",
                    date.getPickedUp(),
                    date.getDateTimeInMS(),
                    date.getDuration(),
                    equipmentNames,
                    equipmentIds);

            trackingOrders.add(order);
        }

        return trackingOrders;
    }
    public List<ReservedDatesDto> getReservedDatesByUserId(Long userId,boolean flag) {
        List<ReservedDate> reservedList=reservedDateRepository.findAllByUserId(userId);
        List<ReservedDatesDto> datesDto=new ArrayList<>();
        for (var item:reservedList) {
            if(item.getPickedUp()==flag) {
                Company company = companyRepository.findById(item.getCompanyId()).get();
                datesDto.add(new ReservedDatesDto(item.getId(),item.getDuration(), item.getDateTimeInMS(), company.getName(),item.getCompanyAdminId(),item.getLinkToOrder(),item.getQrCodeStatus()));
            }
        }
        return datesDto;

    }

    public void DeleteReservedDate(Long reservationId){
        reservedDateRepository.deleteById(reservationId);
    }

    public List<ReservedDatesForCalendarDto>GetByCompany(long companyId)
    {
        List<ReservedDatesForCalendarDto>allDates= new ArrayList<>(companyService.findAllPredefinedDatesByCompanyId(companyId));
        var reservedDates=reservedDateRepository.GetByCompany(companyId);
        for ( var r :reservedDates) {
            ReservedDatesForCalendarDto rd=new ReservedDatesForCalendarDto();
            rd.setId(r.getId());
            rd.setDateTimeInMS(r.getDateTimeInMS());
            rd.setDuration(r.getDuration());
            rd.setUserName(userRepository.getUserById(r.getUserId()).getFirst_name());
            rd.setUserSurname(userRepository.getUserById(r.getUserId()).getLast_name());
            List<String> equipments=new ArrayList<>();
            for (var e:r.getEquipments()) {
                Equipment eq=equipmentRepository.findById(e).get();
                equipments.add(eq.getName());
            }
            rd.setEquipments(equipments);
            allDates.add(rd);
        }
        Comparator<ReservedDatesForCalendarDto> comparator = Comparator.comparing(ReservedDatesForCalendarDto::getDateTimeInMS);
        allDates.sort(comparator);
        return allDates;
    }

    public List<ReservedDatesForCalendarDto>GetByCompanyByWeek(long companyId)
    {
        List<ReservedDatesForCalendarDto> allDates=GetByCompany(companyId);
        Long currentTime= new Date().getTime();
        Long week=currentTime+1000*60*60*24*7;
        List<ReservedDatesForCalendarDto> datesWeek=new ArrayList<>();
        for (var date:allDates) {
            if(date.getDateTimeInMS()<week && date.getDateTimeInMS()>currentTime)
                datesWeek.add(date);
        }
        Comparator<ReservedDatesForCalendarDto> comparator = Comparator.comparing(ReservedDatesForCalendarDto::getDateTimeInMS);
        datesWeek.sort(comparator);
        return datesWeek;
    }
    public List<ReservedDatesForCalendarDto>GetByCompanyByMonth(long companyId, int month, int year)
    {
        List<ReservedDatesForCalendarDto> allDates=GetByCompany(companyId);
        List<ReservedDatesForCalendarDto> datesMonth=new ArrayList<>();
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, 1);


        Date start= calendar.getTime();
        Date end;
        Integer niz[]={0,2,4,6,7,9,11};
        List<Integer> daniV=new ArrayList<Integer>(List.of(niz));
        if(month==1)
        {
            calendar.set(Calendar.YEAR, year);
            calendar.set(Calendar.MONTH, month);
            calendar.set(Calendar.DAY_OF_MONTH, 28);
            end=calendar.getTime();
        }
        else
            if(daniV.contains(month))
            {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, month);
                calendar.set(Calendar.DAY_OF_MONTH, 31);
                end=calendar.getTime();
            }

            else
            {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, month);
                calendar.set(Calendar.DAY_OF_MONTH, 30);
                end=calendar.getTime();
            }
        for (var date:allDates) {

            if(date.getDateTimeInMS()>=start.getTime() && date.getDateTimeInMS()<=end.getTime())
                datesMonth.add(date);
        }
        Comparator<ReservedDatesForCalendarDto> comparator = Comparator.comparing(ReservedDatesForCalendarDto::getDateTimeInMS);
        datesMonth.sort(comparator);
        return datesMonth;
    }
    public List<ReservedDatesForCalendarDto>GetByCompanyByYear(long companyId, int year)
    {
        List<ReservedDatesForCalendarDto> allDates=GetByCompany(companyId);
        List<ReservedDatesForCalendarDto> datesMonth=new ArrayList<>();
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, 0);
        calendar.set(Calendar.DAY_OF_MONTH, 1);


        Date start= calendar.getTime();
        //Date start=new Date(year,month,1);
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, 11);
        calendar.set(Calendar.DAY_OF_MONTH, 31);
        Date end=calendar.getTime();

        for (var date:allDates) {

            if(date.getDateTimeInMS()>=start.getTime() && date.getDateTimeInMS()<=end.getTime())
                datesMonth.add(date);
        }
        Comparator<ReservedDatesForCalendarDto> comparator = Comparator.comparing(ReservedDatesForCalendarDto::getDateTimeInMS);
        datesMonth.sort(comparator);
        return datesMonth;
    }

    public void DeleteById(Long id){
        reservedDateRepository.deleteById(id);
    }

    public String HandleQRCode(byte[] qrCode) throws NotFoundException, IOException {
        try
        {
            String text= QRCodeGenerator.decodeQR(qrCode);
            String [] separate=text.split("/");
            Long orderId=Long.parseLong(separate[separate.length-1]);
            if(!CheckIsScanned(orderId))
            {
                updatePickedUpStatus(orderId, true);
                return orderId.toString();
            }
        }
        catch(Exception e)
        {
            if(e.getMessage().contains("null"))
                return "Error";
            else
                return "Scanned";
        }
        return "Scanned";
    }
    public boolean CheckIsScanned(Long id)
    {
        ReservedDate rd=FindById(id);
        return rd.getQrCodeStatus()==QRCodeStatus.ACCEPTED || rd.getQrCodeStatus()==QRCodeStatus.DENIED;
    }
}
