package com.example.orderservice.client;

import com.example.orderservice.dto.OrchidResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "orchid-service")
public interface OrchidClient {

    @GetMapping("/api/orchids/{id}")
    OrchidResponse getOrchidById(@PathVariable Long id);
}
