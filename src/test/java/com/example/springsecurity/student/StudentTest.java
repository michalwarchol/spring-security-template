package com.example.springsecurity.student;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;

@SpringBootTest
public class StudentTest {

    @Test
    void getId_shouldReturnStudentId(){
        //arrange
        Student student = new Student(6, "Michal");

        //act
        int provided_id = student.getId();

        //assert
        assertEquals(6, provided_id);
    }

    @Test
    void getName_shouldReturnStudentName(){
        //arrange
        Student student = new Student(6, "Michal");

        //act
        String provided_name = student.getName();

        //assert
        assertEquals("Michal", provided_name);
    }
}
