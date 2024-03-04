package ru.sber.appointment.model;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "schedule")
public class Schedule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "doctor_id")
    private Doctor doctor;
    @Enumerated(EnumType.STRING)
    private DayType dayType;
    private LocalDate date;

    @OneToMany(mappedBy = "schedule", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Ticket> tickets = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public DayType getDayType() {
        return dayType;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    public void setDayType(DayType dayType) {
        this.dayType = dayType;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}
