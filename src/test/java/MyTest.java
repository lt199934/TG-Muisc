import java.io.File;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class MyTest {

    public static void main(String[] args) throws ParseException {
        DateFormat dft = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println("---------------");
        String s = "1992-5-3 12:13:24";
        System.out.println((dft.parse(s)));
        String os = System.getProperty("os.name");
        //Windows操作系统
        if (os != null && os.toLowerCase().startsWith("windows")) {
            System.out.println(String.format("当前系统版本是:%s", os));
        } else if (os != null && os.toLowerCase().startsWith("linux")) {//Linux操作系统
            System.out.println(String.format("当前系统版本是:%s", os));
        } else { //其它操作系统
            System.out.println(String.format("当前系统版本是:%s", os));
        }
        String filePath = System.getProperty("user.dir") + System.getProperty("file.separator") + "data" + System.getProperty("file.separator") + "music"+ System.getProperty("file.separator") + "headImg" ;
        System.out.println(filePath);
        File file = new File(filePath);
        if(!file.exists()){
            file.mkdirs();
        }
    }
}
