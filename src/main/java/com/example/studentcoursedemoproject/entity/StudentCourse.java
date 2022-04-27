package com.example.studentcoursedemoproject.entity;

import com.example.studentcoursedemoproject.enums.CourseStatus;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "STUDENT_COURSE")
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class StudentCourse extends BaseEntity {

    @Column(name = "IS_ACTIVE")
    Boolean isActive;
    @EmbeddedId
    private CourseCompositeKey courseCompositeKey;
    private CourseStatus courseStatus;

}
