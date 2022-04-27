package com.example.studentcoursedemoproject.dto;

import com.example.studentcoursedemoproject.entity.Student;
import com.example.studentcoursedemoproject.utils.MultipleDocuments;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
public class StudentDto {

    private Long studentId;

    @NotBlank
    private String studentName;

    @NotBlank
    private String studentEmail;

    @NotBlank
    private String studentRegId;

    private int studentAge;

    private String studentAddress;

    private String studentPhone;

    private String studentImage;

//    private List<StudentFileEncloser> enclosers;

    @Valid
    private MultipleDocuments multipleDocuments;

    public static StudentDto from(Student student) {

        StudentDto studentDto = new StudentDto();
        studentDto.setStudentId(student.getStudentId());
        studentDto.setStudentName(student.getStudentName());
        studentDto.setStudentEmail(student.getStudentEmail());
        studentDto.setStudentRegId(student.getStudentRegId());
        studentDto.setStudentAge(student.getStudentAge());
        studentDto.setStudentAddress(student.getStudentAddress());
        studentDto.setStudentPhone(student.getStudentPhone());
        studentDto.setStudentImage(student.getStudentImage());
        return studentDto;
    }

    public Student to() {
        Student student = new Student();
        BeanUtils.copyProperties(this, student);
        return student;
    }

    public void update(StudentDto dto, Student student) {
        BeanUtils.copyProperties(dto, student);
    }

}
