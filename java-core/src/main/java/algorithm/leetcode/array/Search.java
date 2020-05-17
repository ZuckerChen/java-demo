package algorithm.leetcode.array;

/**
 * @author zucker
 * @description
 * @date: 2020/5/13 11:58 PM
 */
public class Search {
    public int search(int[] nums, int target) {
        int left = 0;
        int right = nums.length - 1;


        while(left<=right){
            int mid = left + ((right-left)>>2);

            if(nums[mid] == target){
                return mid;
            }

            if(nums[0]<=nums[mid]){
                //左边有序
                if(target>=nums[0] && target<nums[mid]){
                    //tareget在左边
                    right = mid -1;
                }else{
                    left = mid +1;
                }
            }else{
                //右边有序
                if(target>nums[mid] && target<=nums[right]){
                    //target在右边
                    left = mid +1;
                }else{
                    right = mid -1;
                }

            }
        }
        return -1;
    }

    public static void main(String[] args) {
        Search search = new Search();
        System.out.println(search.search(new int[]{3,1},1));
    }
}
