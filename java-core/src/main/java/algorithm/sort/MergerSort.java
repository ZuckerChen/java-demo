package algorithm.sort;

/**
 * Description: 归并排序
 * 分治法 用递归的方式拆分成两两比较，再合并有序数组
 * 复杂度：O(nlogn)
 * 稳定
 *
 * @author zucker
 * @version 1.0 2018/6/14 下午4:41
 */
public class MergerSort implements SortStrategy{
    @Override
    public void sort(int[] a) {
        int left_index = 0;
        int right_index = a.length-1;

        int[] temp_arr = new int[a.length];

        sort(a, left_index, right_index, temp_arr);

    }

    private void sort(int[] a, int left_index, int right_index, int[] temp_arr) {
        int mid_index = (left_index + right_index) / 2;

        if (left_index < right_index) {
            //左边归并排序
            sort(a, left_index, mid_index, temp_arr);
            //右边归并排序
            sort(a, mid_index + 1, right_index, temp_arr);
            //合并两个有序集合
            merge(a, left_index, mid_index, right_index, temp_arr);
        }

    }

    private void merge(int[] a, int left_index, int mid_index, int right_index, int[] temp_arr) {
        int i = left_index;
        int j = mid_index + 1;
        int n = 0;

        while (i <= mid_index && j <= right_index) {
            if (a[i] <= a[j]) {
                temp_arr[n++] = a[i++];
            } else {
                temp_arr[n++] = a[j++];
            }
        }

        //如果剩下左边的全部放到临时数组中
        while (i <= mid_index) {
            temp_arr[n++] = a[i++];
        }

        while (j <= right_index) {
            temp_arr[n++] = a[j++];
        }

        n = 0;
        while (left_index <= right_index) {
            a[left_index++] = temp_arr[n++];
        }

    }
}
