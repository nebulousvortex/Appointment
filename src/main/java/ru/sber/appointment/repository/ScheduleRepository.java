package ru.sber.appointment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.sber.appointment.model.Doctor;
import ru.sber.appointment.model.Schedule;
import java.time.LocalDate;


public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
    Schedule findByDateAndDoctor(LocalDate date, Doctor doctor);
}
