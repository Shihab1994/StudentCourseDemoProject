package com.example.studentcoursedemoproject.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Data
@Embeddable
@NoArgsConstructor
public class CourseCompositeKey implements Serializable {

    private static final Long serialVersionUID = 1L;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "STUDENT_ID")
    private Student student;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "COURSE_ID")
    private Course courseId;

    public CourseCompositeKey(Student student, Course courseId) {
        this.student = student;
        this.courseId = courseId;
    }

    @Override
    public String toString() {
        return String.format("CourseCompositeKey{studentId=%s, courseId=%s}",
                student.getStudentId(), this.courseId);
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (!(object instanceof CourseCompositeKey)) return false;

        CourseCompositeKey that = (CourseCompositeKey) object;

        return this.getStudent().getStudentId().equals(that.getStudent().getStudentId())
                && this.courseId.equals(that.courseId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(getStudent().getStudentId(), this.courseId);
    }
}