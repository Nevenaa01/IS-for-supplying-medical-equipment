package com.example.ISA2023.back.controllers;

import com.example.ISA2023.back.dtos.ContractDto;
import com.example.ISA2023.back.models.*;
import com.example.ISA2023.back.services.CompanyService;
import com.example.ISA2023.back.services.ContractService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@CrossOrigin
@RestController
@Component
@RequestMapping(path = "api/v1/contract")
public class ContractController {
    private final ContractService contractService;
    private String message;
    @Autowired
    private RabbitTemplate template;
    @Autowired
    public ContractController(ContractService contractService) {
        this.contractService = contractService;
    }

    @GetMapping
    public List<Contract> getAll(){return contractService.getContracts();}
    @PostMapping
    public Contract createContarct(@RequestBody Contract contract){

        while(Objects.isNull(message) == true){
            System.out.println("null message");
        }

        if(message.equals("Contract")) {
            message = null;
            return contractService.create(contract);
        }
        else{
            message = null;
            contract.setId(0L);
            return contract;
        }
    }

    @RabbitListener(queues = MQConfig.QUEUE)
    public void listener(CustomMessage message){
        this.message = message.getMessage();

        System.out.println(this.message);
    }

    @GetMapping("/getByUserId/{userId}")
    public Contract getContarctByUserId(@PathVariable Long userId){return contractService.getByUserId(userId);}
    @GetMapping("/getByCompanyId/{companyId}")
    public List<ContractDto> getContarctByCompanyId(@PathVariable Long companyId){return contractService.getByCompanyId(companyId);}
    @DeleteMapping("/{userId}")
    public void deleteByUserId(@PathVariable Long userId){contractService.deleteByUserId(userId);}

    @PostMapping("/message")
    public String sendMessage(@RequestBody CustomMessage message){
        message.setMessageId(UUID.randomUUID().toString());
        message.setMessageDate(new Date());
        template.convertAndSend(MQConfig.QUEUE_SENDING,
                message);

        return "Message sent";
    }

}
