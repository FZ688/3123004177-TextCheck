package com.fz;


import java.util.*;


/**
 * @Author： fz
 * @Date： 2025/9/23 0:35
 * @Describe：
 */
public class SimilarityCalculator {

    /**
     * 计算两篇文本的相似度
     * @param text1 原文
     * @param text2 抄袭版文本
     * @param cosineWeight 余弦相似度权重
     * @param editDistanceWeight 编辑距离相似度权重
     * @return 最终相似度
     */
    public double calculateSimilarity(String text1, String text2, double cosineWeight, double editDistanceWeight) {
        // 验证输入
        if (text1 == null || text2 == null) {
            throw new IllegalArgumentException("文本不能为null");
        }

        // 预处理
        String processedText1 = TextProcessor.preprocessText(text1);
        String processedText2 = TextProcessor.preprocessText(text2);

        // 检查空文本
        if (processedText1.isEmpty() || processedText2.isEmpty()) {
            throw new IllegalArgumentException("空文件无法查重！请重新输入！");
        }

        // 分词
        List<String> words1 = TextProcessor.segmentText(processedText1);
        List<String> words2 = TextProcessor.segmentText(processedText2);

        // 计算余弦相似度
        double cosineSimilarity = calculateCosineSimilarity(words1, words2);

        // 计算编辑距离相似度
        double editDistanceSimilarity = calculateEditDistanceSimilarity(processedText1, processedText2);

        // 加权计算最终相似度
        return cosineWeight * cosineSimilarity + editDistanceWeight * editDistanceSimilarity;
    }

    /**
     * 计算余弦相似度
     * @param words1 文本1的分词结果
     * @param words2 文本2的分词结果
     * @return 余弦相似度
     */
    public double calculateCosineSimilarity(List<String> words1, List<String> words2) {
        // 创建词汇表
        Set<String> vocabulary = new HashSet<>(words1);
        vocabulary.addAll(words2);

        // 生成词频向量
        Map<String, Integer> freqMap1 = getFrequencyMap(words1);
        Map<String, Integer> freqMap2 = getFrequencyMap(words2);

        // 计算向量
        List<Integer> vector1 = new ArrayList<>();
        List<Integer> vector2 = new ArrayList<>();

        for (String word : vocabulary) {
            vector1.add(freqMap1.getOrDefault(word, 0));
            vector2.add(freqMap2.getOrDefault(word, 0));
        }

        // 计算点积
        double dotProduct = 0.0;
        double norm1 = 0.0;
        double norm2 = 0.0;

        for (int i = 0; i < vector1.size(); i++) {
            dotProduct += vector1.get(i) * vector2.get(i);
            norm1 += Math.pow(vector1.get(i), 2);
            norm2 += Math.pow(vector2.get(i), 2);
        }

        // 防止除零
        if (norm1 == 0 || norm2 == 0) {
            return 0.0;
        }

        return dotProduct / (Math.sqrt(norm1) * Math.sqrt(norm2));
    }

    /**
     * 计算编辑距离相似度
     * @param text1 文本1
     * @param text2 文本2
     * @return 编辑距离相似度
     */
    public double calculateEditDistanceSimilarity(String text1, String text2) {
        int editDistance = StringUtils.levenshteinDistance(text1, text2);
        int maxLength = Math.max(text1.length(), text2.length());

        if (maxLength == 0) {
            return 1.0;
        }

        return 1.0 - (double) editDistance / maxLength;
    }

    /**
     * 获取词频映射
     * @param words 分词列表
     * @return 词频映射
     */
    private Map<String, Integer> getFrequencyMap(List<String> words) {
        Map<String, Integer> freqMap = new HashMap<>();
        for (String word : words) {
            freqMap.put(word, freqMap.getOrDefault(word, 0) + 1);
        }
        return freqMap;
    }
}
