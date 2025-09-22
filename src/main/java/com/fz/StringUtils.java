package com.fz;

/**
 * @Author： fz
 * @Date： 2025/9/23 0:44
 * @Describe：
 */
public class StringUtils {
    /**
     * 计算两个字符串的Levenshtein距离
     * @param s1 字符串1
     * @param s2 字符串2
     * @return 编辑距离
     */
    public static int levenshteinDistance(String s1, String s2) {
        if (s1 == null || s2 == null) {
            throw new IllegalArgumentException("字符串不能为null");
        }

        int len1 = s1.length();
        int len2 = s2.length();

        // 创建距离矩阵
        int[][] dp = new int[len1 + 1][len2 + 1];

        // 初始化边界
        for (int i = 0; i <= len1; i++) {
            dp[i][0] = i;
        }
        for (int j = 0; j <= len2; j++) {
            dp[0][j] = j;
        }

        // 填充矩阵
        for (int i = 1; i <= len1; i++) {
            char c1 = s1.charAt(i - 1);
            for (int j = 1; j <= len2; j++) {
                char c2 = s2.charAt(j - 1);

                // 字符相同则代价为0，否则为1
                int cost = (c1 == c2) ? 0 : 1;

                // 计算插入、删除、替换的代价
                dp[i][j] = Math.min(
                        Math.min(dp[i - 1][j] + 1,      // 删除
                                dp[i][j - 1] + 1),     // 插入
                        dp[i - 1][j - 1] + cost        // 替换
                );
            }
        }

        return dp[len1][len2];
    }
}
