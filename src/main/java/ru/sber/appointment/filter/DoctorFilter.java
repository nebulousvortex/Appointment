package ru.sber.appointment.filter;

import org.springframework.stereotype.Component;
import ru.sber.appointment.model.Doctor;
import ru.sber.appointment.service.DoctorServiceImpl;

import java.util.List;

@Component
public class DoctorFilter {
    public List<Doctor> filterDoctor(DoctorServiceImpl doctorService, Doctor doctor){
        List<Doctor> resultDoctorList = doctorService.findAllDoctors();
        System.out.println(resultDoctorList);
        if(doctor.getUser() != null) {
            if (doctor.getUser().getFirstName() != null) {
                System.out.println(doctor.getUser().getFirstName());
                resultDoctorList.retainAll(doctorService.findByFirstName(doctor.getUser().getFirstName()));
            }
            if (doctor.getUser().getLastName() != null) {
                System.out.println(doctor.getUser().getLastName());
                resultDoctorList.retainAll(doctorService.findByLastName(doctor.getUser().getLastName()));
            }
            if (doctor.getSpecialization() != null) {
                System.out.println(doctor.getSpecialization());
                resultDoctorList.retainAll(doctorService.findBySpecialization(doctor.getSpecialization()));
            }
        }
        return resultDoctorList;
    }
}
