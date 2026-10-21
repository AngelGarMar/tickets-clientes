package com.tickets.clientes.controllers;

import com.tickets.clientes.constants.Constants;
import com.tickets.clientes.dto.PointsRequest;
import com.tickets.clientes.models.ResponseService;
import com.tickets.clientes.services.PuntosService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/tickets/points")
public class PointsController {
    @Autowired
    private PuntosService service;

    @PostMapping
    public ResponseEntity<ResponseService> createPoints(@RequestBody @Valid PointsRequest points, BindingResult result) {
        if (result.hasFieldErrors()) return validation(result);
        ResponseService response = service.createPoints(points);
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
