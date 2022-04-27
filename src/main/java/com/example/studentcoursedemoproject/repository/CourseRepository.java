package com.example.studentcoursedemoproject.repository;

import com.example.studentcoursedemoproject.entity.Course;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {
    Optional<Course> findByCourseName(String courseName);

    //      Optional<Course> findByCompositeKey(CourseCompositeKey key);

    @Query("SELECT q FROM Course q WHERE LOWER(q.courseName) LIKE LOWER(CONCAT('%', :courseName, '%'))")
    Page<Course> searchCourse(String courseName, Pageable pageable);
}
