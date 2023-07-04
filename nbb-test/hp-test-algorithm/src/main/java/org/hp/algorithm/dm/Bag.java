package org.hp.algorithm.dm;

public class Bag {

    /**
     * 背包问题，从m个物品放入选取物品放入可承受重量为n的背包里面，求背包可携带最多价值，每个物品和对应重量和价值
     * @param args
     */
    public static void main(String[] args) {

        int objectCount = 4; //物品数量
        int bagSize = 5; // 背包空间
        int[] value = {0, 4, 2, 5, 6}; // 每个物品所对应的价值，0表示0号物品的价值为0，也是价值的边界
        int[] space = {0, 2, 1, 3, 4}; // 每个物品所占用的空间，0表示0号物品所占空间为0，也是空间的边界

        int maxValue = getMaxValue(value, space, bagSize);
        System.out.println("最大价值为： " + maxValue);
    }


    /**
     *
     * @param valueArr 物品价值数组
     * @param spaceArr 物品空间数组
     * @param bagSize 背包空间大小
     * @return
     */
    public static int getMaxValue(int[] valueArr, int[] spaceArr, int bagSize) {
        int objectCount = valueArr.length - 1;

        int[][] maxValueArr = new int[valueArr.length + 1][bagSize + 1]; // 存储m个物品翻入n个空间背包的最大值

        // 最终子问题，1个物品放入空间为1的背包里面
        for (int i = 1; i <= objectCount; i++) {
            for (int j = 1; j <= bagSize; j++) {
                int unselectValue = maxValueArr[i - 1][j]; // 当前物品不选择的总价值 = i-1个物品放入空间为j的最大价值

                // 判断当前物品是否可以放进背包
                if (j >= spaceArr[i]) {
                    // 当前物品选择的价值 = 当前物品价值 加 i-1个物品放入空间为j -space[i]的最大价值
                    int selectedValue = valueArr[i] + maxValueArr[i - 1][j -spaceArr[i]];
                    maxValueArr[i][j] = max(unselectValue, selectedValue);
                } else {
                    maxValueArr[i][j] = unselectValue;
                }
            }
        }

        for(int i=0; i<=objectCount; i++) {
            for(int j=0; j<=bagSize; j++) {
                System.out.print(maxValueArr[i][j] + " ");
            }
            System.out.println();
        }
        return maxValueArr[objectCount][bagSize];
    }

    public static int max(int x, int y) {
        return x > y ? x: y;
    }

}
