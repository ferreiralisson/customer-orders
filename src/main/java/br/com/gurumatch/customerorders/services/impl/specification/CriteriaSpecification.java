package br.com.gurumatch.customerorders.services.impl.specification;

import org.springframework.data.jpa.domain.Specification;

public interface CriteriaSpecification<T> {
    Specification<T> toSpecification();
}
