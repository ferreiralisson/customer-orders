package br.com.dbserver.customerorders.dto;

import jakarta.validation.constraints.NotBlank;

public record OrderRequestDTO (
        @NotBlank(message = "numero de controle obrigatorio") String numberControl,
        @NotBlank(message = "nome do cliente obrigatorio") String name,
        @NotBlank(message = "codigo do cliente obrigatorio") String valueControl,
        String amount,
        @NotBlank(message = "codigo do cliente obrigatorio") String clientId
) {
}
