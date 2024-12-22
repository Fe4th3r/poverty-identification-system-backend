package com.example.demo.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@ToString(exclude = {"student"})
public class WorkStudy {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "student_id")
    @JsonIgnore
    private Student student;

    private String position; // 岗位名称

    private LocalDate startDate; // 开始日期

    private LocalDate endDate; // 结束日期

    private String monthlySalary; // 月收入

    // ... 其他字段，例如：工作地点、工作内容描述等
}