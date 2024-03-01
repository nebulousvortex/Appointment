package ru.sber.appointment.model;

import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.Set;


@Entity
@Table(name = "doctors")
public class Doctor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "Должность")
    private String specialization;
    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    public void setId(Long id) {
        this.id = id;
    }
    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }
    public void setUser(User user) {
        this.user = user;
    }
    public String getSpecialization() {
        return specialization;
    }
    public Long getId() {
        return id;
    }
    public User getUser() {
        return user;
    }
}
