package ru.sber.appointment.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.sber.appointment.model.User;
import ru.sber.appointment.service.UserServiceImpl;
import ru.sber.appointment.utils.Validator;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/registration")
public class RegistrationController {

    @Autowired
    UserServiceImpl userService;
    @Autowired
    Validator validator;
    @PostMapping
    public ResponseEntity<?> addUser(@Valid @RequestBody User user, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return validator.getErrorList(bindingResult);
        } else {
            if (!user.getPassword().equals(user.getPasswordConfirm())) {
                return ResponseEntity.ok("Пароли не совпадают");
            }
            if (!userService.saveUser(user)) {
                return ResponseEntity.ok("Пользователь с таким именем уже существует");
            }
            return ResponseEntity.ok(null);
        }
    }
}
