package br.com.gurumatch.customerorders.services.impl.specification;

import br.com.gurumatch.customerorders.models.Order;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class OrderCriteria implements CriteriaSpecification<Order> {

    private final String orderNumber;
    private final String registrationDate;

    public OrderCriteria(String orderNumber, String registrationDate) {
        this.orderNumber = orderNumber;
        this.registrationDate = registrationDate;
    }

    @Override
    public Specification<Order> toSpecification() {
        Specification<Order> specification = Specification.where(null);

        if (Objects.nonNull(orderNumber)) {
            specification = specification.and((root, query, criteriaBuilder) ->
                    criteriaBuilder.equal(root.get("orderNumber"), orderNumber));
        }

        if (Objects.nonNull(registrationDate)) {
            LocalDateTime parsedDate = LocalDateTime.parse(registrationDate, DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss"));
            specification = specification.and((root, query, criteriaBuilder) ->
                    criteriaBuilder.equal(root.get("registrationDate"), parsedDate));
        }
        return specification;
    }


}
