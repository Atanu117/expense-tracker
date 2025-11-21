package com.atanu.expense_tracker.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.atanu.expense_tracker.model.Expense;

public interface ExpenseRepository extends JpaRepository<Expense, Long> {
    

}