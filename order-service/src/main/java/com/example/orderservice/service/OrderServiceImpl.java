package com.example.orderservice.service;

import com.example.orderservice.client.AccountClient;
import com.example.orderservice.client.OrchidClient;
import com.example.orderservice.dto.CreateOrderItemRequest;
import com.example.orderservice.dto.CreateOrderRequest;
import com.example.orderservice.dto.OrchidResponse;
import com.example.orderservice.dto.OrderDetailResponse;
import com.example.orderservice.dto.OrderResponse;
import com.example.orderservice.entity.Order;
import com.example.orderservice.entity.OrderDetail;
import com.example.orderservice.entity.OrderStatus;
import com.example.orderservice.exception.BusinessException;
import com.example.orderservice.exception.ResourceNotFoundException;
import com.example.orderservice.repository.OrderRepository;
import feign.FeignException;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final AccountClient accountClient;
    private final OrchidClient orchidClient;

    @Override
    public List<OrderResponse> getAllOrders() {
        return orderRepository.findAll().stream()
                .map(this::toResponse)
                .toList();
    }

    @Override
    public OrderResponse getOrderById(Long id) {
        return orderRepository.findById(id)
                .map(this::toResponse)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found with id: " + id));
    }

    @Override
    @Transactional
    public OrderResponse createOrder(CreateOrderRequest request) {
        validateAccountExists(request.getAccountId());

        Order order = new Order();
        order.setAccountId(request.getAccountId());
        order.setCreatedAt(Instant.now());
        order.setStatus(OrderStatus.CREATED);
        order.setTotalAmount(BigDecimal.ZERO);

        BigDecimal totalAmount = BigDecimal.ZERO;
        for (CreateOrderItemRequest item : request.getItems()) {
            OrchidResponse orchid = getOrchid(item.getOrchidId());
            if (item.getQuantity() > orchid.getQuantity()) {
                throw new BusinessException("Not enough stock for orchid id: " + item.getOrchidId());
            }

            BigDecimal lineTotal = orchid.getPrice().multiply(BigDecimal.valueOf(item.getQuantity()));
            OrderDetail detail = new OrderDetail(
                    null,
                    orchid.getId(),
                    orchid.getName(),
                    item.getQuantity(),
                    orchid.getPrice(),
                    lineTotal,
                    null
            );

            order.addDetail(detail);
            totalAmount = totalAmount.add(lineTotal);
        }

        order.setTotalAmount(totalAmount);
        return toResponse(orderRepository.save(order));
    }

    private void validateAccountExists(Long accountId) {
        try {
            accountClient.getAccountById(accountId);
        } catch (FeignException.NotFound exception) {
            throw new ResourceNotFoundException("Account not found with id: " + accountId);
        } catch (FeignException exception) {
            throw new BusinessException("Account service is unavailable");
        }
    }

    private OrchidResponse getOrchid(Long orchidId) {
        try {
            return orchidClient.getOrchidById(orchidId);
        } catch (FeignException.NotFound exception) {
            throw new ResourceNotFoundException("Orchid not found with id: " + orchidId);
        } catch (FeignException exception) {
            throw new BusinessException("Orchid service is unavailable");
        }
    }

    private OrderResponse toResponse(Order order) {
        List<OrderDetailResponse> details = order.getDetails().stream()
                .map(detail -> new OrderDetailResponse(
                        detail.getId(),
                        detail.getOrchidId(),
                        detail.getOrchidName(),
                        detail.getQuantity(),
                        detail.getUnitPrice(),
                        detail.getLineTotal()
                ))
                .toList();

        return new OrderResponse(
                order.getId(),
                order.getAccountId(),
                order.getCreatedAt(),
                order.getTotalAmount(),
                order.getStatus(),
                details
        );
    }
}
