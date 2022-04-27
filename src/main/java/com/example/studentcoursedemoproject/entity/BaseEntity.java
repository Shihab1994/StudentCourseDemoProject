package com.example.studentcoursedemoproject.entity;

import com.example.studentcoursedemoproject.enums.RecordStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

@Getter
@Setter
@MappedSuperclass
public abstract class BaseEntity implements Serializable {

    private static final Long serialVersionUID = 1L;

    @JsonIgnore
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "CREATED_AT", updatable = false)
    protected Date createdAt;

    @JsonIgnore
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "UPDATED_AT")
    protected Date updatedAt;

    @Version
    @JsonIgnore
    @Column(name = "RECORD_VERSION")
    private Integer recordVersion;

    //default one, auto increment for each operation like update
    @JsonIgnore
    @Enumerated(EnumType.STRING)
    @Column(name = "RECORD_STATUS")
    private RecordStatus recordStatus;

    @Type(type = "uuid-char")
    @Column(name = "CREATOR", updatable = false)
    private UUID createdBy;

    @Type(type = "uuid-char")
    @Column(name = "UPDATOR")
    private UUID updatedBy;

    @PrePersist
    public void prePersist() {
        this.createdAt = new Date();
    }

    @PreUpdate
    public void preUpdate() {
        this.updatedAt = new Date();
    }
}
