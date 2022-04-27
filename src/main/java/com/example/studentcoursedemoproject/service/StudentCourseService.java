package com.example.studentcoursedemoproject.service;

import com.example.studentcoursedemoproject.dto.StudentCourseDto;
import com.example.studentcoursedemoproject.entity.Course;
import com.example.studentcoursedemoproject.entity.StudentCourse;
import com.example.studentcoursedemoproject.enums.RecordStatus;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface StudentCourseService {

    StudentCourse save(StudentCourseDto dto);

    StudentCourse update(StudentCourseDto dto, RecordStatus status);

    Optional<StudentCourse> findByCourseCompositeKey(Long studentId, Long courseId);

    List<StudentCourse> findAll(String[] sortable, String sortBy, Map<String, Object> filterMap);

}
