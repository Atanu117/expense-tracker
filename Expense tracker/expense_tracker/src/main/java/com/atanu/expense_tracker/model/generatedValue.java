package com.atanu.expense_tracker.model;

import jakarta.persistence.GenerationType;

public @interface generatedValue {

    GenerationType strategy();

}
