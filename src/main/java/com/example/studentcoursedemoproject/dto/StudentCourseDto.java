package com.example.studentcoursedemoproject.dto;

import com.example.studentcoursedemoproject.entity.Course;
import com.example.studentcoursedemoproject.entity.CourseCompositeKey;
import com.example.studentcoursedemoproject.entity.StudentCourse;
import com.example.studentcoursedemoproject.enums.CourseStatus;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

import javax.persistence.Column;

@Data
@NoArgsConstructor
public class StudentCourseDto {

    @Column(name = "STUDENT_ID_2")
    private Long studentId;

    @Column(name = "COURSE_ID_2")
    private Long courseId;

    Boolean isActive;

    private CourseStatus courseStatus;

    public static StudentCourseDto from(StudentCourse studentCourse){

        StudentCourseDto studentCourseDto = new StudentCourseDto();

        studentCourseDto.setCourseStatus(studentCourse.getCourseStatus());
        studentCourseDto.setIsActive(studentCourse.getIsActive());

        return studentCourseDto;
    }

    public StudentCourse to() {
        StudentCourse studentCourse = new StudentCourse();
        studentCourse.setIsActive(isActive);
        studentCourse.setCourseStatus(courseStatus);
        return studentCourse;
    }

    public void update(StudentCourseDto dto, StudentCourse studentCourse) {
        BeanUtils.copyProperties(dto, studentCourse);
    }

}
