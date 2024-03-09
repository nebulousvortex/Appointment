package ru.sber.appointment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.sber.appointment.model.Ticket;

public interface TicketRepository extends JpaRepository<Ticket, Long> {
}
