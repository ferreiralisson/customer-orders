package br.com.dbserver.customerorders.controller;

import br.com.dbserver.customerorders.dto.OrderRequestDTO;
import br.com.dbserver.customerorders.helpers.Util;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequestMapping(path = "/customer-orders")
public class OrdersController {

    private final Logger logger = LoggerFactory.getLogger(OrdersController.class);

    @PostMapping
    public ResponseEntity<Void> createOrder(@RequestBody @Valid OrderRequestDTO orderRequestDTO){
        logger.info("Criando pedido do cliente, Inicio - {}", Util.localDateTimeToString(LocalDateTime.now()));

        logger.info("Criando pedido do cliente, Fim - {}", Util.localDateTimeToString(LocalDateTime.now()));
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
