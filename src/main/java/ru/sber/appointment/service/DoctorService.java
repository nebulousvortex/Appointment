package ru.sber.appointment.service;

import ru.sber.appointment.model.Doctor;

import java.util.List;

public interface DoctorService {
    public List<Doctor> findAllDoctors();
    public void saveDoctor(Doctor doctor);
    void deleteDoctor(Doctor doctor);
    public List<Doctor> findByPost(String post);
    public List<Doctor> findByFirstName(String firstName);
    public List<Doctor> findByLastName(String lastName);
    void updateDoctor(Doctor doctor);
}
