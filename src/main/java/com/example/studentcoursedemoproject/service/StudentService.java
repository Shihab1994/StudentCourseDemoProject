package com.example.studentcoursedemoproject.service;

import com.example.studentcoursedemoproject.dto.StudentDto;
import com.example.studentcoursedemoproject.entity.Student;
import com.example.studentcoursedemoproject.enums.RecordStatus;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface StudentService {
    Student save(StudentDto dto);
    Student update(StudentDto dto, RecordStatus status);
    Optional<Student>findById(int id);
    void updateRecordStatus(Long id, RecordStatus status);
    Optional<Student> findByRegId(String studentRegId);
    List<Student> findAll(String[] sortable, String sortBy, Map<String, Object> filterMap);
    Map<String, Object> getList(String studentName, Integer page, Integer size, String sortBy);
}
