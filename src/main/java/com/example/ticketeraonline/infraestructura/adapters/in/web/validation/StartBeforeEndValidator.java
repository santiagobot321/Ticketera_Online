package com.example.ticketeraonline.infraestructura.adapters.in.web.validation;

import com.example.ticketeraonline.infraestructura.adapters.in.web.dto.EventRequest;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class StartBeforeEndValidator implements ConstraintValidator<StartBeforeEnd, EventRequest> {

    @Override
    public boolean isValid(EventRequest eventRequest, ConstraintValidatorContext context) {
        if (eventRequest.getStartDateTime() == null || eventRequest.getEndDateTime() == null) {
            return true; // Let @NotNull handle nulls
        }
        return eventRequest.getStartDateTime().isBefore(eventRequest.getEndDateTime());
    }
}
