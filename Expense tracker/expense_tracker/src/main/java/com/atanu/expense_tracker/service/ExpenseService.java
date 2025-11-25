package com.atanu.expense_tracker.service;

import com.atanu.expense_tracker.dto.*;
import com.atanu.expense_tracker.exception.NotFoundException;
import com.atanu.expense_tracker.model.Expense;
import com.atanu.expense_tracker.repository.ExpenseRepository;
import com.atanu.expense_tracker.specification.ExpenseSpecification;

import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service layer for Expense operations: create, read, filter (paged),
 * full update (PUT), partial update (PATCH) and delete.
 */
@Service
@Transactional
public class ExpenseService {

    private final ExpenseRepository repository;

    public ExpenseService(ExpenseRepository repository) {
        this.repository = repository;
    }

    /**
     * Create a new Expense from request DTO.
     */
    public ExpenseResponseDTO create(ExpenseRequestDTO dto) {
        Expense expense = new Expense(
                dto.getTitle(),
                dto.getAmount(),
                dto.getCategory(),
                dto.getDate()
        );
        Expense saved = repository.save(expense);
        return toDto(saved);
    }

    /**
     * Get single expense by id.
     */
    @Transactional(readOnly = true)
    public ExpenseResponseDTO get(Long id) {
        Expense e = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Expense not found with id: " + id));
        return toDto(e);
    }

    /**
     * Filter with optional category, amount range and date range.
     * Returns paged response of ExpenseResponseDTO.
     */
    @Transactional(readOnly = true)
    public PagedResponse<ExpenseResponseDTO> filter(String category,
                                                    Double minAmount,
                                                    Double maxAmount,
                                                    LocalDate startDate,
                                                    LocalDate endDate,
                                                    int page,
                                                    int size,
                                                    Sort sort) {
        Pageable pageable = PageRequest.of(Math.max(0, page), Math.max(1, size), sort == null ? Sort.by("date").descending() : sort);

        Specification<Expense> spec = Specification.where(ExpenseSpecification.hasCategory(category))
                .and(ExpenseSpecification.hasMinAmount(minAmount))
                .and(ExpenseSpecification.hasMaxAmount(maxAmount))
                .and(ExpenseSpecification.inDateRange(startDate, endDate));

        Page<Expense> result = repository.findAll(spec, pageable);

        List<ExpenseResponseDTO> content = result.stream()
                .map(this::toDto)
                .collect(Collectors.toList());

        return new PagedResponse<>(
                content,
                result.getNumber(),
                result.getSize(),
                result.getTotalElements(),
                result.getTotalPages()
        );
    }

    /**
     * Convenience filter method that uses default sorting (date desc).
     */
    public PagedResponse<ExpenseResponseDTO> filter(String category,
                                                    Double minAmount,
                                                    Double maxAmount,
                                                    LocalDate startDate,
                                                    LocalDate endDate,
                                                    int page,
                                                    int size) {
        return filter(category, minAmount, maxAmount, startDate, endDate, page, size, Sort.by("date").descending());
    }

    /**
     * Full update (PUT) - replaces expense fields with DTO values.
     */
    public ExpenseResponseDTO update(Long id, ExpenseUpdateDTO dto) {
        Expense existing = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Expense not found with id: " + id));

        existing.setTitle(dto.getTitle());
        existing.setAmount(dto.getAmount());
        existing.setCategory(dto.getCategory());
        existing.setDate(dto.getDate());

        Expense saved = repository.save(existing);
        return toDto(saved);
    }

    /**
     * Partial update (PATCH) - only non-null fields are updated.
     */
    public ExpenseResponseDTO patch(Long id, ExpensePatchDTO dto) {
        Expense existing = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Expense not found with id: " + id));

        if (dto.getTitle() != null) existing.setTitle(dto.getTitle());
        if (dto.getAmount() != null) existing.setAmount(dto.getAmount());
        if (dto.getCategory() != null) existing.setCategory(dto.getCategory());
        if (dto.getDate() != null) existing.setDate(dto.getDate());

        Expense saved = repository.save(existing);
        return toDto(saved);
    }

    /**
     * Delete an expense by id.
     */
    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new NotFoundException("Expense not found with id: " + id);
        }
        repository.deleteById(id);
    }

    /**
     * Map entity to response DTO.
     */
    private ExpenseResponseDTO toDto(Expense e) {
        return new ExpenseResponseDTO(e.getId(), e.getTitle(), e.getAmount(), e.getCategory(), e.getDate());
    }
}
