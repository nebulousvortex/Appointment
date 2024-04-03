package ru.sber.appointment.exception;

public class NoSuchRoleException extends RuntimeException{
    public  NoSuchRoleException(String exception){
        super(exception);
    }
}
