package com.tickets.clientes.dto;

import com.tickets.clientes.entities.Cliente;
import lombok.Data;

import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

@Data
public class ClienteResponse implements Serializable {
    private int id;
    private String nombre;
    private String apaterno;
    private String amaterno;
    private int edad;
    private String celular;
    private String tarjeta;
    private String insen;
    private String creado;
    private String modificado;
    private int estatus;
    private int puntos;

    public ClienteResponse(Cliente cliente, int puntos) {
        this.id = cliente.getId();
        this.nombre = cliente.getNombre();
        this.apaterno = cliente.getApaterno();
        this.amaterno = cliente.getAmaterno();
        this.edad = cliente.getEdad();
        this.celular = cliente.getCelular();
        this.tarjeta = cliente.getTarjeta();
        this.insen = cliente.getInsen();
        LocalDateTime dateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(cliente.getCreado().getTime()), ZoneId.systemDefault());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        this.creado = dateTime.format(formatter);
        if (cliente.getActualizado() != null) {
            LocalDateTime dateModif = LocalDateTime.ofInstant(Instant.ofEpochMilli(cliente.getActualizado().getTime()), ZoneId.systemDefault());
            this.modificado = dateModif.format(formatter);
        } else this.modificado = "";
        this.estatus = cliente.getEstatus();
        this.puntos = puntos;
    }
}
