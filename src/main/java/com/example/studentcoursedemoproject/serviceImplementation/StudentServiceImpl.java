package com.example.studentcoursedemoproject.serviceImplementation;

import com.example.studentcoursedemoproject.dto.StudentDto;
import com.example.studentcoursedemoproject.entity.Student;
import com.example.studentcoursedemoproject.entity.StudentFileEncloser;
import com.example.studentcoursedemoproject.enums.RecordStatus;
import com.example.studentcoursedemoproject.exception.ResourceNotFoundException;
import com.example.studentcoursedemoproject.helper.GetListHelper;
import com.example.studentcoursedemoproject.helper.StudentHelper;
import com.example.studentcoursedemoproject.repository.StudentRepository;
import com.example.studentcoursedemoproject.service.StorageService;
import com.example.studentcoursedemoproject.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {

    private final StudentRepository repository;
    private final StudentHelper helper;
    private final StorageService storageService;

    private final EntityManager em;


    @Override
    public Student save(StudentDto dto) {

        Student student = dto.to();

        List<MultipartFile> files = dto.getMultipleDocuments().getFiles();
        List<StudentFileEncloser> enclosers = new ArrayList<>();

        for (MultipartFile f : files) {
            storageService.store(f.getOriginalFilename(), f);

            StudentFileEncloser encloser = new StudentFileEncloser();
            encloser.setEncloserName(f.getName());
            encloser.setEncloserType(f.getContentType());
            encloser.setEncloserUrl(storageService.getFileUrl());
            enclosers.add(encloser);
        }

        student.addEnclosers(enclosers);
        helper.setBaseData(student, RecordStatus.DRAFT, false);
        return repository.save(student);
    }


    @Override
    @Transactional
    public Student update(StudentDto dto, RecordStatus status){
        Student student = repository.findById(dto.getStudentId())
                .orElseThrow(() -> new ResourceNotFoundException("id: " + dto.getStudentId()));
        dto.update(dto, student);
        helper.getUpdatedData(student, status);
        Student updatedStudent = repository.save(student);
        return updatedStudent;
    }

    @Override
    public Optional<Student> findById(int id) {
        Optional<Student> student= repository.findById((long) id);
        return student;
    }

    @Override
    public Optional<Student> findByRegId(String studentRegId) {
        Optional<Student> student = repository.findByStudentRegId(studentRegId);
        return student;
    }

    @Override
    public List<Student> findAll(String[] sortable, String sortBy, Map<String, Object> filterMap) {
        return new GetListHelper<Student>(em, Student.class).findAll(sortable, sortBy);
    }

    @Override
    public Map<String, Object> getList(String studentName, Integer page, Integer size, String sortBy) {
        GetListHelper<Student> helper = new GetListHelper<>(em, Student.class);

        return helper.getList(repository.searchStudent(studentName,
                helper.getPageable(sortBy, page, size)), page, size);

    }

    @Override
    @Transactional
    public void updateRecordStatus(Long id, RecordStatus status) {

        Student student = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Student Id: " + id));
        helper.getUpdatedData(student, status);
        repository.save(student);
    }


}
