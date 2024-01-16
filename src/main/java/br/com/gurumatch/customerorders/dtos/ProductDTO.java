package br.com.gurumatch.customerorders.dtos;

public record ProductDTO(
        String name,
        String unitPrice,
        String quantity
) {
}
