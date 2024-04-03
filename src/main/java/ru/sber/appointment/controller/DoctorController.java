package ru.sber.appointment.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.sber.appointment.dto.DoctorDTO;
import ru.sber.appointment.utils.DTOCreator;
import ru.sber.appointment.utils.DoctorSelector;
import ru.sber.appointment.model.Doctor;
import ru.sber.appointment.service.DoctorServiceImpl;

import java.util.List;

@RestController
@RequestMapping("/api/v1/doctor")
public class DoctorController {

    @Autowired
    DoctorServiceImpl doctorService;
    @Autowired
    DoctorSelector filter;
    @Autowired
    DTOCreator dtoCreator;

    @PostMapping("/get/doctors")
    public List<DoctorDTO> filteredDoctors(@RequestBody(required = false) Doctor doctor){
        if (doctor != null) {
            List<Doctor> doctors =  filter.filterDoctor(doctorService, doctor);
            return dtoCreator.makeDoctorDTOList(doctors);
        }
        return dtoCreator.makeDoctorDTOList(doctorService.findAllDoctors());
    }


    @PutMapping("/put/doctors")
    @PreAuthorize("denyAll")
    public void updateDoctor(@RequestBody Doctor doctor){
        doctorService.updateDoctor(doctor);
    }


}
