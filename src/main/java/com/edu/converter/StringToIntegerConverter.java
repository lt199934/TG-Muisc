package com.edu.converter;


import org.springframework.core.convert.converter.Converter;



public class StringToIntegerConverter implements Converter<String,Integer> {
    public Integer convert(String s) {
        System.out.println("-------------------------");
        return Integer.parseInt(s);
    }
}
