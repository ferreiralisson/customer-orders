package br.com.gurumatch.customerorders.conveters;

import br.com.gurumatch.customerorders.dtos.ClientDTO;
import br.com.gurumatch.customerorders.dtos.OrderDTO;
import br.com.gurumatch.customerorders.dtos.ProductDTO;
import br.com.gurumatch.customerorders.helpers.Constants;
import br.com.gurumatch.customerorders.helpers.Util;
import br.com.gurumatch.customerorders.models.Client;
import br.com.gurumatch.customerorders.models.Order;
import br.com.gurumatch.customerorders.models.Product;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class OrderDtoToOrderMapper implements Mapper<OrderDTO, Order> {
    @Override
    public Order map(OrderDTO orderDTO, Order order) {

        order = Order.builder()
                .orderNumber(orderDTO.getOrderNumber())
                .registrationDate(getRegistration(orderDTO.getRegistrationDate()))
                .client(getClient(orderDTO.getClient()))
                .products(getProduct(orderDTO.getProducts()))
                .totalOrderValue(sumTotalOrderValue(orderDTO.getProducts()))
                .build();

        return order;
    }

    private BigDecimal sumTotalOrderValue(List<ProductDTO> products){
        return products.stream()
                .map(product -> new BigDecimal(product.unitPrice()).multiply(new BigDecimal(product.quantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private LocalDateTime getRegistration(String registrationDate) {
        return StringUtils.hasText(registrationDate) ?
                Util.stringToLocalDateTime(registrationDate, Constants.DATE_TIME_FORMAT_DEFAULT) :
                LocalDateTime.now().withNano(0);
    }

    private Client getClient(ClientDTO clientDTO) {
        return Client.builder()
                .id(Long.parseLong(clientDTO.id()))
                .name(clientDTO.name())
                .build();
    }

    private List<Product> getProduct(List<ProductDTO> productsDTO) {
        return productsDTO.stream()
                .map(productDTO -> {
                    BigDecimal unitPrice = new BigDecimal(productDTO.unitPrice());
                    int quantity = Optional.ofNullable(productDTO.quantity())
                            .map(Integer::parseInt)
                            .orElse(1);

                    return new Product(productDTO.name(), unitPrice, quantity);
                })
                .collect(Collectors.toList());
    }

}
