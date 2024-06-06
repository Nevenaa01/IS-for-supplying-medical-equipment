package com.example.ISA2023.back.user;

import com.example.ISA2023.back.dtos.JwtAuthenticationRequest;
import com.example.ISA2023.back.dtos.UserTokenState;
import com.example.ISA2023.back.models.Company;
import com.example.ISA2023.back.models.ReservedDate;
import com.example.ISA2023.back.models.irepositories.IReservedDateRepository;
import com.example.ISA2023.back.security.JwtUtils;
import com.example.ISA2023.back.models.irepositories.CompanyRepository;
import org.modelmapper.ModelMapper;
import org.modelmapper.internal.bytebuddy.implementation.auxiliary.AuxiliaryType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.Optional;
import java.util.List;

@Service
public class UserService {
    private final IUserRepository userRepository;
    private final CompanyRepository companyRepository;
    private final IReservedDateRepository reservedDateRepository;
    @Autowired
    private JavaMailSender javaMailSender;
    @Autowired
    private JwtUtils jwtUtils;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired

    private PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(IUserRepository userRepository, CompanyRepository companyRepository, IReservedDateRepository reservedDateRepository) {
        this.userRepository = userRepository;
        this.companyRepository = companyRepository;
        this.reservedDateRepository = reservedDateRepository;
    }

    public void sendVerificationEmail(User user) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(user.getEmail());
        message.setSubject("Account Verification");
        message.setText("Dear " + user.getFirst_name() + ",\n\n"
                + "Thank you for joining us. Please click on the following link to verify your account: "
                + "http://localhost:8090/api/v1/user/verify/" + user.getId());

        javaMailSender.send(message);
    }

    public User findById(Long id){
        return userRepository.findById(id).orElse(null);
    }
    public User save(User user){
        return userRepository.save(user);
    }
    public User update(Long id,User updatedUser){
        Optional<User> optionalUser= userRepository.findById(id);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            updatedUser.setId(id);
            //updatedUser.setPassword(passwordEncoder.encode(updatedUser.getPassword()));
            //updatedUser.setVerified(true);
            modelMapper.map(updatedUser, user);
            userRepository.save(user);
            return user;
        }

        return null;
    }
    public User updatePassword(Long id,User updatedUser){
        Optional<User> optionalUser= userRepository.findById(id);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            updatedUser.setId(id);
            updatedUser.setPassword(passwordEncoder.encode(updatedUser.getPassword()));
            updatedUser.setVerified(true);
            modelMapper.map(updatedUser, user);
            userRepository.save(user);
            return user;
        }

        return null;
    }
    public User findByEmail(String email){
        return userRepository.findByEmail(email);
    }

    public List<User> getCompanyAdministrators(){
        return userRepository.getCompanyAdministrators();
    }
    
    public List<User> getCompanyAdministratorsByCompanyId(long companyId){
        var company = companyRepository.findById(companyId);
        return getCompanyAdministrators()
                .stream()
                .filter(c -> company.get().getAdministratorId().contains(c.getId())).toList();
    }
    public User getLastUser()
    {
        return userRepository.getLastUser();
    }

    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public UserTokenState login(JwtAuthenticationRequest loginDto) {

        Optional<User> userOpt = userRepository.findByUsername(loginDto.getUsername());
        if (userOpt.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "message: Incorrect credentials!");
        }
        if(!passwordEncoder.matches(loginDto.getPassword(), userOpt.get().getPassword())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "message: Incorrect password!");
        }

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);
        User user = (User) authentication.getPrincipal();

        UserTokenState tokenDTO = new UserTokenState();
        tokenDTO.setAccessToken(jwt);
        tokenDTO.setExpiresIn(10000000L);
        tokenDTO.setUsername(loginDto.getUsername());

        return tokenDTO;
    }
    public User getByUserId(Long id)
    {
        return userRepository.getUserById(id);
    }

    public User updateCompanyAdmin(Long id,User user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        Optional<User> optionalUser = userRepository.findById(id);
        if(optionalUser.isPresent()) {
            var existingUser = optionalUser.get();
            modelMapper.map(user, existingUser);
            userRepository.save(existingUser);
            return existingUser;
        }

        return null;
    }
    public boolean isPasswordChanges(String username)
    {
        Optional<User> userOpt = userRepository.findByUsername(username);
        if (userOpt.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "message: Incorrect credentials!");
        }
        if(passwordEncoder.matches("12345", userOpt.get().getPassword())){
            return false;
            //throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "message: Incorrect password!");
        }
        return true;
    }

    public List<User> getUsersWithOrdersByComapny(Long companyId){
        List<User> users = new ArrayList<User>();

        List<ReservedDate> reservedDates = reservedDateRepository.findAll()
                        .stream()
                        .filter(reservedDate -> reservedDate.getCompanyId().equals(companyId)).toList();

        for(var date : reservedDates){
            var user = userRepository.getUserById(date.getUserId());

            if(!users.contains(user))
                users.add(user);
        }

        return users;
    }
    public void resetPenalty(){
        List<User> users=userRepository.findAll();
        for (var user:users) {
            user.setPenaltyPoints(0);
        }
        userRepository.saveAll(users);
    }

}
