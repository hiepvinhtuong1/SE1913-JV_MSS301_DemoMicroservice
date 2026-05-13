package com.example.accountservice.config;

import com.example.accountservice.entity.Account;
import com.example.accountservice.entity.Role;
import com.example.accountservice.repository.AccountRepository;
import com.example.accountservice.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final AccountRepository accountRepository;
    private final RoleRepository roleRepository;

    @Override
    public void run(String... args) {
        if (accountRepository.count() > 0) {
            return;
        }

        Role customer = roleRepository.findByName("CUSTOMER")
                .orElseGet(() -> roleRepository.save(new Role(null, "CUSTOMER")));
        Role admin = roleRepository.findByName("ADMIN")
                .orElseGet(() -> roleRepository.save(new Role(null, "ADMIN")));

        accountRepository.save(new Account(null, "alice", "Alice Nguyen", "alice@example.com", customer));
        accountRepository.save(new Account(null, "bob", "Bob Tran", "bob@example.com", customer));
        accountRepository.save(new Account(null, "admin", "Store Admin", "admin@example.com", admin));
    }
}
