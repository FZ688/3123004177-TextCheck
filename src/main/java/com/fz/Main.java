package com.fz;

import java.io.IOException;



/**
 * @Author： fz
 * @Date： 2025/9/23 0:34
 * @Describe：
 */
public class Main {
    public static void main(String[] args) {
        // 检查命令行参数
        if (args.length != 3) {
            System.err.println("输入有误！用法: java -jar main.jar <original_file> <plagiarized_file> <output_file>");
            System.exit(1);
        }

        String originalFilePath = args[0];
        String plagiarizedFilePath = args[1];
        String outputFilePath = args[2];

        try {
            // 读取文件内容
            String originalText = FileHandler.readFile(originalFilePath);
            String plagiarizedText = FileHandler.readFile(plagiarizedFilePath);

            // 计算相似度
            SimilarityCalculator calculator = new SimilarityCalculator();
            double similarity = calculator.calculateSimilarity(originalText, plagiarizedText, 0.7, 0.3);

            // 输出结果
            String result = String.format("文本查重：%.2f", similarity);
            FileHandler.writeFile(outputFilePath, result);
            System.out.println(result);

        } catch (IOException e) {
            System.err.println("文件操作错误: " + e.getMessage());
            System.exit(1);
        } catch (IllegalArgumentException e) {
            System.err.println("输入错误: " + e.getMessage());
            System.exit(1);
        }
    }
}