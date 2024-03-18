package ru.sber.appointment.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.sber.appointment.jwt_manager.JwtProvider;
import ru.sber.appointment.model.Doctor;
import ru.sber.appointment.model.Ticket;
import ru.sber.appointment.model.User;
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
    JwtProvider jwtProvider;
    @Autowired
    AuthServiceImpl authService;

    @GetMapping("/get/tickets")
    public List<Ticket> getTickets(){
        return ticketService.findAllTickets();
    }

    @GetMapping("/get/tickets/{doctorId}")
    public List<Ticket> getDoctorFreeTickets(@PathVariable Long doctorId){
        return ticketService.findDoctorFreeTicket(new Doctor(doctorId));
    }

    @PostMapping("/post/tickets")
    public void saveTicket(@RequestBody(required = false) Ticket ticket){
        ticketService.saveTicket(ticket);
    }

    @PutMapping("/put/tickets")
    public void updateTicket(@RequestBody Ticket ticket){
        ticketService.updateTicket(ticket);
    }

    @GetMapping("/put/tickets/confirmed/{confirm_token}/{username}/{ticket_id}")
    public void updateTicketConfirm(
            @PathVariable String confirm_token,
            @PathVariable String username,
            @PathVariable Long ticket_id) {
        if (jwtProvider.validateConfirmToken(confirm_token)){
            Ticket ticket = ticketService.findById(ticket_id);
            User user = new User();
            user.setUsername(username);
            ticket.setUser(user);
            ticketService.updateTicket(ticket);
        }
    }

    @GetMapping("/get/tickets/user/{username}")
    public List<Ticket> getUserTickets(@PathVariable String username){
        return ticketService.findUserTicket(username);
    }

    @GetMapping("/get/tickets/doctor/{username}")
    public ResponseEntity<List<Ticket>> getDoctorBusyTickets(@PathVariable String username, ServletResponse response) throws IOException {
        if (authService.getAuthoritiesDoctor(username)){
            return ResponseEntity.ok(ticketService.findDoctorBusyTicket(username));
        }
        else {
            return ResponseEntity.ok(null);
        }
    }
}
