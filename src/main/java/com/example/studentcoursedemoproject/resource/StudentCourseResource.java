package com.example.studentcoursedemoproject.resource;

import com.example.studentcoursedemoproject.dto.StudentCourseDto;
import com.example.studentcoursedemoproject.entity.StudentCourse;
import com.example.studentcoursedemoproject.enums.RecordStatus;
import com.example.studentcoursedemoproject.service.StudentCourseService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.example.studentcoursedemoproject.constants.MessageConstants.COURSE_UPDATE;
import static com.example.studentcoursedemoproject.constants.MessageConstants.STUDENT_COURSE_SAVE;
import static com.example.studentcoursedemoproject.utils.ResponseBuilder.success;
import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/student/course")
public class StudentCourseResource {

    private final StudentCourseService service;

    private final String[] sortable = {"isActive", "courseCompositeKey"};

    @PostMapping("/save")
    @ApiOperation(value = "Save course of a student", response = StudentCourseDto.class)
    public ResponseEntity<JSONObject> save(@Valid @RequestBody StudentCourseDto dto) {
        StudentCourse studentCourse = service.save(dto);
        return ok(success(StudentCourseDto.from(studentCourse), STUDENT_COURSE_SAVE).getJson());
    }

    @PutMapping("/update")
    @ApiOperation(value = "Save course of a student", response = StudentCourseDto.class)
    public ResponseEntity<JSONObject> update(@Valid @RequestBody StudentCourseDto dto) {

        StudentCourse studentCourse = service.update(dto, RecordStatus.DRAFT);
        return ok(success(StudentCourseDto.from(studentCourse), COURSE_UPDATE).getJson());
    }

    @GetMapping("/all")
    @ApiOperation(value = "Get all list of taken courses", response = StudentCourseDto.class)
    public ResponseEntity<JSONObject> findAll(
            @RequestParam(value = "sortBy", defaultValue = "") String sortBy) {
        Map<String, Object> filterMap = new HashMap<>();

        List<StudentCourseDto> responses = service.findAll(sortable, sortBy, filterMap)
                .stream()
                .map(StudentCourseDto::from)
                .collect(Collectors.toList());

        return ok(success(responses).getJson());

    }

}
