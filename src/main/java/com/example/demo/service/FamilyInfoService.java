package com.example.demo.service;

import com.example.demo.entity.FamilyInfo;
import com.example.demo.entity.Student;
import com.example.demo.repository.FamilyInfoRepository;
import com.example.demo.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FamilyInfoService {
    @Autowired
    private FamilyInfoRepository familyInfoRepository;

    @Autowired
    private StudentRepository studentRepository; // 需要注入 StudentRepository

    public FamilyInfo addFamilyInfo(Long studentId, FamilyInfo familyInfo) {
        Student student = studentRepository.findById(studentId).orElse(null);
        if (student == null) {
            return null; // 学生不存在
        }
        // 检查是否已经存在家庭信息
        if (student.getFamilyInfo() != null) {
            return null; // 已存在家庭信息
        }

        familyInfo.setStudent(student);
        return familyInfoRepository.save(familyInfo);
    }

    public FamilyInfo getFamilyInfoByStudentId(Long studentId) {
        return familyInfoRepository.findByStudentId(studentId);
    }

    public FamilyInfo updateFamilyInfo(Long studentId, FamilyInfo familyInfo) {
        FamilyInfo existingFamilyInfo = familyInfoRepository.findByStudentId(studentId);
        if (existingFamilyInfo == null) {
            return null; // 家庭信息不存在
        }
        // 更新字段
        existingFamilyInfo.setAddress(familyInfo.getAddress());
        existingFamilyInfo.setPopulation(familyInfo.getPopulation());
        existingFamilyInfo.setTotalIncome(familyInfo.getTotalIncome());
        // ... 更新其他字段

        return familyInfoRepository.save(existingFamilyInfo);
    }

    public void deleteFamilyInfo(Long studentId) {
        Student student = studentRepository.findById(studentId).orElse(null);
        if (student != null && student.getFamilyInfo() != null) {
            FamilyInfo familyInfo = student.getFamilyInfo();
            familyInfo.setStudent(null); // 解除关联关系
            student.setFamilyInfo(null);
            familyInfoRepository.delete(familyInfo);
        }
    }

    public List<FamilyInfo> getAllFamilyInfos() {
        return familyInfoRepository.findAll();
    }
}