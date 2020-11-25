package com.example.springsecurity.student;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("api/v1/students")
public class StudentController {
    private static final List<Student> DB = Arrays.asList(
            new Student(1, "Donald"),
            new Student(2, "Joe"),
            new Student(3, "Kayne"));

    @GetMapping(path="{id}")
    public Student getStudentById(@PathVariable("id") Integer id){
        return DB.stream()
                .filter(student->id.equals(student.getId()))
                .findFirst()
                .orElseThrow(()->new IllegalStateException("Student "+id+" does not exist!"));
    }
}
