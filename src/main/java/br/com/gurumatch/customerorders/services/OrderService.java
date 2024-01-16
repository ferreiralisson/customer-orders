package br.com.gurumatch.customerorders.services;

import br.com.gurumatch.customerorders.dtos.OrderDTO;
import br.com.gurumatch.customerorders.services.impl.specification.OrderCriteria;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface OrderService {
    OrderDTO createOrder(OrderDTO orderDTO);

    Page<OrderDTO> listAll(Pageable pageable, OrderCriteria criteria);
}
