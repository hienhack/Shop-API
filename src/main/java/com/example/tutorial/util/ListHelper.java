package com.example.tutorial.util;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ListHelper {
    /**
     * Check if a list has duplicated values. The method uses equal method for checking, make sure item's type
     * has implemented equals method.
     *
     * @param items a List of items
     * @return true if the list has duplicated values, otherwise returns false
     */
    public static boolean hasDuplicatedValues(List<? extends Object> items) {
        if (items.size() < 1)
            return false;

        Set<Object> existedItems = new HashSet<>();
        for (Object item : items) {
            if (existedItems.contains(item)) {
                return true;
            }
            existedItems.add(item);
        }
        return false;
    }
}
