package ru.sber.appointment.exception;

public class NoSuchDoctorException extends RuntimeException{
    public NoSuchDoctorException(String exception){
        super(exception);
    }
}
