package com.atanu.expense_tracker.dto;

import jakarta.validation.constraints.*;
import java.time.LocalDate;

public class ExpenseUpdateDTO {

    @NotBlank(message = "Title cannot be blank")
    private String title;

    @NotNull(message = "Amount is required")
    @Positive(message = "Amount must be > 0")
    private Double amount;

    @NotBlank(message = "Category required")
    private String category;

    private LocalDate date;

    // getters & setters
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public Double getAmount() { return amount; }
    public void setAmount(Double amount) { this.amount = amount; }
    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }
    public LocalDate getDate() { return date; }
    public void setDate(LocalDate date) { this.date = date; }
}
