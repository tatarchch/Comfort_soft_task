package com.example.task.controller;

import com.example.task.service.ExcelNumberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/excel")
@RequiredArgsConstructor
@Slf4j
public class ExcelNumberController {

    private final ExcelNumberService service;

    @PostMapping("/findMin")
    public ResponseEntity<?> findMinNumber(@RequestParam String path,
                                                 @RequestParam int n) {

        return service.findMinNumber(path, n);
    }

}
