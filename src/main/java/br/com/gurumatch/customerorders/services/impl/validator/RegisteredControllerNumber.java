package br.com.gurumatch.customerorders.services.impl.validator;

import br.com.gurumatch.customerorders.dtos.OrderDTO;
import br.com.gurumatch.customerorders.exceptions.ExistingResourceException;
import br.com.gurumatch.customerorders.repositories.OrderRepository;

public class RegisteredControllerNumber implements ValidatorOrders {

    private final OrderRepository orderRepository;
    private final OrderDTO order;

    public RegisteredControllerNumber(OrderRepository orderRepository, OrderDTO order) {
        this.orderRepository = orderRepository;
        this.order = order;
    }

    @Override
    public void Valid() {
        if (this.order != null) {
            if (this.orderRepository.existsByOrderNumber(order.getOrderNumber())) {
                throw new ExistingResourceException("Numero de controle ja cadastrado");
            }
        }
    }
}
