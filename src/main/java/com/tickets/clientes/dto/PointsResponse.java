package com.tickets.clientes.dto;

import com.tickets.clientes.entities.Puntos;
import lombok.Data;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

@Data
public class PointsResponse {
    private Integer id;
    private Integer puntos;
    private Integer suma;
    private Integer usados;
    private String creado;

    public PointsResponse(Puntos puntos) {
        this.id = puntos.getId();
        this.puntos = puntos.getPuntos();
        this.suma = puntos.getSuma();
        this.usados = puntos.getUsados();
        LocalDateTime dateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(puntos.getCreado().getTime()), ZoneId.systemDefault());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        this.creado = dateTime.format(formatter);
    }
}
