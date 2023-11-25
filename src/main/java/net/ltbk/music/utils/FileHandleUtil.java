package net.ltbk.music.utils;


import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

/**
 * @Program: music
 * @ClassName File
 * @Author: liutao
 * @Description: 传文件工具类
 * @Create: 2023-03-10 02:01
 * @Version 1.0
 **/
@Slf4j
@Component
public class FileHandleUtil {

    /**
     * 静态目录
     **/
    private static final String STATIC_DIR = "static";
    /**
     * 文件存放的目录
     **/
    private static final String FILE_DIR = "/upload/";


    private static String windowsPath;
    private static String linuxPath;
    /**
     * 绝对路径
     **/
    private static String absolutePath;

    @Value("${file.windows.uploadPath}")
    public void setWindowsPath(String windowsPath) {
        FileHandleUtil.windowsPath = windowsPath;
    }

    @Value("${file.linux.uploadPath}")
    public void setLinuxPath(String linuxPath) {
        FileHandleUtil.linuxPath = linuxPath;
    }

    /**
     * 上传单个文件
     * 最后文件存放路径为：static/upload/image/test.jpg
     * 文件访问路径为：http://127.0.0.1:8080/upload/image/test.jpg
     * 该方法返回值为：/upload/image/test.jpg
     *
     * @param inputStream 文件流
     * @param path        文件路径，如：image/
     * @param filename    文件名，如：test.jpg
     * @return 成功：上传后的文件访问路径，失败返回：null
     */
    public static String upload(InputStream inputStream, String path, String filename) {
        //第一次会创建文件夹
        createDirIfNotExists();
        String filePath = FILE_DIR + path + filename;
        String resultPath = '/' + path + filename;
        //存文件
        File uploadFile = new File(absolutePath, STATIC_DIR + filePath);
        log.info("{}", uploadFile.exists());
        if (uploadFile.exists()) {
            return resultPath;
        }
        try {
            FileUtils.copyInputStreamToFile(inputStream, uploadFile);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        return resultPath;
    }

    /**
     * 创建文件夹路径
     */
    private static void createDirIfNotExists() {
        init();
        if (!absolutePath.isEmpty()) {
            return;
        }

        //获取跟目录
        File file = null;
        try {
            file = new File(ResourceUtils.getURL("classpath:").getPath());
        } catch (FileNotFoundException e) {
            throw new RuntimeException("获取根目录失败，无法创建上传目录！");
        }
        if (!file.exists()) {
            file = new File("");
        }

        absolutePath = file.getAbsolutePath();

        File upload = new File(absolutePath, STATIC_DIR + FILE_DIR);
        if (!upload.exists()) {
            upload.mkdirs();
        }
    }

    /**
     * 删除文件
     *
     * @param path 文件访问的路径upload开始 如： /image/test.jpg
     * @return true 删除成功； false 删除失败
     */
    public static boolean delete(String path) {
        File file = new File(absolutePath, STATIC_DIR + path);
        if (file.exists()) {
            return file.delete();
        }
        return false;
    }

    public static void init() {
        String os = System.getProperty("os.name");
        //Windows操作系统
        if (os != null && os.toLowerCase().startsWith("windows")) {
            absolutePath = windowsPath;
        } else if (os != null && os.toLowerCase().startsWith("linux")) {
            absolutePath = linuxPath;
        }
        log.info("绝对路径：{}", absolutePath);
    }
}
