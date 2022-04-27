package com.example.studentcoursedemoproject.serviceImplementation;

import com.example.studentcoursedemoproject.dto.CourseDto;
import com.example.studentcoursedemoproject.entity.Course;
import com.example.studentcoursedemoproject.entity.CourseCompositeKey;
import com.example.studentcoursedemoproject.entity.Student;
import com.example.studentcoursedemoproject.enums.RecordStatus;
import com.example.studentcoursedemoproject.exception.ResourceNotFoundException;
import com.example.studentcoursedemoproject.helper.CourseHelper;
import com.example.studentcoursedemoproject.helper.GetListHelper;
import com.example.studentcoursedemoproject.repository.CourseRepository;
import com.example.studentcoursedemoproject.service.CourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CourseServiceImpl implements CourseService {

    private final CourseRepository repository;

    private final CourseHelper helper;

    private final EntityManager em;

    @Override
    @Transactional
    public Course save(CourseDto dto) {
        Course course = dto.to();
        helper.getSaveData(course);
        Course savedCourse = repository.save(course);
        return savedCourse;
    }

    @Override
    @Transactional
    public Course update(CourseDto dto, RecordStatus status){
        Course course = repository.findById(dto.getCourseId()).orElseThrow(() -> new ResourceNotFoundException("id: " + dto.getCourseId()));
        dto.update(dto, course);
        helper.getUpdatedData(course, status);
        Course updatedCourse = repository.save(course);
        return updatedCourse;
    }

    @Override
    public Optional<Course> findById(int id) {
        Optional<Course> course= repository.findById((long) id);
        return course;
    }

    @Override
    public Optional<Course> findByCourseName(String courseName) {
        Optional<Course> course = repository.findByCourseName(courseName);
        return course;
    }

    @Override
    public List<Course> findAll(String[] sortable, String sortBy, Map<String, Object> filterMap) {
        return new GetListHelper<Course>(em, Course.class).findAll(sortable, sortBy);
    }

    @Override
    public Map<String, Object> getList(String courseName, Integer page, Integer size, String sortBy) {
        GetListHelper<Course> helper = new GetListHelper<>(em, Course.class);

        return helper.getList(repository.searchCourse(courseName,
                helper.getPageable(sortBy, page, size)), page, size);
    }

    @Override
    @Transactional
    public void updateRecordStatus(Long id, RecordStatus status) {

        Course course = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Course Id: " + id));
        helper.getUpdatedData(course, status);
        repository.save(course);
    }

}
