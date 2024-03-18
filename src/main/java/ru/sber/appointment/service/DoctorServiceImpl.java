package ru.sber.appointment.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import ru.sber.appointment.model.Doctor;
import ru.sber.appointment.model.User;
import ru.sber.appointment.repository.DoctorRepository;
import ru.sber.appointment.service.interfaces.DoctorService;

import java.util.ArrayList;
import java.util.List;

@Service
@Primary
public class DoctorServiceImpl implements DoctorService {
    @Autowired
    DoctorRepository doctorRepository;
    @Autowired
    UserServiceImpl userService;

    @Override
    public List<Doctor> findAllDoctors() {
        return doctorRepository.findAll();
    }

    @Override
    public void saveDoctor(Doctor doctor) {
        doctor.setUser(userService.findByUsername(doctor.getUser().getUsername()));
        userService.updateUserRole(doctor.getUser(), 2L);
        doctorRepository.save(doctor);
    }

    @Override
    public List<Doctor> findBySpecialization(String specialization) {
        return doctorRepository.findBySpecialization(specialization);
    }

    @Override
    public List<Doctor> findByFirstName(String firstName) {
        List<User> usersWithFirstName = userService.findByFirstName(firstName);
        List<Doctor> doctors = new ArrayList<>();
        for (User user : usersWithFirstName) {
            Doctor doctor = doctorRepository.findByUser(user);
            if (doctor != null) {
                doctors.add(doctor);
            }
        }
        return doctors;
    }

    @Override
    public List<Doctor> findByLastName(String lastName) {
        List<User> usersWithLastName = userService.findByLastName(lastName);
        List<Doctor> doctors = new ArrayList<>();
        for (User user : usersWithLastName) {
            Doctor doctor = doctorRepository.findByUser(user);
            if (doctor != null) {
                doctors.add(doctor);
            }
        }
        return doctors;
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
    public Doctor findById(Long id) {
        return doctorRepository.findById(id).orElseThrow();
    }

    @Override
    public Doctor findByUser(User user) {
        return doctorRepository.findByUser(user);
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
