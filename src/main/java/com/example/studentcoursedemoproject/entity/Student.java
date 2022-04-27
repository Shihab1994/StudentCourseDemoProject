package com.example.studentcoursedemoproject.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "STUDENT")
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class Student extends BaseEntity {

    private static final Long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "STUDENT_ID")
    private Long studentId;

    @Column(name = "STUDENT_NAME", length = 150)
    private String studentName;

    @Column(name = "STUDENT_EMAIL", length = 100)
    private String studentEmail;

    @Column(name = "STUDENT_REG_ID", length = 30)
    private String studentRegId;

    @Column(name = "STUDENT_AGE", length = 3)
    private Integer studentAge;

    @Column(name = "STUDENT_ADDRESS", length = 300)
    private String studentAddress;

    @Column(name = "STUDENT_PHONE", length = 20)
    private String studentPhone;

    @Column(name = "STUDENT_IMAGE")
    private String studentImage;

    @OneToMany(
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    @JoinColumn(name = "STUDENT_FILE_ENCLOSER_ID", nullable = false, insertable = false, updatable = false)
   private List<StudentFileEncloser> enclosers;

    public void addEnclosers(List<StudentFileEncloser> enclosers) {
        if (this.enclosers == null) {
            this.enclosers = new ArrayList<>();
        }
        this.enclosers.addAll(enclosers);
    }

}
