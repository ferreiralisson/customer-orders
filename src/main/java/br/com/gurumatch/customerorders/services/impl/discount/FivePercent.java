package br.com.gurumatch.customerorders.services.impl.discount;

import br.com.gurumatch.customerorders.dtos.OrderDTO;
import br.com.gurumatch.customerorders.helpers.Util;

import java.math.BigDecimal;

public class FivePercent extends FullValueDiscount {

    public FivePercent(FullValueDiscount next) {
        super(next);
    }

    @Override
    public BigDecimal discount(OrderDTO order) {

        int sumQuantity = order.getProducts().stream()
                .mapToInt(product -> Integer.parseInt(product.quantity()))
                .sum();

        if (sumQuantity > 5 && sumQuantity < 10) {
            return Util.applyDiscount(new BigDecimal(order.getTotalOrderValue()), BigDecimal.valueOf(5));
        }

        return next.discount(order);
    }
}
