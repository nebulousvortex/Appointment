package ru.sber.appointment.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.sber.appointment.model.Doctor;
import ru.sber.appointment.model.User;
import ru.sber.appointment.service.AuthServiceImpl;
import ru.sber.appointment.service.DoctorServiceImpl;
import ru.sber.appointment.service.ScheduleServiceImpl;
import ru.sber.appointment.service.UserServiceImpl;
import ru.sber.appointment.utils.Validator;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@RestController
@RequestMapping("api/v1/admin/{username}")
public class AdminController {

    @Autowired
    UserServiceImpl userService;
    @Autowired
    Validator validator;
    @Autowired
    AuthServiceImpl authService;

    @Autowired
    ScheduleServiceImpl scheduleService;
    @Autowired
    DoctorServiceImpl doctorService;

    @DeleteMapping("delete/user")
    public ResponseEntity<?> deleteUser(@NotNull @PathVariable String username, @RequestBody User user, BindingResult bindingResult){
        if (bindingResult.hasErrors()) {
            return validator.getErrorList(bindingResult);
        } else {
            userService.deleteUser(user);
            return ResponseEntity.ok("OK");
        }
    }

    @DeleteMapping("delete/doctor")
    public ResponseEntity<?> deleteDoctor(@NotNull @PathVariable String username, @Valid @RequestBody Doctor doctor, BindingResult bindingResult){
        if (bindingResult.hasErrors()) {
            return validator.getErrorList(bindingResult);
        } else {
            doctorService.deleteDoctor(doctor);
            return ResponseEntity.ok("OK");
        }
    }

    @PostMapping("post/doctor")
    public ResponseEntity<?> saveDoctor(@NotNull @PathVariable String username, @Valid @RequestBody Doctor doctor , BindingResult bindingResult){
        if (bindingResult.hasErrors()) {
            return validator.getErrorList(bindingResult);
        } else {
            doctorService.saveDoctor(doctor);
            return ResponseEntity.ok("OK");
        }
    }

    @PostMapping("post/schedule/bulk")
    public ResponseEntity<?> saveScheduleForWeek(@NotNull @PathVariable String username, @Valid @RequestBody Doctor doctor , BindingResult bindingResult){
        if (bindingResult.hasErrors()) {
            return validator.getErrorList(bindingResult);
        } else {
            scheduleService.saveScheduleForWeek(doctor);
            return ResponseEntity.ok("OK");
        }
    }

    @GetMapping("/get")
    public ResponseEntity<?> getAdmin(@PathVariable String username){
        return ResponseEntity.ok("OK");
    }
}
