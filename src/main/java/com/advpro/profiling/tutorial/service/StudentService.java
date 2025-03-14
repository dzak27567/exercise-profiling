package com.advpro.profiling.tutorial.service;

import com.advpro.profiling.tutorial.model.Student;
import com.advpro.profiling.tutorial.model.StudentCourse;
import com.advpro.profiling.tutorial.repository.StudentCourseRepository;
import com.advpro.profiling.tutorial.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author muhammad.khadafi
 */
@Service
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private StudentCourseRepository studentCourseRepository;

    public List<StudentCourse> getAllStudentsWithCourses() {
        List<StudentCourse> allStudentCourses = studentCourseRepository.findAll();

        // Create a map of student IDs to students for efficient lookup
        Map<Long, Student> studentMap = studentRepository.findAll().stream()
                .collect(Collectors.toMap(Student::getId, student -> student));

        // Set the student object for each student course
        allStudentCourses.forEach(sc -> sc.setStudent(studentMap.get(sc.getStudent().getId())));

        return allStudentCourses;
    }

    public Optional<Student> findStudentWithHighestGpa() {
        List<Student> students = studentRepository.findAll();
        Student highestGpaStudent = null;
        double highestGpa = Double.MIN_VALUE; // Mulai dari nilai minimum double

        for (Student student : students) {
            if (student != null && student.getGpa() > highestGpa) {
                highestGpa = student.getGpa();
                highestGpaStudent = student;
            }
        }

        return Optional.ofNullable(highestGpaStudent);
    }


    public String joinStudentNames() {
        List<Student> students = studentRepository.findAll();
        StringBuilder result = new StringBuilder();

        for (int i = 0; i < students.size(); i++) {
            result.append(students.get(i).getName());
            if (i < students.size() - 1) {
                result.append(", ");
            }
        }

        return result.toString();
    }
}

