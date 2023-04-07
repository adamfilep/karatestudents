package com.example.karatestudents.controller;

import com.example.karatestudents.model.Student;
import com.example.karatestudents.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/students")
public class StudentController {

    private StudentService studentService;

    @Autowired
    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @PostMapping
    public void saveStudent(Student student) {
        studentService.saveStudent(student);
    }

    @GetMapping
    public List<Student> getAllStudents() {
        return studentService.getAllStudents();
    }

    @GetMapping("/{id}")
    public Student getStudentById(@PathVariable("id") Long id) {
        return studentService.getStudentById(id);
    }

    @PutMapping("/{id}")
    public void updateStudent(@PathVariable("id") Long id, @RequestBody Student updatedStudent) {
        studentService.updateStudent(id, updatedStudent);
    }

    @DeleteMapping("/{id}")
    public void deleteStudent(@PathVariable("id") Long id) {
        studentService.deleteStudentById(id);
    }

}
