package com.example.ISA2023.back.user;

import ch.qos.logback.core.net.SyslogOutputStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
@CrossOrigin
@RestController
@RequestMapping(path="api/v1/user")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/add")
    public User addUnverified(@RequestBody User user){
        user.setVerified(false);
        User savedUser = userService.save(user);
        userService.sendVerificationEmail(savedUser);

        return savedUser;
    }
    @GetMapping("/verify/{id}")
    public User verify(@PathVariable Long id){
        User user = userService.findById(id);
        user.setVerified(true);
        return userService.update(id, user);
    }
    @GetMapping("/email/{email}")
    public User getByEmail(@PathVariable String email){
        var user = userService.findByEmail(email);
        return user;
    }
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @GetMapping("/{id}")
    public User findById(@PathVariable Long id){
        return userService.findById(id);
    }
    @PutMapping("/{id}")
    public User update(@PathVariable Long id, @RequestBody User updatedUser){
        return userService.update(id,updatedUser);
    }
    @PutMapping("/updatepassword/{id}")
    public User updatePassword(@PathVariable Long id, @RequestBody User updatedUser){
        return userService.updatePassword(id,updatedUser);
    }
    @GetMapping("/companyAdministratorsByCompanyId/{id}")
    public List<User> getCompanyAdministratorsByCompanyId(@PathVariable long id){
        return userService.getCompanyAdministratorsByCompanyId(id);
    }
    @GetMapping("/getlastuser")
    public User getLastUser()
    {
        return userService.getLastUser();
    }
    @GetMapping("/username/{username}")
    public User getLoggedUser(@PathVariable String username){
        return userService.findByUsername(username)
                          .orElseThrow();
    }

    @PutMapping("updateCompanyAdmin/{id}")
    @PreAuthorize("hasAuthority('ROLL_COMPANY_ADMIN')")
    public User updateCompanyAdmin(@PathVariable long id, @RequestBody User user){
        return userService.updateCompanyAdmin(id, user);
    }
    @PutMapping("/ispasschanged/{username}")
    public Boolean isPasswordChanged(@PathVariable String username)
    {
        return userService.isPasswordChanges(username);
    }

    @GetMapping("usersWithOrders/{id}")
    @PreAuthorize("hasAuthority('ROLL_COMPANY_ADMIN')")
    public List<User> GetUsersWithOrdersByComapny(@PathVariable Long id){
        return userService.getUsersWithOrdersByComapny(id);
    }
}
