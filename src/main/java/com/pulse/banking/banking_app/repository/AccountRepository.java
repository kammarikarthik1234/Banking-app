package com.pulse.banking.banking_app.repository;

import com.pulse.banking.banking_app.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Long> {
}
