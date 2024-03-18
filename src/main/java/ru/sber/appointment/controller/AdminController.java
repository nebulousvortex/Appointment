package ru.sber.appointment.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.sber.appointment.model.Doctor;
import ru.sber.appointment.model.User;
import ru.sber.appointment.service.AuthServiceImpl;
import ru.sber.appointment.service.DoctorServiceImpl;
import ru.sber.appointment.service.ScheduleServiceImpl;
import ru.sber.appointment.service.UserServiceImpl;

import javax.servlet.ServletResponse;
import java.io.IOException;

@RestController
@RequestMapping("api/v1/admin/{username}")
public class AdminController {

    @Autowired
    UserServiceImpl userService;

    @Autowired
    AuthServiceImpl authService;

    @Autowired
    ScheduleServiceImpl scheduleService;
    @Autowired
    DoctorServiceImpl doctorService;

    @DeleteMapping("delete/user")
    public ResponseEntity<?> deleteUser(@PathVariable String username, @RequestBody User user){
        if (authService.getAuthoritiesAdmin(username)){
            userService.deleteUser(user);
            return ResponseEntity.ok("OK");
        }
        return null;
    }

    @DeleteMapping("delete/doctor")
    public ResponseEntity<?> deleteDoctor(@PathVariable String username, @RequestBody Doctor doctor){
        if (authService.getAuthoritiesAdmin(username)){
            doctorService.deleteDoctor(doctor);
            return ResponseEntity.ok("OK");
        }
        return null;
    }

    @PostMapping("post/doctor")
    public ResponseEntity<?> saveDoctor(@PathVariable String username, @RequestBody Doctor doctor){
        if (authService.getAuthoritiesAdmin(username)){
            doctorService.saveDoctor(doctor);
            return ResponseEntity.ok("OK");
        }
        return null;
    }

    @PostMapping("post/schedule/bulk")
    public ResponseEntity<?> saveScheduleForWeek(@PathVariable String username, @RequestBody Doctor doctor){
        if (authService.getAuthoritiesAdmin(username)){
            scheduleService.saveScheduleForWeek(doctor);
            return ResponseEntity.ok("OK");
        }
        return null;
    }

    @GetMapping("/get")
    public ResponseEntity<?> getAdmin(@PathVariable String username){
        if (authService.getAuthoritiesAdmin(username)){
            return ResponseEntity.ok("OK");
        }
        return null;
    }

}
