package com.example.demo.repository;

import com.example.demo.entity.FamilyInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FamilyInfoRepository extends JpaRepository<FamilyInfo, Long> {
    FamilyInfo findByStudentId(Long studentId);
}