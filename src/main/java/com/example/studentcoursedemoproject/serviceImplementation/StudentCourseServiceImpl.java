package com.example.studentcoursedemoproject.serviceImplementation;

import com.example.studentcoursedemoproject.dto.StudentCourseDto;
import com.example.studentcoursedemoproject.entity.Course;
import com.example.studentcoursedemoproject.entity.CourseCompositeKey;
import com.example.studentcoursedemoproject.entity.Student;
import com.example.studentcoursedemoproject.entity.StudentCourse;
import com.example.studentcoursedemoproject.enums.RecordStatus;
import com.example.studentcoursedemoproject.helper.GetListHelper;
import com.example.studentcoursedemoproject.helper.StudentCourseHelper;
import com.example.studentcoursedemoproject.repository.CourseRepository;
import com.example.studentcoursedemoproject.repository.StudentCourseRepository;
import com.example.studentcoursedemoproject.repository.StudentRepository;
import com.example.studentcoursedemoproject.service.StudentCourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StudentCourseServiceImpl implements StudentCourseService {

    private final StudentCourseRepository repository;

    private final StudentRepository studentRepository;

    private final CourseRepository courseRepository;

    private final StudentCourseHelper helper;

    private final EntityManager em;

    @Override
    @Transactional
    public StudentCourse save(StudentCourseDto dto) {
        StudentCourse studentCourse = dto.to();
        helper.setCompositeKey(studentCourse, dto.getStudentId(), dto.getCourseId());
        helper.getSaveData(studentCourse);
        StudentCourse savedCourse = repository.save(studentCourse);
        return savedCourse;
    }

    @Override
    @Transactional
    public StudentCourse update(StudentCourseDto dto, RecordStatus status) {

        StudentCourse studentCourse = findByCourseCompositeKey(dto.getStudentId(), dto.getCourseId()).orElseThrow();
        dto.update(dto, studentCourse);
        helper.getUpdatedData(studentCourse, status);
        StudentCourse updatedCourse = repository.save(studentCourse);
        return updatedCourse;
    }

    @Override
    public Optional<StudentCourse> findByCourseCompositeKey(Long studentId, Long courseId) {
        Student student = studentRepository.findById(studentId).orElseThrow();
        Course course = courseRepository.findById(courseId).orElseThrow();
        CourseCompositeKey key = new CourseCompositeKey(student, course);
        Optional<StudentCourse> studentCourse = repository.findByCourseCompositeKey(key);
        return studentCourse;
    }

    @Override
    public List<StudentCourse> findAll(String[] sortable, String sortBy, Map<String, Object> filterMap) {
        return new GetListHelper<StudentCourse>(em, StudentCourse.class).findAll(sortable, sortBy);
    }

}
