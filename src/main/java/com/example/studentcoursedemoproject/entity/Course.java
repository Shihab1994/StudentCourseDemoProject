package com.example.studentcoursedemoproject.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;


@Data
@Entity
@Table(name = "COURSE")
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class Course extends BaseEntity {

    private static final Long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "COURSE_ID")
    private Long courseId;

    @Column(name = "COURSE_NAME", length = 150)
    private String courseName;

    @Column(name = "COURSE_DURATION", length = 50)
    private String courseDuration;

    @Column(name = "COURSE_TEACHER", length = 150)
    private String courseTeacher;

    @Column(name = "COURSE_TYPE", length = 50)
    private String courseType;

}
