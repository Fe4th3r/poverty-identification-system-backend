package com.example.demo.service;

import com.example.demo.entity.Student;
import com.example.demo.entity.FamilyInfo;
import com.example.demo.entity.SpecialGroup;
import com.example.demo.repository.StudentRepository;
import com.example.demo.repository.FamilyInfoRepository;
import com.example.demo.repository.SpecialGroupRepository;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.HashMap;

@Service
public class DataCleaningService {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private FamilyInfoRepository familyInfoRepository;

    @Autowired
    private SpecialGroupRepository specialGroupRepository;

    // 其他 Repository...

    public Map<String, Object> cleanStudentData() {
        List<Student> students = studentRepository.findAll();
        int cleanedCount = 0;
        int errorCount = 0;
        Map<String, Object> result = new HashMap<>();
        for (Student student : students) {
            boolean isCleaned = false;
            // 1. 清洗姓名 (例如：去除首尾空格)
            if (StringUtils.isNotBlank(student.getName()) && !student.getName().equals(student.getName().trim())) {
                student.setName(student.getName().trim());
                isCleaned = true;
            }

            // 2. 清洗性别 (例如：统一为 "男" 或 "女")
            if (StringUtils.isNotBlank(student.getGender())) {
                String cleanedGender = cleanGender(student.getGender());
                if (!student.getGender().equals(cleanedGender)) {
                    student.setGender(cleanedGender);
                    isCleaned = true;
                }
            }

            // 3. 检查身份证号 (例如：简单的长度校验)
            if (StringUtils.isNotBlank(student.getIdCard()) && student.getIdCard().length() != 18) {
                // 身份证号位数不对，记录错误信息，可以根据需求进行不同的处理，如删除该条数据或标记为错误数据
                errorCount++;
                System.err.println("错误：学生 " + student.getName() + " 的身份证号位数不正确: " + student.getIdCard());
            }

            // ... 其他清洗规则

            if (isCleaned) {
                studentRepository.save(student);
                cleanedCount++;
            }
        }
        result.put("total", students.size());
        result.put("cleaned", cleanedCount);
        result.put("error", errorCount);
        return result;
    }

    public Map<String, Object> cleanFamilyInfoData() {
        List<FamilyInfo> familyInfos = familyInfoRepository.findAll();
        int cleanedCount = 0;
        int errorCount = 0;
        Map<String, Object> result = new HashMap<>();

        for (FamilyInfo familyInfo : familyInfos) {
            boolean isCleaned = false;
            // 清洗家庭住址 (例如：去除首尾空格)
            if (StringUtils.isNotBlank(familyInfo.getAddress()) && !familyInfo.getAddress().equals(familyInfo.getAddress().trim())) {
                familyInfo.setAddress(familyInfo.getAddress().trim());
                isCleaned = true;
            }

            // 清洗家庭年总收入 (例如：处理非数字字符)
            if (familyInfo.getTotalIncome() != null && !NumberUtils.isCreatable(familyInfo.getTotalIncome().toString())) {
                // 尝试去除数字以外的字符
                String cleanedIncome = familyInfo.getTotalIncome().toString().replaceAll("[^\\d.]", "");
                if (NumberUtils.isCreatable(cleanedIncome)) {
                    familyInfo.setTotalIncome(Double.parseDouble(cleanedIncome));
                    isCleaned = true;
                } else {
                    errorCount++;
                    System.err.println("错误：家庭信息 " + familyInfo.getId() + " 的家庭年总收入无法转换为数字");
                }
            }

            // ... 其他清洗规则

            if (isCleaned) {
                familyInfoRepository.save(familyInfo);
                cleanedCount++;
            }
        }
        result.put("total", familyInfos.size());
        result.put("cleaned", cleanedCount);
        result.put("error", errorCount);
        return result;
    }

    public Map<String, Object> cleanSpecialGroupData() {
        List<SpecialGroup> specialGroups = specialGroupRepository.findAll();
        int cleanedCount = 0;
        int errorCount = 0;
        Map<String, Object> result = new HashMap<>();

        for (SpecialGroup specialGroup : specialGroups) {
            boolean isCleaned = false;
            // 清洗特殊群体类型 (例如：去除首尾空格, 并可以进行特定值的替换)
            if (StringUtils.isNotBlank(specialGroup.getGroupType()) && !specialGroup.getGroupType().equals(specialGroup.getGroupType().trim())) {
                specialGroup.setGroupType(specialGroup.getGroupType().trim());
                isCleaned = true;
            }

            // 其他清洗规则，例如标准化特殊群体类型名称
            String cleanedType = cleanSpecialGroupType(specialGroup.getGroupType());
            if (!specialGroup.getGroupType().equals(cleanedType)) {
                specialGroup.setGroupType(cleanedType);
                isCleaned = true;
            }

            if (isCleaned) {
                specialGroupRepository.save(specialGroup);
                cleanedCount++;
            }
        }

        result.put("total", specialGroups.size());
        result.put("cleaned", cleanedCount);
        result.put("error", errorCount);
        return result;
    }

    // ... 其他实体的数据清洗方法

    // 辅助方法：清洗性别
    private String cleanGender(String gender) {
        if (gender.contains("男")) {
            return "男";
        } else if (gender.contains("女")) {
            return "女";
        } else {
            return gender; // 无法识别的性别，保持原样或根据需求进行处理
        }
    }

    // 辅助方法：清洗特殊群体类型
    private String cleanSpecialGroupType(String type) {
        // 示例：将 "低保" 统一为 "低保户"
        if ("低保".equals(type)) {
            return "低保户";
        }
        // ... 其他类型的清洗规则
        return type;
    }

    // 其他辅助方法...
}