package ru.sber.appointment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.sber.appointment.model.Doctor;

import java.util.List;

public interface DoctorRepository extends JpaRepository<Doctor, Long> {
    List<Doctor> findByPost (String post);
    List<Doctor> findByFirstName (String firstName);
    List<Doctor> findByLastName (String lastName);
}
