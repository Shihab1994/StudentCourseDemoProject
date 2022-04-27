package com.example.studentcoursedemoproject.service;

import com.example.studentcoursedemoproject.dto.CourseDto;
import com.example.studentcoursedemoproject.entity.Course;
import com.example.studentcoursedemoproject.enums.RecordStatus;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface CourseService {

    Course save(CourseDto dto);

    Course update(CourseDto dto, RecordStatus status);

    Optional<Course> findById(int id);

    Optional<Course> findByCourseName(String courseName);

    List<Course> findAll(String[] sortable, String sortBy, Map<String, Object> filterMap);

    Map<String, Object> getList(String courseName, Integer page, Integer size, String sortBy);

    void updateRecordStatus(Long id, RecordStatus status);

}
