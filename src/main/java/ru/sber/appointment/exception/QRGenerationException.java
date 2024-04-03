package ru.sber.appointment.exception;

public class QRGenerationException extends RuntimeException{
    public  QRGenerationException(String exception){
        super(exception);
    }
}
