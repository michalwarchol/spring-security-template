package com.example.springsecurity.student;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class StudentControllerTest {

    @Test
    void getStudentById_shouldReturnStudent(){
        //arrange
        StudentController studentController = new StudentController();

        //act
        Student student = studentController.getStudentById(2);

        //assert
        assertEquals(2, student.getId());
        assertEquals("Joe", student.getName());
    }

    @Test
    void getStudentById_shouldReturnIllegalStateException(){
        //arrange
        StudentController studentController = new StudentController();

        //act
        //assert
        assertThrows(IllegalStateException.class, ()->studentController.getStudentById(-1));
    }
}
