package ru.sber.appointment.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.sber.appointment.model.User;
import ru.sber.appointment.service.UserService;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/registration")
public class RegistrationController {

    @Autowired
    UserService userService;
    @PostMapping
    public ResponseEntity<?> addUser(@Valid @RequestBody User user, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<String> errorMessages = bindingResult.getFieldErrors().stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .collect(Collectors.toList());
            return ResponseEntity.ok(errorMessages);
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
