package ru.sber.appointment.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.sber.appointment.model.User;
import ru.sber.appointment.service.UserService;

@RestController
@RequestMapping("api/v1/admin")
public class AdminController {

    @Autowired
    UserService userService;

    @DeleteMapping("deleteUser")
    public void deleteUser(@RequestBody User user){
        userService.deleteUser(user);
    }

}
