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
        userService.deleteUser(user);
        return ResponseEntity.ok("OK");
    }

    @DeleteMapping("delete/doctor")
    public ResponseEntity<?> deleteDoctor(@PathVariable String username, @RequestBody Doctor doctor){
        doctorService.deleteDoctor(doctor);
        return ResponseEntity.ok("OK");
    }

    @PostMapping("post/doctor")
    public ResponseEntity<?> saveDoctor(@PathVariable String username, @RequestBody Doctor doctor){
        doctorService.saveDoctor(doctor);
        return ResponseEntity.ok("OK");
    }

    @PostMapping("post/schedule/bulk")
    public ResponseEntity<?> saveScheduleForWeek(@PathVariable String username, @RequestBody Doctor doctor){
        scheduleService.saveScheduleForWeek(doctor);
        return ResponseEntity.ok("OK");
    }

    @GetMapping("/get")
    public ResponseEntity<?> getAdmin(@PathVariable String username){
        return ResponseEntity.ok("OK");
    }
}
