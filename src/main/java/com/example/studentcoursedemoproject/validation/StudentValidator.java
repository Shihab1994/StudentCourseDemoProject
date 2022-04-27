package com.example.studentcoursedemoproject.validation;

import com.example.studentcoursedemoproject.dto.StudentDto;
import com.example.studentcoursedemoproject.entity.Student;
import com.example.studentcoursedemoproject.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.Optional;

import static com.example.studentcoursedemoproject.constants.MessageConstants.ALREADY_EXIST;
import static org.apache.logging.log4j.util.Strings.isNotEmpty;

@Component
@RequiredArgsConstructor
public class StudentValidator implements Validator {

    private final StudentService service;

    @Override
    public boolean supports(Class<?> clazz) {
        return StudentDto.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors error) {
        StudentDto dto = (StudentDto) target;

        if (isNotEmpty(dto.getStudentRegId())) {
            Optional<Student> student = service.findByRegId(dto.getStudentRegId());
            if (student.isPresent()) {
                error.rejectValue("studentRegId", null, ALREADY_EXIST);
            }
        }
    }
}
