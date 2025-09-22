package com.fz;


import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;


/**
 * @Author： fz
 * @Date： 2025/9/23 0:40
 * @Describe：
 */
public class FileHandler {
    /**
     * 读取文件内容
     * @param filePath 文件路径
     * @return 文件内容字符串
     * @throws IOException 当文件不存在或读取失败时抛出
     */
    public static String readFile(String filePath) throws IOException {
        if (!Files.exists(Paths.get(filePath))) {
            throw new FileNotFoundException("文件不存在: " + filePath);
        }

        byte[] bytes = Files.readAllBytes(Paths.get(filePath));
        return new String(bytes, StandardCharsets.UTF_8);
    }

    /**
     * 写入内容到文件
     * @param filePath 文件路径
     * @param content 要写入的内容
     * @throws IOException 当写入失败时抛出
     */
    public static void writeFile(String filePath, String content) throws IOException {
        Files.write(Paths.get(filePath), content.getBytes(StandardCharsets.UTF_8));
    }
}
