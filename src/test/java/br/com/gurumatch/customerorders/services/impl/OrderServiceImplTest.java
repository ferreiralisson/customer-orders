package br.com.gurumatch.customerorders.services.impl;

import br.com.gurumatch.customerorders.conveters.OrderToOrderDtoMapper;
import br.com.gurumatch.customerorders.dtos.OrderDTO;
import br.com.gurumatch.customerorders.dtos.ProductDTO;
import br.com.gurumatch.customerorders.exceptions.ExistingResourceException;
import br.com.gurumatch.customerorders.exceptions.LimitOrderExcededException;
import br.com.gurumatch.customerorders.helpers.OrderCreator;
import br.com.gurumatch.customerorders.models.Order;
import br.com.gurumatch.customerorders.repositories.OrderRepository;
import br.com.gurumatch.customerorders.services.OrderService;
import br.com.gurumatch.customerorders.services.impl.specification.OrderCriteria;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
class OrderServiceImplTest {

    @Autowired
    private OrderService orderService;

    @MockBean
    private OrderRepository orderRepository;

    @BeforeEach
    void setup(){
        when(orderRepository.save(any(Order.class))).thenReturn(OrderCreator.orderValid());
        PageImpl<Order> associatePage = new PageImpl<>(List.of(OrderCreator.orderValid()));
        when(orderRepository.findAll(any(Specification.class), any(Pageable.class))).thenReturn(associatePage);
    }

    @Test
    public void shouldCreateOrderWhenSuccessful() {
        assertThatCode(() -> orderService.createOrder(OrderCreator.orderDTOValid())).doesNotThrowAnyException();
    }

    @Test
    public void shouldListAllOrdersSuccessfully(){
        Pageable pageable = Mockito.mock(Pageable.class);
        OrderCriteria criteria = new OrderCriteria("123", "01-01-2022 12:00:00");

        String expectedOrderNumber = OrderCreator.orderDTOValid().getOrderNumber(); // Criar um objeto OrderDTO válido

        List<Order> mockOrderList = Collections.singletonList(OrderCreator.orderValid());
        Page<Order> mockOrderPage = new PageImpl<>(mockOrderList, pageable, mockOrderList.size());

        when(orderRepository.findAll(criteria.toSpecification(), pageable)).thenReturn(mockOrderPage);

        Page<OrderDTO> resultPage = orderService.listAll(pageable, criteria);

        assertThat(resultPage).isNotNull();
        assertThat(resultPage.getContent()).hasSize(1);
        assertThat(resultPage.getContent().get(0).getOrderNumber()).isEqualTo(expectedOrderNumber);
        assertThat(resultPage.getTotalPages()).isEqualTo(1);
    }

    @Test
    void shouldOrderCreationFailWhenExceedingTheOrderLimit() {

        List<ProductDTO> productDTO = List.of(
                new ProductDTO("Produto 1", "10.1", "10"),
                new ProductDTO("Produto 2", "20.0", "1"),
                new ProductDTO("Produto 3", "15.75", "4")
        );

        LimitOrderExcededException limitOrderExcededException = assertThrows(LimitOrderExcededException.class,
                () -> orderService.createOrder(OrderCreator.orderDTOInvalid(productDTO)));
        assertThat(limitOrderExcededException.getMessage()).isEqualTo("Numero de pedidos excedeu o permitido");
    }

    @Test
    void shouldOrderCreationFailWhenControlNumberAlreadyRegistered() {
        OrderDTO orderDTO = OrderCreator.orderDTOValid();

        when(orderRepository.existsByOrderNumber(orderDTO.getOrderNumber())).thenReturn(true);

        ExistingResourceException existingResourceException = assertThrows(ExistingResourceException.class,
                () -> orderService.createOrder(orderDTO));
        assertThat(existingResourceException.getMessage()).isEqualTo("Numero de controle ja cadastrado");
    }


}