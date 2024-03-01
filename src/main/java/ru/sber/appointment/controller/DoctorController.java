package ru.sber.appointment.controller;

import org.springframework.web.bind.annotation.*;
import ru.sber.appointment.filter.DoctorFilter;
import ru.sber.appointment.model.Doctor;
import ru.sber.appointment.service.DoctorServiceImpl;

import java.util.List;

@RestController
@RequestMapping("/api/v1/doctors")
public class DoctorController {

    private final DoctorServiceImpl doctorService;
    private final DoctorFilter filter;
    public DoctorController(DoctorServiceImpl doctorService, DoctorFilter filter) {
        this.doctorService = doctorService;
        this.filter = filter;
    }

    @GetMapping("/get")
    public List<Doctor> findDoctors(@RequestBody(required = false) Doctor doctor){
        if (doctor != null) {
            return filter.filterDoctor(doctorService, doctor);
        }
        return doctorService.findAllDoctors();
    }

    @PostMapping("/post")
    public void saveDoctor(@RequestBody Doctor doctor){
        doctorService.saveDoctor(doctor);
    }

    @DeleteMapping("/delete")
    public void deleteDoctor(@RequestBody Doctor doctor){ doctorService.deleteDoctor(doctor); }

    @PutMapping("/put")
    public void updateDoctor(@RequestBody Doctor doctor){
        doctorService.updateDoctor(doctor);
    }

}