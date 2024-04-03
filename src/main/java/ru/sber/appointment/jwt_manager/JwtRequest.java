package ru.sber.appointment.jwt_manager;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class JwtRequest {
    @NotNull
    private String login;
    @NotNull
    private String password;

    public void setLogin(String login) {
        this.login = login;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

}
