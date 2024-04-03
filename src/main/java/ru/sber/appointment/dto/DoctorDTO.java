package ru.sber.appointment.dto;


public class DoctorDTO {
    private Long id;
    private String specialization;
    private UserDTO user;

    public void setId(Long id) {
        this.id = id;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public String getSpecialization() {
        return specialization;
    }

    public UserDTO getUser() {
        return user;
    }
}
