package ru.sber.appointment.model;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Schedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "doctor_id")
    private Doctor doctor;
    @ManyToOne
    @JoinColumn(name = "day_type_id")
    private DayType dayType;
    private Date date;

    public Long getId() {
        return id;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public DayType getDayType() {
        return dayType;
    }

    public Date getDate() {
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

    public void setDate(Date date) {
        this.date = date;
    }
}
