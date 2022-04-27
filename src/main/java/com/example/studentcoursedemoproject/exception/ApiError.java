package com.example.studentcoursedemoproject.exception;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
public class ApiError implements Serializable {

    private static final long serialVersionUID = 1L;

    public static Map<String, String> fieldError(BindingResult bindingResult) {
        Map<String, String> fieldErrors = new HashMap<>();
        if (bindingResult.hasErrors()) {
            List<FieldError> errors = bindingResult.getFieldErrors();
            errors.forEach(e -> {
                fieldErrors.put(e.getField(), e.getDefaultMessage());
            });
        }
        return fieldErrors;
    }
}


