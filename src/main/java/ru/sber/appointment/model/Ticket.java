package ru.sber.appointment.model;

import javax.persistence.*;
import java.time.LocalTime;

@Entity
@Table(name = "ticket")
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalTime time;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "schedule_id")
    private Schedule schedule;
    @ManyToOne(cascade = CascadeType.ALL, optional = true)
    @JoinColumn(name = "user_id")
    private User user;

    @PreRemove
    private void clearUser(){
        this.user = null;
    }

    public Long getId() {
        return id;
    }

    public LocalTime getTime() {
        return time;
    }

    public Schedule getSchedule() {
        return schedule;
    }

    public User getUser() {
        return user;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setTime(LocalTime time) {
        this.time = time;
    }

    public void setSchedule(Schedule schedule) {
        this.schedule = schedule;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
