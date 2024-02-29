package com.epam.esm.exceptions;

import jakarta.persistence.EntityNotFoundException;

public class CustomAccessDeniedException extends EntityNotFoundException {
    public CustomAccessDeniedException(String message) {
        super(message);
    }
}
