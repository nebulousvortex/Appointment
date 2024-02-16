package ru.sber.appointment.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import ru.sber.appointment.model.Doctor;
import ru.sber.appointment.repository.DoctorRepository;

import java.util.List;

@Service
@Primary
public class DoctorServiceImpl implements DoctorService {
    private final DoctorRepository repository;

    @Autowired
    public DoctorServiceImpl(DoctorRepository doctorRepository) {
        this.repository = doctorRepository;
    }

    @Override
    public List<Doctor> findAllDoctors() {
        return repository.findAll();
    }

    @Override
    public void saveDoctor(Doctor doctor) {
        repository.save(doctor);
    }

    @Override
    public void deleteDoctor(Doctor doctor) {
        repository.delete(doctor);
    }

    @Override
    public List<Doctor> findBySpecialization(String specialization) {
        return repository.findBySpecialization(specialization);
    }

    @Override
    public List<Doctor> findByFirstName(String firstName) {
        return repository.findByFirstName(firstName);
    }

    @Override
    public List<Doctor> findByLastName(String lastName) {
        return repository.findByLastName(lastName);
    }

    @Override
    public void updateDoctor(Doctor doctor) {
        repository.save(doctor);
    }
}
