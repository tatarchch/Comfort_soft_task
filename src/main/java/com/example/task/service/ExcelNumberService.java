package com.example.task.service;

import com.example.task.util.FindUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class ExcelNumberService {

    public ResponseEntity<?> findMinNumber(String path, int n) {
        if (n <= 0) {
            String error = "N должно быть положительным";
            log.warn(error);
            return ResponseEntity.badRequest().body(error);
        }

        File file = new File(path);
        if (!file.exists() || !file.isFile()) {
            String error = "Файл не существует: " + path;
            log.warn(error);
            return ResponseEntity.badRequest().body(error);
        }

        try {
            List<Integer> numbers = this.readNumbersFromExcel(path, n);

            if (numbers.isEmpty()) {
                String error = "Файл не содержит чисел";
                log.warn(error);
                return ResponseEntity.badRequest().body(error);
            }

            int actualN = Math.min(n, numbers.size());
            int result = FindUtil.findMin(numbers, actualN);

            return ResponseEntity.ok(result);

        } catch (IOException e) {
            String error = "Ошибка чтения файла: ";
            log.warn("{}: {}", error, path);
            return ResponseEntity.badRequest().body(error);
        }
    }

    private List<Integer> readNumbersFromExcel(String path, int n) throws IOException {
        List<Integer> numbers = new ArrayList<>(n);

        try (FileInputStream file = new FileInputStream(path);
             Workbook workbook = new XSSFWorkbook(file)) {

            Sheet sheet = workbook.getSheetAt(0);
            DataFormatter dataFormatter = new DataFormatter();

            for (int rowIndex = 0; rowIndex < n && rowIndex <= sheet.getLastRowNum(); rowIndex++) {

                Row row = sheet.getRow(rowIndex);
                if (row == null) {
                    continue;
                }

                Cell cell = row.getCell(0);
                if (cell == null) {
                    continue;
                }

                try {
                    String cellValue = dataFormatter.formatCellValue(cell).trim();

                    if (cellValue.isEmpty()) {
                        continue;
                    }

                    int number = Integer.parseInt(cellValue);
                    numbers.add(number);

                } catch (NumberFormatException e) {
                    String error = "Ошибка чтения файла";
                    log.warn("{}: {}", error, e.getMessage());
                }
            }
        }
        return numbers;
    }

}
