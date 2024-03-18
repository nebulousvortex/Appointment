package ru.sber.appointment.filter;

import org.springframework.stereotype.Component;
import ru.sber.appointment.model.Doctor;
import ru.sber.appointment.service.DoctorServiceImpl;

import java.util.List;

@Component
public class DoctorFilter {
    public List<Doctor> filterDoctor(DoctorServiceImpl doctorService, Doctor doctor){
        List<Doctor> resultDoctorList = doctorService.findAllDoctors();
        if(doctor.getUser() != null) {
            if (doctor.getUser().getFirstName() != null && !doctor.getUser().getFirstName().equals("")) {
                resultDoctorList.retainAll(doctorService.findByFirstName(doctor.getUser().getFirstName()));
            }
            if (doctor.getUser().getLastName() != null && !doctor.getUser().getLastName().equals("")) {
                resultDoctorList.retainAll(doctorService.findByLastName(doctor.getUser().getLastName()));
            }
            if (doctor.getSpecialization() != null && !doctor.getSpecialization().equals("")) {
                resultDoctorList.retainAll(doctorService.findBySpecialization(doctor.getSpecialization()));
            }
        }
        return resultDoctorList;
    }
}
