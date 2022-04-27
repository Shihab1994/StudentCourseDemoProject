package com.example.studentcoursedemoproject.helper;

import com.example.studentcoursedemoproject.entity.Course;
import com.example.studentcoursedemoproject.enums.RecordStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CourseHelper {

    public void getSaveData(Course course){
        course.setRecordStatus(RecordStatus.DRAFT);
    }

    public void getUpdatedData(Course course, RecordStatus status){
        course.setRecordStatus(status);
    }
}
