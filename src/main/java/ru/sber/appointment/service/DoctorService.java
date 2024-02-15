package ru.sber.appointment.service;

import ru.sber.appointment.model.Doctor;

import java.util.List;

interface DoctorService {
    List<Doctor> findAllDoctors();
    void saveDoctor(Doctor doctor);
    void deleteDoctor(Doctor doctor);
    List<Doctor> findByPost(String post);
    List<Doctor> findByFirstName(String firstName);
    List<Doctor> findByLastName(String lastName);
    void updateDoctor(Doctor doctor);
}
