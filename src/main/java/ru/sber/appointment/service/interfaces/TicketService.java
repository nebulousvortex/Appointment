package ru.sber.appointment.service.interfaces;

import ru.sber.appointment.model.Doctor;
import ru.sber.appointment.model.Schedule;
import ru.sber.appointment.model.Ticket;

import java.util.List;

public interface TicketService {
    List<Ticket> findAllTickets();

    Ticket findById(Long id);

    void saveTicket(Ticket ticket);

    void updateTicket(Ticket unknownTicket);

    List<Ticket> findDoctorFreeTicket(Doctor unknownDoctor);

    void saveForDay(Schedule schedule);

    List<Ticket> findUserTicket(String username);

    List<Ticket> findDoctorBusyTicket(String username);
}
