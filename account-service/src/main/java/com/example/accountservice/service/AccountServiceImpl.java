package com.example.accountservice.service;

import com.example.accountservice.dto.AccountResponse;
import com.example.accountservice.entity.Account;
import com.example.accountservice.exception.ResourceNotFoundException;
import com.example.accountservice.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;

    @Override
    public AccountResponse getAccountById(Long id) {
        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Account not found with id: " + id));

        return new AccountResponse(
                account.getId(),
                account.getUsername(),
                account.getFullName(),
                account.getEmail(),
                account.getRole().getName()
        );
    }
}
