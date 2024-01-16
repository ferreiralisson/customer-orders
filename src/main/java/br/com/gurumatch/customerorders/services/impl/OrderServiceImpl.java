package br.com.gurumatch.customerorders.services.impl;

import br.com.gurumatch.customerorders.conveters.OrderDtoToOrderMapper;
import br.com.gurumatch.customerorders.conveters.OrderToOrderDtoMapper;
import br.com.gurumatch.customerorders.dtos.OrderDTO;
import br.com.gurumatch.customerorders.models.Order;
import br.com.gurumatch.customerorders.models.Product;
import br.com.gurumatch.customerorders.repositories.OrderRepository;
import br.com.gurumatch.customerorders.services.OrderService;
import br.com.gurumatch.customerorders.services.impl.discount.FivePercent;
import br.com.gurumatch.customerorders.services.impl.discount.FullValueDiscount;
import br.com.gurumatch.customerorders.services.impl.discount.TenPercentDiscount;
import br.com.gurumatch.customerorders.services.impl.specification.OrderCriteria;
import br.com.gurumatch.customerorders.services.impl.validator.LimitOrder;
import br.com.gurumatch.customerorders.services.impl.validator.RegisteredControllerNumber;
import br.com.gurumatch.customerorders.services.impl.validator.ValidatorOrders;
import jakarta.transaction.Transactional;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final OrderDtoToOrderMapper orderMapper;

    private final OrderToOrderDtoMapper orderDtoMapper;

    public OrderServiceImpl(OrderRepository orderRepository, OrderDtoToOrderMapper orderMapper, OrderToOrderDtoMapper orderDtoMapper) {
        this.orderRepository = orderRepository;
        this.orderMapper = orderMapper;
        this.orderDtoMapper = orderDtoMapper;
    }

    @Override
    @Transactional
    @CacheEvict(value = "orders", allEntries = true)
    public OrderDTO createOrder(OrderDTO orderDTO) {
        //Valid Order
        LimitOrder limitOrder = new LimitOrder(orderDTO);
        RegisteredControllerNumber registeredControllerNumber = new RegisteredControllerNumber(orderRepository, orderDTO);
        validate(limitOrder);
        validate(registeredControllerNumber);

        //Convert OrderDTO in Order for save object
        Order mappedOrder = orderMapper.map(orderDTO, new Order());

        List<Product> products = mappedOrder.getProducts();
        if (products != null && !products.isEmpty()) {
            products.forEach(product -> product.setOrder(mappedOrder));
        }

        //Discount on the total amount
        orderDTO.setTotalOrderValue(mappedOrder.getTotalOrderValue().toString());
        mappedOrder.setTotalOrderValue(fullValueDiscount(orderDTO));

        return orderDtoMapper.map(orderRepository.save(mappedOrder), new OrderDTO());
    }

    @Override
    @Cacheable(value = "orders")
    public Page<OrderDTO> listAll(Pageable pageable, OrderCriteria criteria) {
        Page<Order> orderpage = orderRepository.findAll(criteria.toSpecification(), pageable);

        List<OrderDTO> orders = orderpage
                .stream()
                .map(order -> orderDtoMapper.map(order, new OrderDTO()))
                .collect(Collectors.toList());

        return new PageImpl<>(orders, pageable, orderpage.getTotalPages());
    }

    private BigDecimal fullValueDiscount(OrderDTO orderDTO){
        FullValueDiscount fullValueDiscount = new FivePercent(new TenPercentDiscount());
        return fullValueDiscount.discount(orderDTO);
    }


    private void validate(ValidatorOrders validatorOrders){
        validatorOrders.Valid();
    }
}
