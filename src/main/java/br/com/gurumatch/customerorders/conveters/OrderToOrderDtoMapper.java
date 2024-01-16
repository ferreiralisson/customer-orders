package br.com.gurumatch.customerorders.conveters;

import br.com.gurumatch.customerorders.dtos.ClientDTO;
import br.com.gurumatch.customerorders.dtos.OrderDTO;
import br.com.gurumatch.customerorders.dtos.ProductDTO;
import br.com.gurumatch.customerorders.helpers.Util;
import br.com.gurumatch.customerorders.models.Order;
import br.com.gurumatch.customerorders.models.Product;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class OrderToOrderDtoMapper implements Mapper<Order, OrderDTO> {
    @Override
    public OrderDTO map(Order order, OrderDTO orderDTO) {
        return OrderDTO.builder()
                .id(order.getId().toString())
                .orderNumber(order.getOrderNumber())
                .registrationDate(Util.localDateTimeToString(order.getRegistrationDate()))
                .totalOrderValue(order.getTotalOrderValue().toString())
                .client(new ClientDTO(order.getClient().getId().toString(), order.getClient().getName()))
                .products(getProducts(order.getProducts()))
                .build();
    }

    private List<ProductDTO> getProducts(List<Product> products) {
        return products.stream()
                .map(product -> new ProductDTO(product.getName(), product.getUnitPrice().toString(), product.getQuantity().toString()))
                .collect(Collectors.toList());
    }
}
