package com.example.demo.controller;

import com.example.demo.service.PovertyIdentificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/poverty-identification")
public class PovertyIdentificationController {

    @Autowired
    private PovertyIdentificationService povertyIdentificationService;

    @PostMapping("/identify")
    public ResponseEntity<Map<String, String>> identifyPovertyLevels() {
        povertyIdentificationService.identifyPovertyLevels();
        Map<String, String> response = new HashMap<>();
        response.put("message", "贫困生甄别完成");
        return ResponseEntity.ok(response);
    }
}