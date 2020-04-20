package algorithm.sort;

/**
 * @author zucker
 * @description
 * @date: 2020/4/3 4:04 PM
 */
public class SortContext {
    private SortStrategy strategy;

    public SortContext(SortStrategy strategy) {
        this.strategy = strategy;
    }

    public void sort(int[] arr) {
        strategy.sort(arr);
    }
}
