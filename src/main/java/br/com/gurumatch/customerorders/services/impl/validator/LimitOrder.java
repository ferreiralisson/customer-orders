package br.com.gurumatch.customerorders.services.impl.validator;

import br.com.gurumatch.customerorders.dtos.OrderDTO;
import br.com.gurumatch.customerorders.exceptions.LimitOrderExcededException;

public class LimitOrder implements ValidatorOrders {

    private final OrderDTO orderDTO;

    public LimitOrder(OrderDTO orderDTO) {
        this.orderDTO = orderDTO;
    }


    @Override
    public void Valid() {
        int sumQuantity = orderDTO.getProducts().stream()
                .mapToInt(product -> Integer.parseInt(product.quantity()))
                .sum();
        
        if (sumQuantity > 10) {
            throw new LimitOrderExcededException("Numero de pedidos excedeu o permitido");
        }
    }
}
