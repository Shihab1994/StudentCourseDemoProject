package com.example.studentcoursedemoproject.enums;

public enum CourseStatus {

    PASSED(0),
    FAILED(1),
    INCOMPLETE(2),
    RETAKE(3);

    private final Integer label;

    CourseStatus(Integer label) {
        this.label = label;
    }

    public Integer getLabel() {
        return label;
    }

}
