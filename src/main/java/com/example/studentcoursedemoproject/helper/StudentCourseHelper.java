package com.example.studentcoursedemoproject.helper;

import com.example.studentcoursedemoproject.entity.Course;
import com.example.studentcoursedemoproject.entity.CourseCompositeKey;
import com.example.studentcoursedemoproject.entity.Student;
import com.example.studentcoursedemoproject.entity.StudentCourse;
import com.example.studentcoursedemoproject.enums.RecordStatus;
import com.example.studentcoursedemoproject.repository.CourseRepository;
import com.example.studentcoursedemoproject.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class StudentCourseHelper {

    private final CourseRepository courseRepository;

    private final StudentRepository studentRepository;

    public void setBaseData(StudentCourse studentCourse, RecordStatus status, Boolean forUpdate) {
        studentCourse.setRecordStatus(status);
    }

    public void getSaveData(StudentCourse studentCourse) {
        studentCourse.setRecordStatus(RecordStatus.DRAFT);
    }

    public void getUpdatedData(StudentCourse studentCourse, RecordStatus status) {
        studentCourse.setRecordStatus(status);
    }

    public void setCompositeKey(StudentCourse studentCourse, Long studentId, Long courseId) {
        Student student = studentRepository.findById(studentId).orElseThrow();
        Course course = courseRepository.findById(courseId).orElseThrow();
        CourseCompositeKey key = new CourseCompositeKey(student, course);
        studentCourse.setCourseCompositeKey(key);
    }
}
