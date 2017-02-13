package com.example.arpit.congressapplication;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Arpit on 23-11-2016.
 */

public class DateFormatter {
    final String OLD_FORMAT = "yyyy-MM-dd";
    final String NEW_FORMAT = "MMM dd,yyyy";
    String newDateString;

    public DateFormatter() {
    }
    // August 12, 2010

    public String dateFormat(String date) {
        SimpleDateFormat sdf = new SimpleDateFormat(OLD_FORMAT);
        Date d = null;
        try {
            d = sdf.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        sdf.applyPattern(NEW_FORMAT);
        newDateString = sdf.format(d);

        //System.out.println(newDateString);
        return newDateString;
    }
}
