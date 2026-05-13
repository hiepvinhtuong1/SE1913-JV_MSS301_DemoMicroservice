package com.example.accountservice.service;

import com.example.accountservice.dto.AccountResponse;

public interface AccountService {

    AccountResponse getAccountById(Long id);
}
