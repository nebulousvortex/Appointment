package ru.sber.appointment.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.sber.appointment.model.User;
import ru.sber.appointment.service.UserService;
import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping("/api/v1/registration")
public class RegistrationController {

    @Autowired
    private UserService userService;
    @PostMapping
    public ResponseEntity addUser(@RequestBody User user) {
        if (!user.getPassword().equals(user.getPasswordConfirm())) {
            return ResponseEntity.ok("Пароли не совпадают");
        }
        if (!userService.saveUser(user)) {
            return ResponseEntity.ok("Пользователь с таким именем уже существует");
        }
        return ResponseEntity.ok("Пользователь успешно зарегистрирован");
    }
}
