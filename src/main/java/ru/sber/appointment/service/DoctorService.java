package ru.sber.appointment.service;

import ru.sber.appointment.model.Doctor;
import ru.sber.appointment.model.User;

import java.util.List;

interface DoctorService {
    List<Doctor> findAllDoctors();
    void saveDoctor(Doctor doctor);
    void deleteDoctor(Doctor doctor);
    List<Doctor> findBySpecialization(String specialization);
    List<Doctor> findByFirstName(String firstName);
    List<Doctor> findByLastName(String lastName);
    void updateDoctor(Doctor doctor);
    Doctor findById(Long id);
    Doctor findByUser(User byUsername);
}
