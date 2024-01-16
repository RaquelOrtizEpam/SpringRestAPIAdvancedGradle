package com.epam.esm.exceptions;

import jakarta.persistence.EntityNotFoundException;
import lombok.experimental.StandardException;

@StandardException
public class GiftCertificationNotFoundException extends EntityNotFoundException{
    public GiftCertificationNotFoundException(String message) {
        super(message);
    }
}
