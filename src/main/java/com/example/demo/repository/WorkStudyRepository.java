package com.example.demo.repository;

import com.example.demo.entity.WorkStudy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WorkStudyRepository extends JpaRepository<WorkStudy, Long> {
    List<WorkStudy> findByStudentId(Long studentId);
}