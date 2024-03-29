package ru.sber.appointment.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.sber.appointment.filter.DoctorFilter;
import ru.sber.appointment.model.Doctor;
import ru.sber.appointment.service.DoctorServiceImpl;

import java.util.List;

@RestController
@RequestMapping("/api/v1/doctor")
public class DoctorController {

    @Autowired
    DoctorServiceImpl doctorService;
    @Autowired
    DoctorFilter filter;

    @GetMapping("/get/doctors")
    public List<Doctor> findDoctors(@RequestBody(required = false) Doctor doctor){
        if (doctor != null) {
            return filter.filterDoctor(doctorService, doctor);
        }
        return doctorService.findAllDoctors();
    }

    @PostMapping("/get/doctors")
    public List<Doctor> filteredDoctors(@RequestBody(required = false) Doctor doctor){
        if (doctor != null) {
            return filter.filterDoctor(doctorService, doctor);
        }
        return doctorService.findAllDoctors();
    }


    @PutMapping("/put/doctors")
    @PreAuthorize("denyAll")
    public void updateDoctor(@RequestBody Doctor doctor){
        doctorService.updateDoctor(doctor);
    }


}
