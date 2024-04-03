package ru.sber.appointment.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.sber.appointment.exception.NoSuchTicketException;
import ru.sber.appointment.model.Doctor;
import ru.sber.appointment.model.Schedule;
import ru.sber.appointment.model.Ticket;
import ru.sber.appointment.model.User;
import ru.sber.appointment.repository.ScheduleRepository;
import ru.sber.appointment.repository.TicketRepository;
import ru.sber.appointment.service.interfaces.DoctorService;
import ru.sber.appointment.service.interfaces.TicketService;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Сервис для работы с талонами на прием у врачей.
 */
@Service
public class TicketServiceImpl implements TicketService {
    @Autowired
    EmailServiceImpl emailService;
    @Autowired
    TicketRepository ticketRepository;
    @Autowired
    UserServiceImpl userService;
    @Autowired
    DoctorService doctorService;
    @Autowired
    ScheduleRepository scheduleRepository;
    @Override
    public List<Ticket> findAllTickets() {
        return ticketRepository.findAll();
    }

    @Override
    public Ticket findById(Long id) {
        return ticketRepository.findById(id).orElseThrow(()-> new NoSuchTicketException("Не найден талон с id " + id));
    }

    @Override
    public void saveTicket(Ticket ticket) {
        ticketRepository.save(ticket);
    }

    /**
     * Метод для обновления информации о талоне на прием и отправки уведомления на почту.
     * @param unknownTicket объект с обновленными данными о талоне
     */
    @Override
    public void updateTicket(Ticket unknownTicket) {
        User user = userService.findByUsername(unknownTicket.getUser().getUsername());
        Ticket ticket = ticketRepository.findById(unknownTicket.getId()).orElseThrow(()-> new NoSuchTicketException("Не найден талон с id " + unknownTicket.getId()));
        ticket.setUser(user);
        ticketRepository.save(ticket);
        String message = "Здравствуйте, \n" + user.getFirstName() + "! Вы были записаны на прием к "
                + ticket.getSchedule().getDoctor().getUser().getFirstName() + ' '
                + ticket.getSchedule().getDoctor().getUser().getLastName() + '\n'
                + "Дата и время: " + ticket.getSchedule().getDate() + ' ' + ticket.getTime();
        emailService.sendEmail(user.getMail(), "Запись к врачу!", message);
    }

    /**
     * Метод для поиска свободных и актуальных талонов у определенного врача.
     * @param unknownDoctor объект врача
     * @return список свободных талонов
     */
    @Override
    public List<Ticket> findDoctorFreeTicket(Doctor unknownDoctor){
        Doctor doctor = doctorService.findById(unknownDoctor.getId());
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

    @Override
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

    @Override
    public List<Ticket> findUserTicket(String username) {
        User user = userService.findByUsername(username);
        return ticketRepository.findAllByUser(user);
    }

    /**
     * Метод для поиска занятых талонов у определенного врача.
     * @param username имя пользователя (врача)
     * @return список занятых талонов
     */
    @Override
    public List<Ticket> findDoctorBusyTicket(String username) {
        Doctor doctor = doctorService.findByUser(userService.findByUsername(username));
        List<Schedule> futureSchedules = scheduleRepository.findAllByDoctorAndDateAfter(doctor, LocalDate.now());

        List<Ticket> tickets = new ArrayList<>();
        for (Schedule schedule : futureSchedules) {
            List<Ticket> doctorTickets = ticketRepository.findAllBySchedule(schedule);
            for (Ticket ticket : doctorTickets) {
                if (ticket.getUser() != null) {
                    tickets.add(ticket);
                }
            }
        }

        return tickets;
    }
}
