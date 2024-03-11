package ru.sber.appointment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.sber.appointment.model.Schedule;
import ru.sber.appointment.model.Ticket;

import java.util.List;

public interface TicketRepository extends JpaRepository<Ticket, Long> {
    List<Ticket> findAllByScheduleIn(List<Schedule> schedules);

    List<Ticket> findAllBySchedule(Schedule schedule);
}
