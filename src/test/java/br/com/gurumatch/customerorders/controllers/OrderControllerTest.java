package br.com.gurumatch.customerorders.controllers;

import br.com.gurumatch.customerorders.dtos.OrderDTO;
import br.com.gurumatch.customerorders.dtos.ProductDTO;
import br.com.gurumatch.customerorders.exceptions.ExistingResourceException;
import br.com.gurumatch.customerorders.helpers.Constants;
import br.com.gurumatch.customerorders.helpers.OrderCreator;
import br.com.gurumatch.customerorders.services.OrderService;
import br.com.gurumatch.customerorders.services.impl.specification.OrderCriteria;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatCode;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;


@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @InjectMocks
    private OrderController orderController;

    @Mock
    private OrderService orderService;

    @BeforeEach
    void setup(){
        PageImpl<OrderDTO> orderDTOPage = new PageImpl<>(List.of(OrderCreator.orderDTOValid()));
        when(orderService.listAll(any(), any())).thenReturn(orderDTOPage);
        when(orderService.createOrder(any(OrderDTO.class))).thenReturn(OrderCreator.orderDTOValid());
    }

    @Test
    public void shouldCreateOrderSuccessfully(){
        OrderDTO orderDTO = OrderCreator.orderDTOValid();
        assertThatCode(() -> orderController.createOrder(orderDTO))
                .doesNotThrowAnyException();
        ResponseEntity<OrderDTO> entity = orderController.createOrder(orderDTO);
        assertThat(entity).isNotNull();
        assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.CREATED);
    }

    @Test
    public void shouldListAllOrdersSuccessfully(){
        Pageable pageable = Pageable.unpaged();
        String expectedOrderNumber = OrderCreator.orderDTOValid().getOrderNumber();
        OrderCriteria criteria = new OrderCriteria(expectedOrderNumber, OrderCreator.orderDTOValid().getRegistrationDate());
        Page<OrderDTO> associatePage = orderController.listAll(pageable, criteria).getBody();

        assertThat(associatePage).isNotEmpty().hasSize(1);
        assertThat(associatePage.toList()).isNotEmpty();
        assertThat(associatePage.toList().get(0).getOrderNumber()).isEqualTo(expectedOrderNumber);
    }

    @Test
    public void shouldOrderCreationFailWhenExceedingTheOrderLimit() throws Exception {

        List<ProductDTO> productDTO = List.of(
                new ProductDTO("Produto 1", "10.1", "10"),
                new ProductDTO("Produto 2", "20.0", "1"),
                new ProductDTO("Produto 3", "15.75", "4")
        );

        OrderDTO orderDTO = OrderCreator.orderDTOInvalid(productDTO);

        mockMvc.perform(MockMvcRequestBuilders.post(Constants.API_VERSION + "/customer_orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(orderDTO)))
                .andExpect(MockMvcResultMatchers.status().isForbidden());
    }

}