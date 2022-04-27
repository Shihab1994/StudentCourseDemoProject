package com.example.studentcoursedemoproject.validation;

import com.example.studentcoursedemoproject.dto.CourseDto;
import com.example.studentcoursedemoproject.entity.Course;
import com.example.studentcoursedemoproject.service.CourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.Optional;

import static com.example.studentcoursedemoproject.constants.MessageConstants.ALREADY_EXIST;
import static org.apache.logging.log4j.util.Strings.isNotEmpty;

@Component
@RequiredArgsConstructor
public class CourseValidator implements Validator{

    private final CourseService service;

    @Override
    public boolean supports(Class<?> clazz) {
        return CourseDto.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors error) {
        CourseDto dto = (CourseDto) target;

        if (isNotEmpty(dto.getCourseName())) {
            Optional<Course> course = service.findByCourseName(dto.getCourseName());
            if (course.isPresent()) {
                error.rejectValue("courseName", null, ALREADY_EXIST);
            }
        }
    }

}
