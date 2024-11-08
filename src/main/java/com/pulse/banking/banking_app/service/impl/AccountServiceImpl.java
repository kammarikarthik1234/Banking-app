package com.pulse.banking.banking_app.service.impl;

import com.pulse.banking.banking_app.dto.AccountDto;
import com.pulse.banking.banking_app.mapper.AccountMapper;
import com.pulse.banking.banking_app.model.Account;
import com.pulse.banking.banking_app.repository.AccountRepository;
import com.pulse.banking.banking_app.service.AccountService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AccountServiceImpl implements AccountService {

    private AccountRepository accountRepository;


    public AccountServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public AccountDto createAccount(AccountDto accountDto) {
        Account account = AccountMapper.mapToAccount(accountDto);
        Account savedAccount= accountRepository.save(account);
        return AccountMapper.mapToAccountDto(savedAccount);
    }

    @Override
    public AccountDto getAccountById(Long id) {
        Account account = accountRepository.findById(id).orElseThrow(()-> new RuntimeException("Account doesn't exists"));
        return AccountMapper.mapToAccountDto(account);
    }

    @Override
    public AccountDto deposit(Long id, double amount) {
        Account account = accountRepository.findById(id).orElseThrow(()-> new RuntimeException("Account Doesn't exists"));
    double total = account.getBalance()+amount;
    account.setBalance(total);
   Account savedAccount = accountRepository.save(account);
    return AccountMapper.mapToAccountDto(savedAccount);
    }

    @Override
    public AccountDto withdraw(Long id, double amount) {
        Account account = accountRepository.findById(id).orElseThrow(()-> new RuntimeException("Account Doesn't exists"));
        if(account.getBalance() < amount){
            throw  new RuntimeException("Insufficient funds");
        }
        double finalAmount = account.getBalance() - amount;
        account.setBalance(finalAmount);
        Account savedAccount = accountRepository.save(account);
        return AccountMapper.mapToAccountDto(savedAccount);
    }

    @Override
    public List<AccountDto> getAllAccounts() {
        List<Account> accounts = accountRepository.findAll();
        return accounts.stream().map((account)->AccountMapper.mapToAccountDto(account)).collect((Collectors.toList()));
    }

    @Override
    public void deleteAccount(Long id) {
        Account account = accountRepository.findById(id).orElseThrow(()-> new RuntimeException("Account Doesn't exists"));
        accountRepository.deleteById(id);
    }
}
