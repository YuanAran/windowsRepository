
package org.example;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Queue;

public class Main {
    private static final int MAX_WEIGHT = 110;
    private static int maxValue = 0; // 记录最大价值
    private static ArrayList<Article> bestSolution = new ArrayList<>(); // 记录最优解

    public static int select(ArrayList<Article> list, int maxWeight) {
        backtrack(list, 0, 0, maxWeight, 0, new ArrayList<>()); // 开始回溯
        return maxValue;
    }

    private static void backtrack(ArrayList<Article> list, int currentIndex, int currentWeight, int remainingWeight, int currentValue, ArrayList<Article> currentSolution) {
        if (currentIndex == list.size()) {
            // 如果当前路径的价值大于已知的最大价值，则更新最大价值和最优解
            if (currentValue > maxValue) {
                maxValue = currentValue;
                bestSolution = new ArrayList<>(currentSolution);
            }
            return;
        }
//软件2202 0304220201 陈炬鹏
        Article currentArticle = list.get(currentIndex);

        // 计算上界（UB）
        double upperBound = currentValue + calculateUpperBound(list, currentIndex, remainingWeight);
        System.out.println("ub:" + upperBound); // 输出上界值

        // 如果上界小于或等于已知的最大价值，则剪枝
        if (upperBound <= maxValue) {
            return;
        }

        // Case 1: 不选择当前物品
        backtrack(list, currentIndex + 1, currentWeight, remainingWeight, currentValue, currentSolution);

        // Case 2: 选择当前物品（如果它能放入剩余容量）
        if (currentArticle.weight <= remainingWeight) {
            currentArticle.isSelected = true;
            currentSolution.add(currentArticle); // 将当前物品加入当前解
            int newWeight = currentWeight + currentArticle.weight;
            int newValue = currentValue + currentArticle.value;

            backtrack(list, currentIndex + 1, newWeight, remainingWeight - currentArticle.weight, newValue, currentSolution);

            // 回溯：取消选择当前物品
            currentArticle.isSelected = false;
            currentSolution.remove(currentSolution.size() - 1); // 从当前解中移除当前物品
        }
    }

    private static double calculateUpperBound(ArrayList<Article> list, int currentIndex, int remainingWeight) {
        double upperBound = 0;
        int totalWeight = 0;

        for (int i = currentIndex; i < list.size(); i++) {
            Article article = list.get(i);
            if (totalWeight + article.weight <= remainingWeight) {
                upperBound += article.value;
                totalWeight += article.weight;
            } else {
                upperBound += article.value * ((double) (remainingWeight - totalWeight) / article.weight);
                break;
            }
        }

        return upperBound;
    }

    public static void main(String[] args) {
        ArrayList<Article> articles = new ArrayList<>();
        articles.add(new Article(11, 21));
        articles.add(new Article(1, 11));
        articles.add(new Article(21, 31));
        articles.add(new Article(23, 33));
        articles.add(new Article(43, 53));
        articles.add(new Article(33, 43));
        articles.add(new Article(55, 65));
        articles.add(new Article(45, 55));

        int result = select(articles, MAX_WEIGHT);

        // 将最优解放入队列中
        Queue<Article> queue = new ArrayDeque<>(bestSolution);

        // 输出最优解和最优值
        System.out.println("最优解: " + queue);
        System.out.println("最大价值: " + result);
    }

    static class Article {
        int weight;
        int value;
        boolean isSelected;

        public Article(int weight, int value) {
            this.weight = weight;
            this.value = value;
            this.isSelected = false;
        }

        @Override
        public String toString() {
            return "Article{" +
                    "weight=" + weight +
                    ", value=" + value +
                    '}';
        }
    }
}