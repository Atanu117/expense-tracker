package com.atanu.expense_tracker.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.atanu.expense_tracker.model.Expense;
import com.atanu.expense_tracker.service.ExpenseService;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/api/expenses")
public class ExpenseController {
    private final ExpenseService service;

    public ExpenseController(ExpenseService Service) {
        this.service = Service;
    }

    @PostMapping
    public Expense create(@RequestBody Expense expense) {
        //TODO: process POST request
        return service.addExpense(expense);
    }
    
    @GetMapping
    public List<Expense> getAll() {
        return service.getAllExpenses();
    }
    
    @GetMapping("/{id}")
    public Expense getById(@PathVariable Long id) {
        return service.getExpense(id);
    }

    @PutMapping("/{id}")
    public Expense update(@PathVariable Long id, @RequestBody Expense expense) {
        return service.updateExpense(id, expense);
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id) {
        return service.deleteExpense(id);
    }
}
