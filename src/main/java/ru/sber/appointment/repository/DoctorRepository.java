package ru.sber.appointment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.sber.appointment.model.Doctor;
import ru.sber.appointment.model.User;

import java.util.List;

public interface DoctorRepository extends JpaRepository<Doctor, Long> {
    List<Doctor> findBySpecialization (String specialization);
    Doctor findByUser (User user);
}
