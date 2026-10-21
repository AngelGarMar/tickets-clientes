package com.tickets.clientes.services;

import com.tickets.clientes.dto.PointsRequest;
import com.tickets.clientes.models.ResponseService;

public interface PuntosService {
    ResponseService createPoints(PointsRequest request);
}
