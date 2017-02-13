package com.example.arpit.congressapplication;

import java.util.Comparator;
import java.util.Map;

/**
 * Created by Arpit on 27-11-2016.
 */
public class CompareArrayLegis implements Comparator<Map<String, String>>
{
    private final String key;
    private final String key1;

    public CompareArrayLegis(String key,String key1){
        this.key = key;
        this.key1=key1;
    }
    public int compare(Map<String, String> first,
                       Map<String, String> second)
    {
        // Assume no nulls, and simple ordinal comparisons

        // First by campus - stop if this gives a result.
        int campusResult = first.get(key).compareTo(second.get(key));
        if (campusResult != 0)
        {
            return campusResult;
        }

        // Next by faculty
           int facultyResult = first.get(key1).compareTo(second.get(key1));
            return facultyResult;
    }
}
