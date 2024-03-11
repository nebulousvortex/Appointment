package ru.sber.appointment.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.sber.appointment.model.Doctor;
import ru.sber.appointment.model.Ticket;
import ru.sber.appointment.service.ScheduleService;
import ru.sber.appointment.service.TicketService;

import java.util.List;

@RestController
@RequestMapping("api/v1/ticket")
public class TicketController {
    @Autowired
    TicketService ticketService;

    @GetMapping("/getAll")
    public List<Ticket> getTickets(){
        return ticketService.findAllTickets();
    }

    @GetMapping("get/{doctorId}/tickets")
    public List<Ticket> getDoctorTickets(@PathVariable Long doctorId){
        return ticketService.findDoctorTicket(new Doctor(doctorId));
    }

    @PostMapping("/post")
    public void saveTicket(@RequestBody(required = false) Ticket ticket){
        ticketService.saveTicket(ticket);
    }

    @PutMapping("/appointUser")
    public void updateTicket(@RequestBody Ticket ticket){
        ticketService.updateTicket(ticket);
    }
}
