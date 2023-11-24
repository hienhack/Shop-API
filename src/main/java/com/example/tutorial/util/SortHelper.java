package com.example.tutorial.util;

import com.example.tutorial.controller.ProductController;
import com.example.tutorial.enumeration.ProductSortMode;
import org.springframework.data.domain.Sort;

import java.util.List;

public class SortHelper {
    public static Sort getFinalSort(List<ProductSortMode> sortModes) {
        List<Sort> sorts = sortModes.stream().map(sortMode -> {
            String sortInfo = sortMode.name();
            int index = sortInfo.indexOf('_');

            return Sort.by(Sort.Direction.valueOf(sortInfo.substring(0, index)),
                    sortInfo.substring(index + 1).toLowerCase());
        }).toList();

        return sorts.stream().reduce(Sort.unsorted(), Sort::and);
    }
}
