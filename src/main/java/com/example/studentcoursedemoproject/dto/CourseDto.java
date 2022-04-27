package com.example.studentcoursedemoproject.dto;

import com.example.studentcoursedemoproject.entity.Course;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;


@Data
@NoArgsConstructor
public class CourseDto {

    private Long courseId;

    private String courseName;

    private String courseDuration;

    private String courseTeacher;

    private String courseType;

    public static CourseDto from (Course course){

        CourseDto courseDto = new CourseDto();

        courseDto.setCourseId(course.getCourseId());
        courseDto.setCourseName(course.getCourseName());
        courseDto.setCourseDuration(course.getCourseDuration());
        courseDto.setCourseTeacher(course.getCourseTeacher());
        courseDto.setCourseType(course.getCourseType());
        return courseDto;
    }

    public Course to(){
        Course course = new Course();
        BeanUtils.copyProperties(this, course);
        return course;
    }

    public void update(CourseDto dto, Course course){
        BeanUtils.copyProperties(dto, course);
    }

}


