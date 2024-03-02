package ru.sber.appointment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.sber.appointment.model.Schedule;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
}
