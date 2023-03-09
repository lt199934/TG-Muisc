package net.ltbk.music;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.ResourceUtils;

import java.io.*;
import java.net.URL;

@SpringBootTest
class MusicApplicationTests {

    @Test
    void contextLoads(){

    }

    public static void main(String[] args) throws FileNotFoundException {
        File save = new File("src/main/resources/static/upload/songListImg");
        if (!save.exists()) {
            save.mkdir();
        }
        String path = save.getPath();
        System.out.println(path);
    }

}
