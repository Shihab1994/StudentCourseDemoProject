package com.example.studentcoursedemoproject.enums;

public enum RecordStatus {

    DRAFT(0),
    ACTIVE(1),
    DELETED(2);

    private final Integer label;

    RecordStatus(Integer label) {
        this.label = label;
    }

    public Integer getLabel() {
        return label;
    }
}
