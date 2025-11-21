package com.atanu.expense_tracker.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.atanu.expense_tracker.model.Expense;
import com.atanu.expense_tracker.repository.ExpenseRepository;

@Service
public class ExpenseService {
    private final ExpenseRepository repository;

    public ExpenseService(ExpenseRepository repository) {
        this.repository = repository;
    }
    public Expense addExpense(Expense expense) {
        return repository.save(expense);
    }
    public List<Expense> getAllExpenses() {
        return repository.findAll();
    }
    public Expense getExpense(Long id) {
        return repository.findById(id).orElse(null);
    }
    public Expense updateExpense(Long id, Expense Expense) {
        Expense old = repository.findById(id).orElse(null);
        if (old != null) {
            old.setTitle(Expense.getTitle());
            old.setAmount(Expense.getAmount());
            old.setDate(Expense.getDate());
            old.setCategory(Expense.getCategory());
            return repository.save(old);
        }
        return null;
    }
    public String deleteExpense(Long id) {
        repository.deleteById(id);
        return "Expense with id " + id + " has been deleted.";
    }
}
