package com.example.karatestudents.service;

import com.example.karatestudents.model.Student;
import com.example.karatestudents.model.Training;
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

    public void saveStudent(Student student) {
        studentRepository.save(student);
    }

    public List<Student> getAllStudents() {
       return studentRepository.findAll();
    }

    public Student getStudentById(Long id) {
        return studentRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    @Transactional
    public void updateStudent(Long id, Student updatedStudent) {
        Student student = studentRepository.getReferenceById(id);
        student.setName(updatedStudent.getName());
        student.setDateOfBirth(updatedStudent.getDateOfBirth());
        student.setStartedKarate(updatedStudent.getStartedKarate());
        student.setRank(updatedStudent.getRank());
        student.setStudent(updatedStudent.isStudent());
        student.setTrainingList(updatedStudent.getTrainingList());
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
