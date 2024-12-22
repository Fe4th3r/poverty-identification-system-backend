package com.example.demo.service;

import com.example.demo.entity.Student;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

@Service
public class ExcelExportService {

    public byte[] exportStudentsToExcel(List<Student> students) throws IOException {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Students");

        // 创建表头
        Row headerRow = sheet.createRow(0);
        headerRow.createCell(0).setCellValue("ID");
        headerRow.createCell(1).setCellValue("学号");
        headerRow.createCell(2).setCellValue("姓名");
        headerRow.createCell(3).setCellValue("性别");
        headerRow.createCell(4).setCellValue("身份证号");
        headerRow.createCell(5).setCellValue("专业");
        headerRow.createCell(6).setCellValue("贫困等级");
        // ... 其他表头

        // 填充数据
        int rowNum = 1;
        for (Student student : students) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(student.getId());
            row.createCell(1).setCellValue(student.getStuId());
            row.createCell(2).setCellValue(student.getName());
            row.createCell(3).setCellValue(student.getGender());
            row.createCell(4).setCellValue(student.getIdCard());
            row.createCell(5).setCellValue(student.getMajor());
            row.createCell(6).setCellValue(student.getPovertyLevel() != null ? student.getPovertyLevel().toString() : "");
            // ... 其他字段
        }

        // 将 Excel 文件写入 ByteArrayOutputStream
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        workbook.write(outputStream);
        workbook.close();

        return outputStream.toByteArray();
    }
}