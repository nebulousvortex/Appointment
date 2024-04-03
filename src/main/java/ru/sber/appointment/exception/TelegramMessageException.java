package ru.sber.appointment.exception;

public class TelegramMessageException extends RuntimeException{
    public  TelegramMessageException(String exception){
        super(exception);
    }
}
