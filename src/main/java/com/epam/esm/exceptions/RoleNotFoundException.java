package com.epam.esm.exceptions;

import jakarta.persistence.EntityNotFoundException;
import lombok.experimental.StandardException;

@StandardException
public class RoleNotFoundException extends EntityNotFoundException {
}
