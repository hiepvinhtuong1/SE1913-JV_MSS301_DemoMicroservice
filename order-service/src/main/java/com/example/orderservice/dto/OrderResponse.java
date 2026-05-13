package com.example.orderservice.dto;

import com.example.orderservice.entity.OrderStatus;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderResponse {

    private Long id;
    private Long accountId;
    private Instant createdAt;
    private BigDecimal totalAmount;
    private OrderStatus status;
    private List<OrderDetailResponse> details;
}
