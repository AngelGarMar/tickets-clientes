package com.tickets.clientes.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Entity
@Table(name = "puntos")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Puntos {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NotNull
    private int puntos;
    @NotNull
    private int suma;
    private int usados;
    private Timestamp creado;
    private Timestamp actualizado;
    private int estatus;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "clientes_id")
    private Cliente cliente;
}
