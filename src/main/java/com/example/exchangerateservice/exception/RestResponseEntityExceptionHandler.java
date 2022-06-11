package com.example.exchangerateservice.exception;

import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice("com.example.exchangerateservice")
public class RestResponseEntityExceptionHandler {

    private final HttpHeaders headers = new HttpHeaders();
    private final ObjectNode node = JsonNodeFactory.instance.objectNode();

    public RestResponseEntityExceptionHandler() {
        headers.add("Content-Type", "application/json");
    }

    @ExceptionHandler(NullPointerException.class)
    ResponseEntity<String> handleNullPointerException(NullPointerException e) {
        node.put("status", HttpStatus.NOT_FOUND.value());
        node.put("error", "Not Found");
        node.put("message", e.getMessage());
        return new ResponseEntity<>(node.toString(), headers, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(RuntimeException.class)
    ResponseEntity<String> handleRuntime(RuntimeException e) {
        node.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
        node.put("error", "Server Error");
        node.put("message", e.getMessage());
        return new ResponseEntity<>(node.toString(), headers, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
