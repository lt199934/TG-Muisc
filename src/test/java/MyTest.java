import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class MyTest {

    public static void main(String[] args) throws ParseException {
        DateFormat dft=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println("---------------");
        String s= "1992-5-3 12:13:24";
        System.out.println((dft.parse(s)));
    }
}
