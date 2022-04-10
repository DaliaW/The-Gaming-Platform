package com.example.demo.student;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

@Service
public class StudentService {

    @GetMapping
    public List<student> getStudents() {
        return List.of(
                new student(1L, "John", 21, LocalDate.of(2000, Month.JANUARY, 1), "Doe@gmail.com"),
                new student(2L, "Jane", 22, LocalDate.of(2001, Month.FEBRUARY, 2), "Jane@gmail.com"),
                new student(3L, "Jack", 23, LocalDate.of(2002, Month.MARCH, 3), "kaj@gmail.com")
        );
    }
}
