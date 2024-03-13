package ru.sber.appointment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.sber.appointment.model.Schedule;
import ru.sber.appointment.model.Ticket;
import ru.sber.appointment.model.User;

import java.util.List;

public interface TicketRepository extends JpaRepository<Ticket, Long> {
    List<Ticket> findAllByScheduleIn(List<Schedule> schedules);
    List<Ticket> findAllBySchedule(Schedule schedule);
    List<Ticket> findAllByUser(User user);
}
