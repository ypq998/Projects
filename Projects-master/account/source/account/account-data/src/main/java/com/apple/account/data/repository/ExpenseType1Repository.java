package com.apple.account.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.apple.account.data.entity.ExpenseType1;

@Repository
public interface ExpenseType1Repository extends JpaRepository<ExpenseType1, Long> {
  
}
