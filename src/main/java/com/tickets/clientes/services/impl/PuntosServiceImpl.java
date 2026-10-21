package com.tickets.clientes.services.impl;

import com.tickets.clientes.constants.Constants;
import com.tickets.clientes.dto.PointsRequest;
import com.tickets.clientes.dto.PointsResponse;
import com.tickets.clientes.entities.Cliente;
import com.tickets.clientes.entities.Puntos;
import com.tickets.clientes.models.ResponseService;
import com.tickets.clientes.repositories.ClienteRepository;
import com.tickets.clientes.repositories.PuntosRepository;
import com.tickets.clientes.services.PuntosService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Optional;

@Service
@Slf4j
public class PuntosServiceImpl implements PuntosService {
    @Autowired
    private PuntosRepository puntosRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Override
    @Transactional
    public ResponseService createPoints(PointsRequest request) {
        log.info("se revisa que exista el clienteId");
        Optional<Cliente> optionalCliente = clienteRepository.findById(request.getClienteId());
        if (optionalCliente.isEmpty()) {
            log.info("no existe el cliente");
            return new ResponseService(Constants.RESPONSE_TYPE_ERROR, Constants.CLIENT_UNKNOWN, null);
        }
        log.info("extrayendo el último registro de puntos del cliente");
        Optional<Puntos> puntosOptional = puntosRepository.findTopByOrderByIdDesc(request.getClienteId());
        if (puntosOptional.isEmpty()) {
            if (request.getUsados() != null && request.getUsados() > 0) {
                log.info("se intentan usar puntos pero el cliente no tiene");
                return new ResponseService(Constants.RESPONSE_TYPE_SUCCESS, Constants.POINTS_INSUFFICIENT, null);
            }
            log.info("no existen puntos del cliente, se guardan los primeros");
            Puntos puntos = Puntos.builder()
                    .puntos(request.getSuma())
                    .suma(request.getSuma())
                    .creado(Timestamp.valueOf(LocalDateTime.now(ZoneId.of("America/Mexico_City"))))
                    .estatus(1)
                    .cliente(optionalCliente.get())
                    .build();
            PointsResponse pointsResponse = new PointsResponse(puntosRepository.save(puntos));
            log.info("se regresa los datos de los puntos guardados");
            return new ResponseService(Constants.RESPONSE_TYPE_SUCCESS, Constants.POINTS_SAVED, pointsResponse);
        }
        Puntos puntosExists = puntosOptional.get();
        log.info("totalesBD: {}, para sumar de esta operación: {}, para restar de esta operación: {}", puntosExists.getPuntos(), request.getSuma(), request.getUsados());
        int puntosTotales = puntosExists.getPuntos() + request.getSuma();
        int puntosRestados = request.getUsados();
        if (puntosTotales < puntosRestados) {
            log.info("los puntos a usar son mayores a los puntos acumulados, no se puede esta operación.");
            return new ResponseService(Constants.RESPONSE_TYPE_SUCCESS, Constants.POINTS_INSUFFICIENT, null);
        }
        Puntos puntosSaved = Puntos.builder()
                .puntos(puntosTotales - puntosRestados)
                .suma(request.getSuma())
                .usados(puntosRestados)
                .creado(Timestamp.valueOf(LocalDateTime.now(ZoneId.of("America/Mexico_City"))))
                .estatus(1)
                .cliente(optionalCliente.get())
                .build();
        PointsResponse pointsResponse = new PointsResponse(puntosRepository.save(puntosSaved));
        log.info("se regresan los datos de los puntos guardados");
        return new ResponseService(Constants.RESPONSE_TYPE_SUCCESS, Constants.POINTS_SAVED, pointsResponse);
    }
}
