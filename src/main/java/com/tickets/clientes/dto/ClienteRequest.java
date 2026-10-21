package com.tickets.clientes.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.io.Serializable;

@Data
public class ClienteRequest implements Serializable {
    @NotBlank(message = "El campo nombre no puede ser vacío.")
    @Size(min = 1, max = 20)
    private String nombre;
    @Size(min = 0, max = 20)
    private String apaterno;
    @Size(min = 0, max = 20)
    private String amaterno;
    @NotNull(message = "El campo edad no puede ser vacío.")
    private Integer edad;
    @NotBlank(message = "El campo celular no puede ser vacío.")
    @Size(min = 10, max = 10)
    private String celular;
    @Size(min = 0, max = 20)
    private String tarjeta;
    @Size(min = 0, max = 45)
    private String insen;
}
