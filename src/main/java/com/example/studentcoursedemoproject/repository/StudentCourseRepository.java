package com.example.studentcoursedemoproject.repository;

import com.example.studentcoursedemoproject.entity.CourseCompositeKey;
import com.example.studentcoursedemoproject.entity.StudentCourse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StudentCourseRepository extends JpaRepository<StudentCourse, CourseCompositeKey> {

    Optional<StudentCourse> findByCourseCompositeKey(CourseCompositeKey key);
}
