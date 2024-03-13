package ru.sber.appointment.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import ru.sber.appointment.model.Doctor;
import ru.sber.appointment.model.User;
import ru.sber.appointment.repository.DoctorRepository;

import java.util.Collections;
import java.util.List;

@Service
@Primary
public class DoctorServiceImpl implements DoctorService {
    @Autowired
    DoctorRepository doctorRepository;
    @Autowired
    UserService userService;
    @Autowired
    ScheduleService scheduleService;

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
        return Collections.singletonList(doctorRepository.findByUser((User) userService.findByFirstName(firstName).get(0)));
    }

    @Override
    public List<Doctor> findByLastName(String lastName) {
        return Collections.singletonList(doctorRepository.findByUser((User) userService.findByLastName(lastName).get(0)));
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
        return (Doctor) doctorRepository.findByUser(user);
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
