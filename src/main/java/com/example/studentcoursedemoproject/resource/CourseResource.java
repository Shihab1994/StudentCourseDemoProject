package com.example.studentcoursedemoproject.resource;


import com.example.studentcoursedemoproject.dto.CourseDto;
import com.example.studentcoursedemoproject.dto.StudentDto;
import com.example.studentcoursedemoproject.entity.Course;
import com.example.studentcoursedemoproject.entity.Student;
import com.example.studentcoursedemoproject.enums.RecordStatus;
import com.example.studentcoursedemoproject.exception.ResourceNotFoundException;
import com.example.studentcoursedemoproject.service.CourseService;
import com.example.studentcoursedemoproject.utils.CommonDataHelper;
import com.example.studentcoursedemoproject.utils.PaginatedResponse;
import com.example.studentcoursedemoproject.validation.CourseValidator;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ValidationUtils;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;
import java.util.stream.Collectors;

import static com.example.studentcoursedemoproject.constants.MessageConstants.*;
import static com.example.studentcoursedemoproject.exception.ApiError.fieldError;
import static com.example.studentcoursedemoproject.utils.ResponseBuilder.error;
import static com.example.studentcoursedemoproject.utils.ResponseBuilder.success;
import static com.example.studentcoursedemoproject.utils.StringUtils.isNotEmpty;
import static org.springframework.http.ResponseEntity.badRequest;
import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/course")
public class CourseResource {

    private final CourseService service;

    private final CommonDataHelper helper;

    private final CourseValidator validator;

    private final String[] sortable = {"courseId", "courseName"};

    @PostMapping("/save")
    @ApiOperation(value = "Save course", response = CourseDto.class)
    public ResponseEntity<JSONObject> save(@Valid @RequestBody CourseDto dto, BindingResult bindingResult) {

        ValidationUtils.invokeValidator(validator, dto, bindingResult);

        if(bindingResult.hasErrors()){
            return badRequest().body(error(fieldError(bindingResult)).getJson());
        }
        Course course = service.save(dto);
        return ok(success(CourseDto.from(course), COURSE_SAVE).getJson());
    }

    @PutMapping("/update")
    @ApiOperation(value = "Update course", response = CourseDto.class)
    public ResponseEntity<JSONObject> update(@Valid @RequestBody CourseDto dto, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return badRequest().body(error(fieldError(bindingResult)).getJson());
        }
        Course course = service.update(dto, RecordStatus.DRAFT);
        return ok(success(CourseDto.from(course), COURSE_UPDATE).getJson());
    }

    @GetMapping("/find/{id}")
    @ApiOperation(value = "Find course by Id", response = CourseDto.class)
    public ResponseEntity<JSONObject> findById(@PathVariable Integer id) {

        Course course = service.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Course Id: " + id));

        return ok(success(CourseDto.from(course)).getJson());
    }

    @GetMapping("/all")
    @ApiOperation(value = "Get all courses", response = CourseDto.class)
    public ResponseEntity<JSONObject> findAll(
            @RequestParam(value = "sortBy", defaultValue = "") String sortBy) {
        Map<String, Object> filterMap = new HashMap<>();

        List<CourseDto> responses = service.findAll(sortable, sortBy, filterMap)
                .stream()
                .map(CourseDto::from)
                .collect(Collectors.toList());

        return ok(success(responses).getJson());

    }


    @GetMapping("/list")
    @ApiOperation(value = "Get Course", response = CourseDto.class)
    public ResponseEntity<JSONObject> getList(
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "size", defaultValue = "10") Integer size,
            @RequestParam(value = "sortBy", defaultValue = "") String sortBy,
            @RequestParam(value = "courseName", defaultValue = "") String courseName
    ) {
        PaginatedResponse response = new PaginatedResponse();

        Map<String, Object> courseMap = service.getList(courseName, page, size, sortBy);
        List<Course> students = (List<Course>)courseMap.get("lists");
        List<CourseDto> responses = students
                .stream()
                .map(CourseDto::from)
                .collect(Collectors.toList());

        helper.getCommonData(page, size, courseMap, response, responses);

        return ok(success(response).getJson());
    }

    @PutMapping("/change-record-status/{id}/{status}")
    @ApiOperation(value = "Change record status", response = CourseDto.class)
    public ResponseEntity<JSONObject> changeRecordStatus(@PathVariable Long id, @PathVariable RecordStatus status) {

        service.updateRecordStatus(id, status);
        return ok(success(null, status.toString().toLowerCase() + " successfully").getJson());
    }

}
