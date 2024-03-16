package ru.sber.appointment.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.sber.appointment.model.Doctor;
import ru.sber.appointment.model.Schedule;
import ru.sber.appointment.service.ScheduleServiceImpl;

import java.util.List;

@RestController
@RequestMapping("api/v1/schedule")
public class ScheduleController {

    @Autowired
    ScheduleServiceImpl scheduleService;

    @GetMapping("/get")
    public List<Schedule> getSchedule(){
        return scheduleService.findAllSchedule();
    }

    @PostMapping("/post")
    public void saveSchedule(@RequestBody(required = false) Schedule schedule){
        scheduleService.saveSchedule(schedule);
    }

    @PostMapping("/postForWeek")
    public void saveScheduleForWeek(@RequestBody Doctor doctor){
        scheduleService.saveScheduleForWeek(doctor);
    }

    @PutMapping("/updateSchedule")
    public void updateDayType(@RequestBody Schedule schedule){
        scheduleService.updateSchedule(schedule);
    }
}

