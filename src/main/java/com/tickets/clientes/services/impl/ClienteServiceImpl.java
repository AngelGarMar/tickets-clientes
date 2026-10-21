package com.tickets.clientes.services.impl;

import com.tickets.clientes.constants.Constants;
import com.tickets.clientes.dto.ClienteRequest;
import com.tickets.clientes.dto.ClienteResponse;
import com.tickets.clientes.entities.Cliente;
import com.tickets.clientes.entities.Puntos;
import com.tickets.clientes.models.ResponseService;
import com.tickets.clientes.repositories.ClienteRepository;
import com.tickets.clientes.repositories.PuntosRepository;
import com.tickets.clientes.services.ClienteService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Optional;

@Service
@Slf4j
@RefreshScope
public class ClienteServiceImpl implements ClienteService {
    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private PuntosRepository puntosRepository;

    @Value("${app.maintenance.createClients}")
    private boolean createClients;

    @Override
    @Transactional
    public ResponseService createClient(ClienteRequest request) {
        log.info("se revisa que por configuración se puedan crear clientes");
        if (!createClients) {
            log.info("no se pueden crear clientes");
            return new ResponseService(Constants.RESPONSE_TYPE_WARNING, Constants.CLIENT_CREATE_DISABLED, null);
        }
        log.info("se revisa que no exista el numero de celular");
        Optional<Cliente> optionalCliente =  clienteRepository.findByCelular(request.getCelular());
        if (optionalCliente.isPresent()) {
            log.info("ya existe un cliente con ese celular");
            return new ResponseService(Constants.RESPONSE_TYPE_ERROR, Constants.CLIENT_CEL_EXISTS, null);
        }
        log.info("se inserta en la base de datos");
        Cliente clienteSaved = Cliente.builder()
                .nombre(request.getNombre().trim())
                .apaterno(request.getApaterno() == null ? "" : request.getApaterno().trim())
                .amaterno(request.getAmaterno() == null ? "" : request.getAmaterno().trim())
                .edad(request.getEdad())
                .celular(request.getCelular())
                .tarjeta(request.getTarjeta() == null ? "" : request.getTarjeta().trim())
                .insen(request.getInsen() == null ? "" : request.getInsen().trim())
                .creado(Timestamp.valueOf(LocalDateTime.now(ZoneId.of("America/Mexico_City"))))
                .estatus(1)
                .build();
        ClienteResponse clienteResponse = new ClienteResponse(clienteRepository.save(clienteSaved), 0);
        log.info("se regresa el cliente guardado");
        return new ResponseService(Constants.RESPONSE_TYPE_SUCCESS, Constants.CLIENT_SAVED, clienteResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public ResponseService getClient(Integer clientId, HttpServletRequest servletRequest) {
        log.info("petición atendida desde el puerto {}", servletRequest.getServerPort());
        log.info("se verifica el cliente");
        Optional<Cliente> clientOptional = clienteRepository.findById(clientId);
        if (clientOptional.isEmpty()) {
            log.info("no existe el cliente");
            return new ResponseService(Constants.RESPONSE_TYPE_ERROR, Constants.CLIENT_UNKNOWN, null);
        }
        Optional<Puntos> puntosOptional = puntosRepository.findTopByOrderByIdDesc(clientId);
        int puntosTotales = 0;
        if (puntosOptional.isPresent()) puntosTotales = puntosOptional.get().getPuntos();
        ClienteResponse clienteResponse = new ClienteResponse(clientOptional.get(), puntosTotales);
        log.info("se regresa el cliente");
        return new ResponseService(Constants.RESPONSE_TYPE_SUCCESS, Constants.CLIENT_GET, clienteResponse);
    }
}
