package com.example.demo.controller;

import com.example.demo.entity.Student;
import com.example.demo.service.StudentService;
import com.example.demo.service.ExcelExportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/student")
@CrossOrigin(origins = "*") // 允许所有来源的跨域请求
public class StudentController {
    @Autowired
    private StudentService studentService;

    @PostMapping
    public ResponseEntity<Object> addStudent(@RequestBody Student student) {
        Student newStudent = studentService.addStudent(student);
        Map<String, Object> response = new HashMap<>();
        if (newStudent != null) {
            response.put("message", "学生信息添加成功");
            response.put("student", newStudent);
            return ResponseEntity.ok(response);
        } else {
            response.put("message", "学生信息添加失败，学号已存在");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Student> getStudentById(@PathVariable Long id) {
        Student student = studentService.getStudentById(id);
        if (student != null) {
            return ResponseEntity.ok(student);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping
    public ResponseEntity<List<Student>> getAllStudents() {
        List<Student> students = studentService.getAllStudents();
        return ResponseEntity.ok(students);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateStudent(@PathVariable Long id, @RequestBody Student student) {
        student.setId(id); // 防止恶意更新 ID
        Student existingStudent = studentService.getStudentByIdWithAllCollections(id);
        if(existingStudent == null){
            Map<String, Object> response = new HashMap<>();
            response.put("message", "更新失败，学生不存在");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
        Student updatedStudent = studentService.updateStudent(student);
        Map<String, Object> response = new HashMap<>();
        if (updatedStudent != null) {
            response.put("message", "学生信息更新成功");
            response.put("student", updatedStudent);
            return ResponseEntity.ok(response);
        } else {
            response.put("message", "学生信息更新失败");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteStudent(@PathVariable Long id) {
        studentService.deleteStudent(id);
        Map<String, Object> response = new HashMap<>();
        response.put("message", "学生信息删除成功");
        return ResponseEntity.ok(response);
    }

    @GetMapping("/search")
    public ResponseEntity<List<Student>> searchStudentsByName(@RequestParam String name) {
        List<Student> students = studentService.searchStudentsByName(name);
        return ResponseEntity.ok(students);
    }
    @Autowired
    private ExcelExportService excelExportService;

    @GetMapping("/export")
    public ResponseEntity<byte[]> exportStudents() {
        try {
            List<Student> students = studentService.getAllStudents();
            byte[] excelBytes = excelExportService.exportStudentsToExcel(students);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            headers.setContentDispositionFormData("attachment", "students.xlsx"); // 设置文件名

            return new ResponseEntity<>(excelBytes, headers, HttpStatus.OK);
        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}