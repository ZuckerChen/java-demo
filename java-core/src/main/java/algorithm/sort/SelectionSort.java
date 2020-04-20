package algorithm.sort;

/**
 * @author zucker
 * @description 选择排序
 * 初始状态：无序区为R[1..n]，有序区为空；
 * 第i趟排序(i=1,2,3…n-1)开始时，当前有序区和无序区分别为R[1..i-1]和R(i..n）。该趟排序从当前无序区中-选出关键字最小的记录 R[k]，将它与无序区的第1个记录R交换，使R[1..i]和R[i+1..n)分别变为记录个数增加1个的新有序区和记录个数减少1个的新无序区；
 * n-1趟结束，数组有序化了。
 * O(n²)
 * @date: 2020/4/3 4:17 PM
 */
public class SelectionSort implements SortStrategy {
    @Override
    public void sort(int[] arr) {
        int tmp=0, min_index=0;
        for (int i = 0; i < arr.length - 1; i++) {
            for (int j = min_index + 1; j < arr.length - 1; j++) {
                if(arr[i]>arr[j]){
                    tmp = arr[j];
                }
            }
            arr[min_index]=tmp;
        }
    }
}
