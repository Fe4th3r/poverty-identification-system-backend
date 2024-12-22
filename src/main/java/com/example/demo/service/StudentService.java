package com.example.demo.service;

import com.example.demo.entity.Student;
import com.example.demo.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class StudentService {
    @Autowired
    private StudentRepository studentRepository;

    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    public List<Student> searchStudentsByName(String name) {
        return studentRepository.findByNameContaining(name);
    }

    // 添加 addStudent 方法
    public Student addStudent(Student student) {
        // 可以在这里添加一些业务逻辑，例如检查学号是否已存在
        if (studentRepository.findByStuId(student.getStuId()) != null) {
            return null; // 学号已存在
        }
        return studentRepository.save(student);
    }

    public Student getStudentById(Long id) {
        return studentRepository.findById(id).orElse(null);
    }

    public Student getStudentByIdWithAllCollections(Long id) {
        return studentRepository.findByIdWithAllCollections(id).orElse(null);
    }

    @Transactional
    public Student updateStudent(Student student) {
        // 只能更新部分信息，不允许更新学号等关键信息
        Student existingStudent = studentRepository.findByIdWithAllCollections(student.getId()).orElse(null);
        if (existingStudent != null) {
            existingStudent.setName(student.getName());
            existingStudent.setGender(student.getGender());
            existingStudent.setIdCard(student.getIdCard());
            existingStudent.setMajor(student.getMajor());
            existingStudent.setPovertyLevel(student.getPovertyLevel());
            // ... 更新其他字段
            return studentRepository.save(existingStudent);
        }
        return null;
    }

    public void deleteStudent(Long id) {
        studentRepository.deleteById(id);
    }
}