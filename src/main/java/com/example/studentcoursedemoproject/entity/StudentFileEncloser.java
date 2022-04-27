package com.example.studentcoursedemoproject.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@Data
@Entity
@DynamicInsert
@DynamicUpdate
@Table(name = "STUDENT_FILE_ENCLOSER")
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class StudentFileEncloser {

    private static final Long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "STUDENT_FILE_ENCLOSER_ID")
    private Long fileEncloserId;

    @Column(name = "ENCLOSER_NAME", length = 200)
    private String encloserName;

    @Column(name = "ENCLOSER_TYPE")
    private String encloserType;

    @Column(name = "ENCLOSER_URL")
    private String encloserUrl;

}
