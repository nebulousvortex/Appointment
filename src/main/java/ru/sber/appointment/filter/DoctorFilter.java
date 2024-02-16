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
        if (doctor.getFirstName() != null){
            System.out.println(doctor.getFirstName());
            resultDoctorList.retainAll(doctorService.findByFirstName(doctor.getFirstName()));
        }
        if (doctor.getLastName() != null){
            System.out.println(doctor.getLastName());
            resultDoctorList.retainAll(doctorService.findByLastName(doctor.getLastName()));
        }
        if (doctor.getSpecialization() != null){
            System.out.println(doctor.getSpecialization());
            resultDoctorList.retainAll(doctorService.findBySpecialization(doctor.getSpecialization()));
        }
        return resultDoctorList;
    }
}
