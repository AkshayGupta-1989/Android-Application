package com.example.arpit.congressapplication;

import java.util.Comparator;
import java.util.Map;

public class CompareArrayBill implements Comparator<Map<String, String>> {
    private final String key;

    public CompareArrayBill(String key){
        this.key = key;
    }

    public int compare(Map<String, String> first,
                       Map<String, String> second){
        // TODO: Null checking, both for maps and values
        String firstValue = first.get(key);
        String secondValue = second.get(key);
        return secondValue.compareTo(firstValue);
    }



}
