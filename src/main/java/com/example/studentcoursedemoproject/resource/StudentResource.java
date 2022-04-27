package com.example.studentcoursedemoproject.resource;

import com.example.studentcoursedemoproject.dto.StudentDto;
import com.example.studentcoursedemoproject.entity.Student;
import com.example.studentcoursedemoproject.enums.RecordStatus;
import com.example.studentcoursedemoproject.exception.ResourceNotFoundException;
import com.example.studentcoursedemoproject.service.StudentService;
import com.example.studentcoursedemoproject.utils.CommonDataHelper;
import com.example.studentcoursedemoproject.utils.PaginatedResponse;
import com.example.studentcoursedemoproject.validation.StudentValidator;
import io.swagger.annotations.ApiOperation;
import org.json.simple.JSONObject;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import java.util.*;
import java.util.stream.Collectors;

import static com.example.studentcoursedemoproject.constants.MessageConstants.*;
import static com.example.studentcoursedemoproject.utils.ResponseBuilder.success;
import static org.apache.logging.log4j.util.Strings.isNotEmpty;
import static org.springframework.http.ResponseEntity.badRequest;
import static org.springframework.http.ResponseEntity.ok;
import static com.example.studentcoursedemoproject.utils.ResponseBuilder.error;
import static com.example.studentcoursedemoproject.exception.ApiError.fieldError;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/student")
public class StudentResource {

    private final StudentService service;
    private final CommonDataHelper helper;

    private final String[] sortable = {"studentId", "studentName"};

    @PostMapping(
            path = "/save",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    @ApiOperation(value = "Save student",  response = StudentDto.class)
    public ResponseEntity<JSONObject> save(@ModelAttribute @Valid StudentDto dto) {

        if (!Objects.isNull(dto.getMultipleDocuments())) {
            String[] fileTypes = {"jpg", "png", "PNG", "jpeg"};
            Map<Integer, String> errors = dto.getMultipleDocuments().validate(1000000000, fileTypes);
            if (errors.size() > 0) {
                return ok(error(errors, "File types error occurred").getJson());
            }
        }

        if (isNotEmpty(dto.getStudentRegId())) {
            Optional<Student> student = service.findByRegId(dto.getStudentRegId());
            if (student.isPresent()) {
//                error.rejectValue("studentRegId", null, ALREADY_EXIST);
                return ok(error(null,  ALREADY_EXIST).getJson());

            }
        }

        Student student = service.save(dto);
        return ok(success(StudentDto.from(student), STUDENT_SAVE).getJson());
    }


//    @PostMapping("/save")
//    @ApiOperation(value = "Save student", response = StudentDto.class)
//    public ResponseEntity<JSONObject> save(@Valid @RequestBody StudentDto dto, BindingResult bindingResult) {
//
//        ValidationUtils.invokeValidator(validator, dto, bindingResult);
//
//        if(bindingResult.hasErrors()){
//            return badRequest().body(error(fieldError(bindingResult)).getJson());
//        }
//        Student student = service.save(dto);
//        return ok(success(StudentDto.from(student), STUDENT_SAVE).getJson());
//    }


    @PutMapping("/update")
    @ApiOperation(value = "update student", response = StudentDto.class)
    public ResponseEntity<JSONObject> update(@Valid @RequestBody StudentDto dto, BindingResult bindingResult) {

        if(bindingResult.hasErrors()){
            return badRequest().body(error(fieldError(bindingResult)).getJson());
        }
        Student student = service.update(dto, RecordStatus.DRAFT);
        return ok(success(StudentDto.from(student), STUDENT_UPDATE).getJson());
    }

    @GetMapping("/find/{id}")
    @ApiOperation(value = "Find by Id", response = StudentDto.class)
    public ResponseEntity<JSONObject> findById(@PathVariable Integer id) {

        Student student = service.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Student Id: " + id));
        return ok(success(StudentDto.from(student)).getJson());
    }

    @PutMapping("/change-record-status/{id}/{status}")
    @ApiOperation(value = "Change record status", response = StudentDto.class)
    public ResponseEntity<JSONObject> changeRecordStatus(@PathVariable Long id, @PathVariable RecordStatus status) {

         service.updateRecordStatus(id, status);
        return ok(success(null, status.toString().toLowerCase() + " successfully").getJson());
    }

    @GetMapping("/all")
    @ApiOperation(value = "Get all students", response = StudentDto.class)
    public ResponseEntity<JSONObject> findAll(
            @RequestParam(value = "sortBy", defaultValue = "") String sortBy) {
        Map<String, Object> filterMap = new HashMap<>();

        List<StudentDto> responses = service.findAll(sortable, sortBy, filterMap)
                .stream()
                .map(StudentDto::from)
                .collect(Collectors.toList());

        return ok(success(responses).getJson());

    }


    @GetMapping("/list")
    @ApiOperation(value = "Get Student", response = StudentDto.class)
    public ResponseEntity<JSONObject> getList(
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "size", defaultValue = "10") Integer size,
            @RequestParam(value = "sortBy", defaultValue = "") String sortBy,
            @RequestParam(value = "studentName", defaultValue = "") String studentName
    ) {
        PaginatedResponse response = new PaginatedResponse();

        Map<String, Object> studentMap = service.getList(studentName, page, size, sortBy);
        List<Student> students = (List<Student>)studentMap.get("lists");
        List<StudentDto> responses = students
                .stream()
                .map(StudentDto::from)
                .collect(Collectors.toList());

        helper.getCommonData(page, size, studentMap, response, responses);

        return ok(success(response).getJson());
    }


}
