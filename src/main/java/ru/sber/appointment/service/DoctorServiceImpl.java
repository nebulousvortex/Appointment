package ru.sber.appointment.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import ru.sber.appointment.model.Doctor;
import ru.sber.appointment.repository.DoctorRepository;
import ru.sber.appointment.repository.UserRepository;

import java.util.List;

@Service
@Primary
public class DoctorServiceImpl implements DoctorService {
    @Autowired
    DoctorRepository doctorRepository;
    @Autowired
    UserService userService;
    @Autowired
    UserRepository userRepository;

    @Override
    public List<Doctor> findAllDoctors() {
        return doctorRepository.findAll();
    }

    @Override
    public void saveDoctor(Doctor doctor) {
        doctor.setUser(userRepository.findByUsername(doctor.getUser().getUsername()));
        userService.updateUserRole(doctor.getUser(), 2L);
        doctorRepository.save(doctor);
    }

    @Override
    public List<Doctor> findBySpecialization(String specialization) {
        return doctorRepository.findBySpecialization(specialization);
    }

    @Override
    public List<Doctor> findByFirstName(String firstName) {
        return doctorRepository.findByUser(userRepository.findByFirstName(firstName).get(0));
    }

    @Override
    public List<Doctor> findByLastName(String lastName) {
        return doctorRepository.findByUser(userRepository.findByLastName(lastName).get(0));
    }

    @Override
    public void updateDoctor(Doctor unknownDoctor) {
        if(doctorRepository.findById(unknownDoctor.getId()).isPresent()){
            Doctor doctor = doctorRepository.findById(unknownDoctor.getId()).get();
            doctor.setSpecialization(unknownDoctor.getSpecialization());
            doctorRepository.save(doctor);
        }
    }

    @Override
    public void deleteDoctor(Doctor unknownDoctor) {
        if (doctorRepository.findById(unknownDoctor.getId()).isPresent()){
            Doctor doctor = doctorRepository.findById(unknownDoctor.getId()).get();
            userService.updateUserRole(doctor.getUser(), 1L);
            doctorRepository.delete(doctor);
        }

    }
}
