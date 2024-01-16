package br.com.gurumatch.customerorders.controllers;

import br.com.gurumatch.customerorders.dtos.OrderDTO;
import br.com.gurumatch.customerorders.helpers.Constants;
import br.com.gurumatch.customerorders.services.OrderService;
import br.com.gurumatch.customerorders.services.impl.specification.OrderCriteria;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static br.com.gurumatch.customerorders.helpers.Util.localDateTimeToString;
import static java.time.LocalDateTime.now;

@RestController
@RequestMapping(path = Constants.API_VERSION + "/customer_orders")
public class OrderController {

    private final Logger logger = LoggerFactory.getLogger(OrderController.class);
    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping(consumes = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<OrderDTO> createOrder(@RequestBody @Valid OrderDTO orderDTO){
        logger.info("Criando pedido do cliente, Inicio - {}", localDateTimeToString(now()));
        OrderDTO order = orderService.createOrder(orderDTO);
        logger.info("Criando pedido do cliente, Fim - {}", localDateTimeToString(now()));
        return new ResponseEntity<>(order, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<Page<OrderDTO>> listAll(Pageable pageable, OrderCriteria criteria){
        logger.info("Listando pedidos, inicio -  {}", localDateTimeToString(now()));
        Page<OrderDTO> orders = orderService.listAll(pageable, criteria);
        logger.info("Listando pedidos, fim -  {}", localDateTimeToString(now()));
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }
}
