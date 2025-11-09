package com.example.task.service;

import com.example.task.util.FindUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class ExcelNumberService {

    public ResponseEntity<?> findMinNumber(String path, int n) {
        try {
            List<Integer> numbers = this.readNumbersFromExcel(path);

            if (numbers.isEmpty()) {
                String error = "Файл не содержит чисел";
                log.warn(error);
                return ResponseEntity.badRequest().body(error);
            }

            if (n <= 0 || n > numbers.size()) {
                String error = "N должно быть в диапазоне от 1 до " + numbers.size();
                log.warn(error);
                return ResponseEntity.badRequest().body(error);
            }

            return ResponseEntity.ok(FindUtil.findMin(numbers, n));

        } catch (IOException e) {
            String error = "Файл по такому пути не найден";
            log.warn(error);
            return ResponseEntity.badRequest().body(error);
        }

    }

    private List<Integer> readNumbersFromExcel(String path) throws IOException {
        List<Integer> numbers = new ArrayList<>();

        try (FileInputStream file = new FileInputStream(path);
             Workbook workbook = new XSSFWorkbook(file)) {

            Sheet sheet = workbook.getSheetAt(0);
            DataFormatter dataFormatter = new DataFormatter();

            for (Row row : sheet) {
                Cell cell = row.getCell(0);
                if (cell != null) {
                    try {
                        String cellValue = dataFormatter.formatCellValue(cell).trim();

                        if (cellValue.isEmpty()) {
                            continue;
                        }

                        int number = Integer.parseInt(cellValue);
                        numbers.add(number);

                    } catch (NumberFormatException e) {
                        log.warn("Невозможно преобразовать значение '{}' в целое число в строке {}",
                                dataFormatter.formatCellValue(cell), row.getRowNum() + 1);
                    }
                }
            }
        }
        return numbers;
    }

}
