package br.com.gurumatch.customerorders.helpers;

import br.com.gurumatch.customerorders.dtos.ClientDTO;
import br.com.gurumatch.customerorders.dtos.OrderDTO;
import br.com.gurumatch.customerorders.dtos.ProductDTO;
import br.com.gurumatch.customerorders.models.Client;
import br.com.gurumatch.customerorders.models.Order;
import br.com.gurumatch.customerorders.models.Product;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class OrderCreator {

    public static OrderDTO orderDTOValid(){
        return OrderDTO.builder()
                .id("1")
                .orderNumber("123")
                .totalOrderValue("83.08")
                .client(new ClientDTO("1", "Client 1"))
                .products(productDtoValid())
                .build();

    }

    public static Order orderValid() {
        return Order.builder()
                .id(1L)
                .orderNumber("123")
                .registrationDate(LocalDateTime.of(2024, 1, 15, 12, 20))
                .totalOrderValue(new BigDecimal("83.08"))
                .client(Client.builder()
                        .id(1L)
                        .name("Client 1")
                        .build())
                .products(productValid())
                .build();
    }

    private static List<Product> productValid(){
        return List.of(
                Product.builder()
                        .id(1L)
                        .name("Produto 1")
                        .unitPrice(new BigDecimal("10.1"))
                        .quantity(5)
                        .build(),
                Product.builder()
                        .id(1L)
                        .name("Produto 2")
                        .unitPrice(new BigDecimal("20.1"))
                        .quantity(1)
                        .build(),
                Product.builder()
                        .id(1L)
                        .name("Produto 3")
                        .unitPrice(new BigDecimal("15.75"))
                        .quantity(4)
                        .build()
        );
    }

    private static List<ProductDTO> productDtoValid(){
        return List.of(
                new ProductDTO("Produto 1", "10.1", "5"),
                new ProductDTO("Produto 2", "20.0", "1"),
                new ProductDTO("Produto 3", "15.75", "4")
        );
    }

    public static OrderDTO orderDTOInvalid(List<ProductDTO> productDTO){
        return OrderDTO.builder()
                .id("1")
                .orderNumber("123")
                .totalOrderValue("83.08")
                .client(new ClientDTO("1", "Client 1"))
                .products(productDTO)
                .build();

    }
}
