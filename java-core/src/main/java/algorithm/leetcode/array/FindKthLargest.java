package algorithm.leetcode.array;

import java.util.Random;

/**
 * @author zucker
 * @description
 * @date: 2020/5/13 9:10 PM
 */
public class FindKthLargest {
    public int findKthLargest(int[] nums, int k) {
        //快速排序：找第K大的数 相当于 找第N-K小的数
        return quickSelect(nums,0,nums.length-1,nums.length-k);
    }

    private int quickSelect(int[] nums,int left,int right,int k){
        if(left == right){
            return nums[left];
        }

        Random random_num = new Random();
        int pivot_index = left + random_num.nextInt(right - left);

        pivot_index = partition(nums,left,right,pivot_index);
        if(pivot_index==k){
            return nums[pivot_index];
        }else if(pivot_index<k){
            //k在右边
            return quickSelect(nums,pivot_index+1,right, k);
        }else{
            //k在左边
            return quickSelect(nums,left,pivot_index-1, k);
        }
    }

    private int partition(int[] nums,int left,int right,int pivot_index){
        int pivot = nums[pivot_index];
        //move pivot to end
        swap(pivot_index,right,nums);

        int tmp_left = left;
        //移动所有小于pivot的元素到左边

        for(int i = left;i<=right;i++){
            if(nums[i]<pivot){
                swap(i,tmp_left,nums);
                tmp_left++;
            }
        }

        //move pivot to its final postion
        swap(right,tmp_left,nums);

        return tmp_left;
    }

    private void swap (int a,int b,int[] nums){
        int tmp = nums[a];
        nums[a] = nums[b];
        nums[b] = tmp;
    }

    public static void main(String[] args) {
        FindKthLargest findKthLargest = new FindKthLargest();
        System.out.println(findKthLargest.findKthLargest(new int[]{3,2,1,5,6,4},2));
    }
}
