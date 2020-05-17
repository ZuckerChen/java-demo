package algorithm.leetcode;

/**
 * @author zucker
 * @description
 * @date: 2020/4/9 5:52 PM
 */
public class TrapRain {
    public static void main(String[] args) {
    }

    /**
     * 给定 n 个非负整数表示每个宽度为 1 的柱子的高度图，计算按此排列的柱子，下雨之后能接多少雨水。
     */
    public int trap(int[] height) {
        int res = 0;
        int left_index = 0;
        int right_index = 0;

        while (right_index == height.length - 1) {
            if (height[left_index] < 0) {
                left_index++;
            } else {
                boolean canTrap = false;
                right_index = left_index + 1;
                //找沟壑
                while (right_index == height.length - 1) {
                    int deduction = height[left_index] - height[right_index];
                    if (deduction > 0) {
                        right_index = left_index + 1;
                    }else{
                        break;
                    }
                }
                //计算沟壑储水量
            }
        }

        return res;
    }
}
