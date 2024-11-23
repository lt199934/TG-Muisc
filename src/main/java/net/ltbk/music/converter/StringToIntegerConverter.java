package net.ltbk.music.converter;


import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class StringToIntegerConverter implements Converter<String, Integer> {
    @Override
    public Integer convert(String s) {
        System.out.println("-------------------------");
        return Integer.parseInt(s);
    }
}
