package com.edu.formatter;

import org.springframework.format.Formatter;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class StringToDateFormatter implements Formatter<Date> {
    public Date parse(String s, Locale locale) throws ParseException {
        DateFormat dft = new SimpleDateFormat("yyyy-MM-dd");
        System.out.println(s);
        return dft.parse(s);
    }

    public String print(Date date, Locale locale) {
        DateFormat dft = new SimpleDateFormat("yyyy-MM-dd");
        return dft.format(date);
    }
}
