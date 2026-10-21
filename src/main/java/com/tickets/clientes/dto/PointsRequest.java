package com.tickets.clientes.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serializable;

@Data
public class PointsRequest implements Serializable {
    @NotNull(message = "El campo clienteId no puede ser nulo")
    @Min(value = 0, message = "El valor mínimo es 1")
    private Integer clienteId;
    @NotNull(message = "El campo suma no puede ser nulo")
    @Min(value = 0, message = "El valor mínimo es 0")
    private Integer suma;
    @NotNull(message = "El campo usados no puede ser nulo")
    @Min(value = 0, message = "El valor mínimo es 0")
    private Integer usados;
}
