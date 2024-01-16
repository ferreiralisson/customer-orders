package br.com.gurumatch.customerorders.dtos;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.List;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class OrderDTO {
    private String id;
    @NotBlank(message = "numero de controle obrigatorio")
    private String orderNumber;
    private String registrationDate;
    private ClientDTO client;
    private String totalOrderValue;
    private List<ProductDTO> products;
}