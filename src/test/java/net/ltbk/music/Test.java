package net.ltbk.music;

/**
 * @Program: music
 * @ClassName Test
 * @Author: liutao
 * @Description:
 * @Create: 2023-09-28 10:24
 * @Version 1.0
 **/


public class Test {
    public static void main(String[] args) throws InterruptedException {
        int count = 0;
        for (float y = 2.5f; y > -2.0f; y -= 0.12f) {
            for (float x = -2.3f; x < 2.3f; x += 0.041f) {
                float a = x * x + y * y - 4f;
                if ((a * a * a - x * x * y * y * y) < -0.0f) {
                    String str = "I lOVE YOU";
                    int num = count % str.length();
                    System.out.print(str.charAt(num));
                    count++;
                } else {
                    System.out.print(" ");
                }
            }
            System.err.println();
            Thread.sleep(100);
        }

    }
}
