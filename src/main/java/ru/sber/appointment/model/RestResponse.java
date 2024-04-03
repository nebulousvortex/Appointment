package ru.sber.appointment.model;

import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Component
public class RestResponse {

    RestTemplate restTemplate;
    HttpHeaders headers;
    public RestResponse() {
        this.restTemplate = new RestTemplate();
        this.headers = new HttpHeaders();
    }

    public ResponseEntity<?> getResponseEntity(String url, HttpMethod method, Class<?> type){
        headers.setAccept(List.of(MediaType.APPLICATION_OCTET_STREAM));
        HttpEntity<?> entity = new HttpEntity<>(headers);
        return restTemplate.exchange(url, method, entity, type);
    }
}
