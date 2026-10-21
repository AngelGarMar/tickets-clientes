package com.tickets.clientes.services;

import com.tickets.clientes.dto.ClienteRequest;
import com.tickets.clientes.models.ResponseService;
import jakarta.servlet.http.HttpServletRequest;

public interface ClienteService {
    ResponseService createClient(ClienteRequest request);
    ResponseService getClient(Integer clientId, HttpServletRequest servletRequest);
}
