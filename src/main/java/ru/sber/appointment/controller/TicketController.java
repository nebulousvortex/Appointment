package ru.sber.appointment.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.sber.appointment.model.Doctor;
import ru.sber.appointment.model.Ticket;
import ru.sber.appointment.service.AuthServiceImpl;
import ru.sber.appointment.service.TicketServiceImpl;

import javax.servlet.ServletResponse;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("api/v1/ticket")
public class TicketController {
    @Autowired
    TicketServiceImpl ticketService;
    @Autowired
    AuthServiceImpl authService;

    @GetMapping("/getAll")
    public List<Ticket> getTickets(){
        return ticketService.findAllTickets();
    }

    @GetMapping("get/{doctorId}/tickets")
    public List<Ticket> getDoctorFreeTickets(@PathVariable Long doctorId){
        return ticketService.findDoctorFreeTicket(new Doctor(doctorId));
    }

    @PostMapping("/post")
    public void saveTicket(@RequestBody(required = false) Ticket ticket){
        ticketService.saveTicket(ticket);
    }

    @PutMapping("/appointUser")
    public void updateTicket(@RequestBody Ticket ticket){
        ticketService.updateTicket(ticket);
    }

    @GetMapping("get/{username}/user/tickets")
    public List<Ticket> getUserTickets(@PathVariable String username){
        return ticketService.findUserTicket(username);
    }

    @GetMapping("get/{username}/doctor/tickets")
    public ResponseEntity<List<Ticket>> getDoctorBusyTickets(@PathVariable String username, ServletResponse response) throws IOException {
        if (authService.getAuthoritiesDoctor(username)){
            return ResponseEntity.ok(ticketService.findDoctorBusyTicket(username));
        }
        else {
            return ResponseEntity.ok(null);
        }
    }
}
