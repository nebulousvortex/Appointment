package ru.sber.appointment.utils;

import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class Validator {
    public ResponseEntity<?> getErrorList(BindingResult bindingResult){
        List<String> errorMessages = bindingResult.getFieldErrors().stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.toList());
        return ResponseEntity.ok(errorMessages);
    }
}
