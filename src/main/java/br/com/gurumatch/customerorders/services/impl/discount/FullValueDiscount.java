package br.com.gurumatch.customerorders.services.impl.discount;


import br.com.gurumatch.customerorders.dtos.OrderDTO;
import lombok.AllArgsConstructor;

import java.math.BigDecimal;

@AllArgsConstructor
public abstract class FullValueDiscount {

    protected FullValueDiscount next;
    public abstract BigDecimal discount(OrderDTO orders);
}
