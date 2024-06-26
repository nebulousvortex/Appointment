package ru.sber.appointment.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "doctors")
public class Doctor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotNull(message = "Id должен быть заполнен")
    private Long id;

    private String specialization;
    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;
    @OneToMany(mappedBy = "doctor", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Schedule> schedules = new ArrayList<>();

    public Doctor(Long doctorId) {
        this.id = doctorId;
    }

    public Doctor() {
    }

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
