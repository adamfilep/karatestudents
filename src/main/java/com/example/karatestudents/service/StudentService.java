package com.example.karatestudents.service;

import com.example.karatestudents.model.Student;
import com.example.karatestudents.model.Training;
import com.example.karatestudents.model.dto.StudentDto;
import com.example.karatestudents.model.enums.Rank;
import com.example.karatestudents.repository.StudentRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StudentService {

    private StudentRepository studentRepository;

    @Autowired
    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public void saveStudent(StudentDto studentDto) {
        Student student = Student.builder()
                .name(studentDto.getName())
                .dateOfBirth(studentDto.getDateOfBirth())
                .startedKarate(studentDto.getStartedKarate())
                .rank(Rank.valueOf(studentDto.getRank()))
                .isStudent(studentDto.isStudent())
                .build();
        studentRepository.save(student);
    }

    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    public Student getStudentById(Long id) {
        return studentRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    @Transactional
    public void updateStudent(Long id, StudentDto updatedStudentDto) {
        Student student = studentRepository.getReferenceById(id);
        student.setName(updatedStudentDto.getName());
        student.setDateOfBirth(updatedStudentDto.getDateOfBirth());
        student.setStartedKarate(updatedStudentDto.getStartedKarate());
        student.setRank(Rank.valueOf(updatedStudentDto.getRank()));
        student.setStudent(updatedStudentDto.isStudent());
        studentRepository.save(student);
    }

    @Transactional
    public void deleteStudentById(Long id) {
        Student student = studentRepository.getReferenceById(id);
        List<Training> trainingList = student.getTrainingList();
        if (trainingList != null) {
            for (Training training : trainingList) {
                training.getStudentList().remove(student);
            }
        }
        studentRepository.deleteById(id);
    }

}
