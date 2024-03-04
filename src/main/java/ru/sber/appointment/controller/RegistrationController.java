package ru.sber.appointment.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.sber.appointment.model.User;
import ru.sber.appointment.service.UserService;

@RestController
@RequestMapping("/api/v1/registration")
public class RegistrationController {

    @Autowired
    private UserService userService;

    @PostMapping
    public String addUser(@RequestBody User user) {
        if (!user.getPassword().equals(user.getPasswordConfirm())) {
            return "Пароли не совпадают";
        }
        if (!userService.saveUser(user)) {
            return "Пользователь с таким именем уже существует";
        }
        return "Пользователь успешно зарегистрирован";
    }
}
