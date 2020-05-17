package algorithm.leetcode;

/**
 * @author zucker
 * @description
 * @date: 2020/4/23 10:44 AM
 */
public class ArraysCorrelation {

    /**
     * 给你一个整数数组 nums 和一个整数 k。
     * 如果某个 连续 子数组中恰好有 k 个奇数数字，我们就认为这个子数组是「优美子数组」。
     * 请返回这个数组中「优美子数组」的数目。
     * 解法：滑动窗口+当前窗口的优美子数组
     */
    public int numberOfSubarrays(int[] nums, int k) {
        int left = 0, right = 0, count = 0, res = 0;

        //右指针先走，每遇到奇数count++
        while (right < nums.length) {
            if (nums[right++] % 2 == 1) {
                count++;
            }

            if (count == k) {
                int tmp = right;
                //查第一个奇数左边有多少个偶数，则有n+1种组合
                while (right < nums.length && nums[right] % 2 == 0) {
                    right++;
                }
                int rightCount = right - tmp;


                //查最后一个奇数右边有多少个偶数，则有m+1种组合
                int leftCount = 0;
                while (nums[left] % 2 == 0) {
                    leftCount++;
                    left++;
                }

                //总共有(n+1)x(m+1)种组合
                res += (rightCount + 1) * (leftCount + 1);

                left++;
                count--;

            }
        }
        return res;
    }

    public static void main(String[] args) {
        ArraysCorrelation arraysCorrelation = new ArraysCorrelation();
        int[] nums = new int[]{1, 1, 2, 1, 1};
        int k = 3;

        int i = arraysCorrelation.numberOfSubarrays(nums, k);
        System.out.println(i);
    }
}
