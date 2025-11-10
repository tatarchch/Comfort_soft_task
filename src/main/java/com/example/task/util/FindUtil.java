package com.example.task.util;

import lombok.experimental.UtilityClass;

import java.util.List;

@UtilityClass
public class FindUtil {

    public int findMin(List<Integer> numbers, int n) {

        if (numbers == null || numbers.isEmpty()) {
            throw new IllegalArgumentException("Список чисел не может быть пустым");
        }


        int min = numbers.get(0);
        for (int i = 1; i < n; i++) {
            if (numbers.get(i) < min) {
                min = numbers.get(i);
            }
        }
        return min;
    }

}
