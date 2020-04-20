package algorithm.sort;

/**
 * @author zucker
 * @description
 * ——比较类：
 * ————交换排序：
 * 冒泡排序 new BubbleSort()
 * 快速排序
 *
 * ————插入排序：
 * 简单插入排序 new InsertionSort()
 * 希尔排序
 *
 * ————选择排序
 * 简单选择排序 new SelectionSort()
 * 堆排序
 *
 * ————归并排序
 * 二路归并排序 new MergerSort()
 * 多路归并排序
 *
 *
 * ——非比较类：
 * 计数排序
 * 桶排序
 * 基数排序
 *
 * @date: 2020/4/3 4:09 PM
 */
public class SortMain {
    public static void main(String[] args) {
        int[] arr = new int[]{5, 3, 2, 1, 8, 9, 7};
        SortContext insertSort = new SortContext(new SelectionSort());
        insertSort.sort(arr);
        printArr(arr);
    }

    private static void printArr(int[] arr) {
        System.out.print("排序后：");
        for (int i = 0; i < arr.length - 1; i++) {
            System.out.print(arr[i] + " ");
        }
    }

}
