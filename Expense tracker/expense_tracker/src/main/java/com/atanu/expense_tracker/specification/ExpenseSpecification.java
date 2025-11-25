package com.atanu.expense_tracker.specification;

import com.atanu.expense_tracker.model.Expense;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;

public class ExpenseSpecification {

    public static Specification<Expense> hasCategory(String category) {
        return (root, query, cb) ->
                category == null ? null : cb.equal(root.get("category"), category);
    }

    public static Specification<Expense> hasMinAmount(Double minAmount) {
        return (root, query, cb) ->
                minAmount == null ? null : cb.greaterThanOrEqualTo(root.get("amount"), minAmount);
    }

    public static Specification<Expense> hasMaxAmount(Double maxAmount) {
        return (root, query, cb) ->
                maxAmount == null ? null : cb.lessThanOrEqualTo(root.get("amount"), maxAmount);
    }

    public static Specification<Expense> inDateRange(LocalDate start, LocalDate end) {
        return (root, query, cb) -> {
            if (start != null && end != null)
                return cb.between(root.get("date"), start, end);
            if (start != null)
                return cb.greaterThanOrEqualTo(root.get("date"), start);
            if (end != null)
                return cb.lessThanOrEqualTo(root.get("date"), end);

            return null;
        };
    }
}
