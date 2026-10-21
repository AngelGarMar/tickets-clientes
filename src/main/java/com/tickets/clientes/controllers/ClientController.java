package com.tickets.clientes.controllers;

import com.tickets.clientes.constants.Constants;
import com.tickets.clientes.dto.ClienteRequest;
import com.tickets.clientes.models.ResponseService;
import com.tickets.clientes.services.ClienteService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/tickets/clients")
@Slf4j
public class ClientController {
    @Autowired
    private ClienteService service;

    @PostMapping
    public ResponseEntity<ResponseService> createClient(@RequestBody @Valid ClienteRequest request, BindingResult result) {
        if (result.hasFieldErrors()) return validation(result);
        ResponseService response = service.createClient(request);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{clientId}")
    public ResponseEntity<ResponseService> getClient(@PathVariable Integer clientId, HttpServletRequest request, HttpServletRequest servletRequest) {
        ResponseService response = service.getClient(clientId, servletRequest);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    private ResponseEntity<ResponseService> validation(BindingResult result) {
        Map<String, String> errors = new HashMap<>();
        result.getFieldErrors().forEach(err -> {
            errors.put(err.getField(), err.getDefaultMessage());
        });
        return new ResponseEntity<>(new ResponseService(Constants.RESPONSE_TYPE_ERROR, Constants.MISSED_INFORMATION, errors), HttpStatus.OK);
    }
}
