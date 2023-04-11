package com.example.karatestudents.controller;

import com.example.karatestudents.model.Student;
import com.example.karatestudents.model.dto.StudentDto;
import com.example.karatestudents.service.StudentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/students")
@Tag(name = "Operations on students")
public class StudentController {

    private StudentService studentService;

    @Autowired
    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @Operation(summary = "Save new student")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "New student saved")
    })
    @PostMapping
    public void saveStudent(@Valid @RequestBody StudentDto studentDto) {
        studentService.saveStudent(studentDto);
    }

    @Operation(summary = "Get all students")
    @ApiResponses(value = {
           @ApiResponse(responseCode = "200", description = "Students found")
    })
    @GetMapping
    public List<Student> getAllStudents() {
        return studentService.getAllStudents();
    }

    @Operation(summary = "Get student with a specific id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Student found")
    })
    @GetMapping("/{id}")
    public Student getStudentById(@PathVariable("id") Long id) {
        return studentService.getStudentById(id);
    }

    @Operation(summary = "Update student with a specific id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Student updated")
    })
    @PutMapping("/{id}")
    public void updateStudent(@PathVariable("id") Long id, @Valid @RequestBody StudentDto updatedStudentDto) {
        studentService.updateStudent(id, updatedStudentDto);
    }

    @Operation(summary = "Delete student with a specific id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Student deleted")
    })
    @DeleteMapping("/{id}")
    public void deleteStudent(@PathVariable("id") Long id) {
        studentService.deleteStudentById(id);
    }
}
