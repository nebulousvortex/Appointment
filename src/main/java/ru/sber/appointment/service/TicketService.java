package ru.sber.appointment.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.sber.appointment.model.Doctor;
import ru.sber.appointment.model.Schedule;
import ru.sber.appointment.model.Ticket;
import ru.sber.appointment.model.User;
import ru.sber.appointment.repository.DoctorRepository;
import ru.sber.appointment.repository.ScheduleRepository;
import ru.sber.appointment.repository.TicketRepository;
import ru.sber.appointment.repository.UserRepository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class TicketService {
    @Autowired
    TicketRepository ticketRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    DoctorRepository doctorRepository;
    @Autowired
    ScheduleRepository scheduleRepository;
    public List<Ticket> findAllTickets() {
        return ticketRepository.findAll();
    }

    public void saveTicket(Ticket ticket) {
        ticketRepository.save(ticket);
    }

    public void updateTicket(Ticket unknownTicket) {
        User user = userRepository.findByUsername(unknownTicket.getUser().getUsername());
        Ticket ticket = ticketRepository.findById(unknownTicket.getId()).orElseThrow();
        ticket.setUser(user);
        ticketRepository.save(ticket);
    }

    public List<Ticket> findDoctorTicket(Doctor unknownDoctor){
        Doctor doctor = doctorRepository.findById(unknownDoctor.getId()).orElseThrow();
        List<Schedule> futureSchedules = scheduleRepository.findAllByDoctorAndDateAfter(doctor, LocalDate.now());

        List<Ticket> tickets = new ArrayList<>();
        for (Schedule schedule : futureSchedules) {
            List<Ticket> doctorTickets = ticketRepository.findAllBySchedule(schedule);
            for (Ticket ticket : doctorTickets) {
                if (ticket.getUser() == null) {
                    tickets.add(ticket);
                }
            }
        }

        return tickets;
    }

    public void saveForDay(Schedule schedule){
        LocalTime time = LocalTime.now().withHour(8).withMinute(0).withSecond(0).withNano(0);
        for (int j = 0; j < 24; j++) {
            time = time.plusMinutes(30);
            Ticket ticket = new Ticket();
            ticket.setSchedule(schedule);
            ticket.setTime(time);
            ticketRepository.save(ticket);
        }
    }
}
