package com.tickets.clientes.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.List;

@Entity
@Table(name = "clientes")
@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class Cliente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NotBlank
    @Size(min = 1, max = 20)
    private String nombre;
    @Size(min = 0, max = 20)
    private String apaterno;
    @Size(min = 0, max = 20)
    private String amaterno;
    @NotNull
    private int edad;
    @NotBlank
    @Size(min = 10, max = 10)
    private String celular;
    @Size(min = 0, max = 20)
    private String tarjeta;
    @Size(min = 0, max = 45)
    private String insen;
    private String foto;
    private Timestamp creado;
    private Timestamp actualizado;
    private int estatus;
    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Puntos> puntos;
}
