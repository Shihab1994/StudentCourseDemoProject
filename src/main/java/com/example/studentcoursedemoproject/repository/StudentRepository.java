package com.example.studentcoursedemoproject.repository;

import com.example.studentcoursedemoproject.entity.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
    Optional<Student> findByStudentRegId(String studentRegId);

    @Query("SELECT q FROM Student q WHERE LOWER(q.studentName) LIKE LOWER(CONCAT('%', :studentName, '%'))")
    Page<Student> searchStudent(String studentName, Pageable pageable);

}
