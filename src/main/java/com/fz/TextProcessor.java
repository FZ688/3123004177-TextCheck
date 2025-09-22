package com.fz;

import com.hankcs.hanlp.HanLP;
import com.hankcs.hanlp.seg.common.Term;

import java.util.*;
import java.util.regex.Pattern;


/**
 * @Author： fz
 * @Date： 2025/9/23 0:35
 * @Describe：
 */
public class TextProcessor {
    // 标点符号正则
    private static final Pattern PUNCTUATION_PATTERN = Pattern.compile("[\\p{P}\\s]+");
    // 停用词
    private static final String[] STOP_WORDS = {"的", "了", "是", "很", "我", "有", "和", "也", "吧", "啊", "你", "他", "她"};

    /**
     * 文本预处理
     * @param text 原始文本
     * @return 处理后的文本
     */
    public static String preprocessText(String text) {
        if (text == null || text.isEmpty()) {
            return "";
        }

        // 去除标点符号和空白符
        String processed = PUNCTUATION_PATTERN.matcher(text).replaceAll(" ").trim();

        // 长文本去除停用词
        if (processed.length() > 800) {
            for (String stopWord : STOP_WORDS) {
                processed = processed.replace(stopWord, "");
            }
        }

        return processed;
    }

    /**
     * 分词处理
     * @param text 处理后的文本
     * @return 分词结果列表
     */
    public static List<String> segmentText(String text) {
        if (text == null || text.isEmpty()) {
            return new ArrayList<>();
        }

        List<Term> terms = HanLP.segment(text);
        List<String> words = new ArrayList<>();

        for (Term term : terms) {
            String word = term.word.trim();
            if (!word.isEmpty()) {
                words.add(word);
            }
        }

        return words;
    }
}