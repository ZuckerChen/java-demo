package algorithm.sort;

import com.sun.tools.javac.util.ArrayUtils;

import java.util.Collection;

/**
 * Description:插入排序
 *
 * 从第一个元素开始，该元素可以认为已经被排序
 * 取出下一个元素，在已经排序的元素序列中从后向前扫描
 * 如果该元素（已排序）大于新元素，将该元素移到下一位置
 * 重复步骤3，直到找到已排序的元素小于或者等于新元素的位置
 * 将新元素插入到该位置后
 * 重复步骤2~5
 *
 * tips：查找的代价如果较大，可改用二分查找。
 * @author zucker
 * @version 1.0 2018/6/12 上午11:23
 */
public class InsertionSort {
    public static void main(String[] args) {
        int[] a = new int[]{5,3,2,1,8,9,7};
        sort(a);
        for (int i = 0; i < a.length-1; i++) {
            System.out.println(a[i]);
        }

    }

    public static void sort(int[] arr){
        for (int i = 1; i < arr.length; i++) {
            int currNum = arr[i];
            int j = i-1;
            while (j>=0 && currNum<arr[j]){
                arr[j+1] = arr[j];
                j--;
            }
            arr[j+1]=currNum;
        }
    }
}
