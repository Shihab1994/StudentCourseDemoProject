package com.example.studentcoursedemoproject.helper;

import com.example.studentcoursedemoproject.entity.Student;
import com.example.studentcoursedemoproject.enums.RecordStatus;
import com.example.studentcoursedemoproject.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class StudentHelper {

    private final StudentRepository repository;

    //    private final ActiveUserContext context;
//
    public void getSaveData(Student student) {
//        student.setCreatedBy(UUID.fromString(context.getLoggedInUserId()));
        student.setRecordStatus(RecordStatus.DRAFT);
    }

    public void getUpdatedData(Student student, RecordStatus status) {
//        student.setCreatedBy(UUID.fromString(context.getLoggedInUserId()));
        student.setRecordStatus(status);
    }

    public void setBaseData(Student student, RecordStatus status, Boolean forUpdate) {
        student.setRecordStatus(status);
    }

}
