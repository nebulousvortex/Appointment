package ru.sber.appointment.exception;

public class NoSuchUserException extends RuntimeException{
    public  NoSuchUserException(String exception){
        super(exception);
    }
}
