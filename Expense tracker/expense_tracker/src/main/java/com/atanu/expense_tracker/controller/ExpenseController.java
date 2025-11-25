package com.atanu.expense_tracker.controller;

import com.atanu.expense_tracker.dto.*;
import com.atanu.expense_tracker.service.ExpenseService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/v1/expenses")
@CrossOrigin
public class ExpenseController {

    private final ExpenseService service;

    public ExpenseController(ExpenseService service) {
        this.service = service;
    }

    @PostMapping
    public ExpenseResponseDTO create(@Valid @RequestBody ExpenseRequestDTO dto) {
        return service.create(dto);
    }

    @GetMapping("/{id}")
    public ExpenseResponseDTO get(@PathVariable Long id) {
        return service.get(id);
    }

    @GetMapping
    public PagedResponse<ExpenseResponseDTO> filter(
            @RequestParam(required = false) String category,
            @RequestParam(required = false) Double minAmount,
            @RequestParam(required = false) Double maxAmount,
            @RequestParam(required = false) LocalDate startDate,
            @RequestParam(required = false) LocalDate endDate,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return service.filter(category, minAmount, maxAmount, startDate, endDate, page, size);
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id) {
        service.delete(id);
        return "Deleted";
    }
}
