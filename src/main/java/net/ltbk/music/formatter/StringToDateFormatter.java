package net.ltbk.music.formatter;

import org.springframework.format.Formatter;
import org.springframework.stereotype.Component;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
@Component
public class StringToDateFormatter implements Formatter<Date> {
    @Override
    public Date parse(String s, Locale locale) throws ParseException {
        DateFormat dft = new SimpleDateFormat("yyyy-MM-dd");
        System.out.println(s);
        return dft.parse(s);
    }

    @Override
    public String print(Date date, Locale locale) {
        DateFormat dft = new SimpleDateFormat("yyyy-MM-dd");
        return dft.format(date);
    }
}
