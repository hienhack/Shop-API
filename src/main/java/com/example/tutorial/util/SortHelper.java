package com.example.tutorial.util;

import org.springframework.data.domain.Sort;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class SortHelper {
    public static Sort getFinalSort(Class<? extends Enum<?>> enumClass, String sortQuery) {
        Set<String> acceptedValues = Stream.of(enumClass.getEnumConstants())
                .map(value -> value.name().toUpperCase()).collect(Collectors.toSet());

        List<Sort> sorts = Arrays.stream(sortQuery.split(",")).map(sortMode -> {
                String field = sortMode.replace("-", "");
                if (!acceptedValues.contains(field)) {
                    int index = sortMode.indexOf('-');
                    Sort.Direction direction = index == 0 ? Sort.Direction.DESC : Sort.Direction.ASC;
                    return Sort.by(direction, field);
                } else {
                    return Sort.unsorted();
                }

        }).toList();

        return sorts.stream().reduce(Sort.unsorted(), Sort::and);
    }
}
