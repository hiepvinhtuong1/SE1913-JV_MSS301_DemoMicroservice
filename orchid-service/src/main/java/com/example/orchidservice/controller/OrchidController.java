package com.example.orchidservice.controller;

import com.example.orchidservice.dto.OrchidResponse;
import com.example.orchidservice.service.OrchidService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/orchids")
@RequiredArgsConstructor
public class OrchidController {

    private final OrchidService orchidService;

    @GetMapping("/{id}")
    public OrchidResponse getOrchidById(@PathVariable Long id) {
        return orchidService.getOrchidById(id);
    }
}
