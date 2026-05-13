package com.example.orderservice.dto;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrchidResponse {

    private Long id;
    private String name;
    private BigDecimal price;
    private Integer quantity;
    private String categoryName;
}
