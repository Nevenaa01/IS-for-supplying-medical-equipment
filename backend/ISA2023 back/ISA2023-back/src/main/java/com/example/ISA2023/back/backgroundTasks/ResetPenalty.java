package com.example.ISA2023.back.backgroundTasks;

import com.example.ISA2023.back.user.IUserRepository;
import com.example.ISA2023.back.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import java.time.LocalDate;
@Component
public class ResetPenalty {

    private final UserService userService;
    @Autowired
    public ResetPenalty(UserService userService) {
        this.userService = userService;
    }

    @Scheduled(fixedRate = 86400000)
    public void myTask() {
        LocalDate currentDate = LocalDate.now();
        if (currentDate.getDayOfMonth() == 1) {
            userService.resetPenalty();
        }else{
            System.out.println("Its not first day of the month");
        }


    }
}
