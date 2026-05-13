package com.example.orchidservice.service;

import com.example.orchidservice.dto.OrchidResponse;
import com.example.orchidservice.entity.Orchid;
import com.example.orchidservice.exception.ResourceNotFoundException;
import com.example.orchidservice.repository.OrchidRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrchidServiceImpl implements OrchidService {

    private final OrchidRepository orchidRepository;

    @Override
    public OrchidResponse getOrchidById(Long id) {
        Orchid orchid = orchidRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Orchid not found with id: " + id));

        return new OrchidResponse(
                orchid.getId(),
                orchid.getName(),
                orchid.getPrice(),
                orchid.getQuantity(),
                orchid.getCategory().getName()
        );
    }
}
