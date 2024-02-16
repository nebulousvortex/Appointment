package ru.sber.appointment.model;

import javax.persistence.*;


@Entity
@Table(name = "doctors")
public class Doctor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "Имя")
    private String firstName;
    @Column(name = "Фамилия")
    private String lastName;
    @Column(name = "Должность")
    private String specialization;
    public void setId(Long id) {
        this.id = id;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }
    public String getLastName() {
        return lastName;
    }
    public String getSpecialization() {
        return specialization;
    }
    public String getFirstName() {
        return firstName;
    }
    public Long getId() {
        return id;
    }
}
