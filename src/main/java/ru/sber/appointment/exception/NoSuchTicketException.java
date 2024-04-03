package ru.sber.appointment.exception;

public class NoSuchTicketException extends RuntimeException{
    public  NoSuchTicketException(String exception){
        super(exception);
    }
}
