package ru.sber.appointment.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.sber.appointment.model.Doctor;
import ru.sber.appointment.model.Schedule;
import ru.sber.appointment.model.Ticket;
import ru.sber.appointment.repository.ScheduleRepository;
import ru.sber.appointment.repository.TicketRepository;
import ru.sber.appointment.service.interfaces.DoctorService;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class TicketServiceTest {
    @Mock
    private TicketRepository ticketRepository;
    @Mock
    private DoctorService doctorService;
    @Mock
    private ScheduleRepository scheduleRepository;
    @InjectMocks
    private TicketServiceImpl ticketService;

    @Test
    public void testFindAllTickets() {
        List<Ticket> tickets = new ArrayList<>();
        Mockito.when(ticketRepository.findAll()).thenReturn(tickets);

        List<Ticket> foundTickets = ticketService.findAllTickets();

        assertEquals(tickets, foundTickets);
    }

    @Test
    public void testSaveTicket() {
        Ticket ticket = new Ticket();
        ticket.setId(1L);

        ticketService.saveTicket(ticket);

        Mockito.verify(ticketRepository).save(ticket);
    }

    @Test
    public void testFindDoctorFreeTicket() {
        Doctor unknownDoctor = new Doctor();
        unknownDoctor.setId(1L);
        Doctor doctor = new Doctor();
        doctor.setId(1L);

        Schedule schedule = new Schedule();
        schedule.setDate(LocalDate.now());
        schedule.setDoctor(doctor);

        List<Schedule> schedules = new ArrayList<>();
        schedules.add(schedule);

        Ticket ticket = new Ticket();
        ticket.setSchedule(schedule);

        Mockito.when(doctorService.findById(1L)).thenReturn(doctor);
        Mockito.when(scheduleRepository.findAllByDoctorAndDateAfter(doctor, LocalDate.now())).thenReturn(schedules);
        Mockito.when(ticketRepository.findAllBySchedule(schedule)).thenReturn(List.of(ticket));

        List<Ticket> doctorFreeTickets = ticketService.findDoctorFreeTicket(unknownDoctor);

        assertEquals(1, doctorFreeTickets.size());
    }
}