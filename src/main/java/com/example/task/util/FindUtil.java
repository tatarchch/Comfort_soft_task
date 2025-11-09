package com.example.task.util;

import lombok.experimental.UtilityClass;

import java.util.List;

@UtilityClass
public class FindUtil {

    public int findMin(List<Integer> numbers, int n) {
        if (numbers == null || numbers.isEmpty()) {
            throw new IllegalArgumentException("Список чисел не может быть пустым");
        }
        if (n <= 0 || n > numbers.size()) {
            throw new IllegalArgumentException("N должно быть в диапазоне от 1 до " + numbers.size());
        }

        if (n == 1) {
            return numbers.get(0);
        }

        int[] arr = numbers.stream().limit(n).mapToInt(Integer::intValue).toArray();
        int k = n - 1;

        int min = arr[0];
        for (int i = 1; i < arr.length; i++) {
            if (arr[i] < min) {
                min = arr[i];
            }
        }

        return min;
    }

}
