package com.example.orchidservice.config;

import com.example.orchidservice.entity.Category;
import com.example.orchidservice.entity.Orchid;
import com.example.orchidservice.repository.CategoryRepository;
import com.example.orchidservice.repository.OrchidRepository;
import java.math.BigDecimal;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final CategoryRepository categoryRepository;
    private final OrchidRepository orchidRepository;

    @Override
    public void run(String... args) {
        if (orchidRepository.count() > 0) {
            return;
        }

        Category phalaenopsis = categoryRepository.findByName("Phalaenopsis")
                .orElseGet(() -> categoryRepository.save(new Category(null, "Phalaenopsis")));
        Category cattleya = categoryRepository.findByName("Cattleya")
                .orElseGet(() -> categoryRepository.save(new Category(null, "Cattleya")));

        orchidRepository.save(new Orchid(null, "White Moon Orchid", new BigDecimal("25.00"), 20, phalaenopsis));
        orchidRepository.save(new Orchid(null, "Purple Rain Orchid", new BigDecimal("32.50"), 12, phalaenopsis));
        orchidRepository.save(new Orchid(null, "Golden Cattleya", new BigDecimal("45.00"), 8, cattleya));
    }
}
