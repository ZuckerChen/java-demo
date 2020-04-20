package algorithm.leetcode;

class MyCircularQueue {
    int[] fixed_arr;
    int head_index = 0;
    int tail_index = 0;
    int size = 0;


    /**
     * Initialize your data structure here. Set the size of the queue to be k.
     */
    public MyCircularQueue(int k) {
        fixed_arr = new int[k];
    }

    /**
     * Insert an element into the circular queue. Return true if the operation is successful.
     */
    public boolean enQueue(int value) {
        if (isFull()) {
            return false;
        }

        if (tail_index == fixed_arr.length - 1) {
            tail_index = 0;
        } else {
            if (!isEmpty()) {
                tail_index++;
            }
        }

        fixed_arr[tail_index] = value;
        size++;
        return true;
    }

    /**
     * Delete an element from the circular queue. Return true if the operation is successful.
     */
    public boolean deQueue() {
        if (isEmpty()) {
            return false;
        }

        fixed_arr[head_index] = 0;
        size--;
        if(!isEmpty()){
            if (head_index == fixed_arr.length - 1) {
                head_index = 0;
            } else {
                head_index++;
            }
        }
        return true;
    }

    /**
     * Get the front item from the queue.
     */
    public int Front() {
        if (isEmpty()) {
            return -1;
        }

        return fixed_arr[head_index];
    }

    /**
     * Get the last item from the queue.
     */
    public int Rear() {
        if (isEmpty()) {
            return -1;
        }

        return fixed_arr[tail_index];
    }

    /**
     * Checks whether the circular queue is empty or not.
     */
    public boolean isEmpty() {
        if (size == 0) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Checks whether the circular queue is full or not.
     */
    public boolean isFull() {
        if (size == fixed_arr.length) {
            return true;
        } else {
            return false;
        }

    }

    public static void main(String[] args) {

        MyCircularQueue obj = new MyCircularQueue(3);
        System.out.println(obj.enQueue(4));
        System.out.println(obj.deQueue());
        System.out.println(obj.enQueue(9));
        System.out.println(obj.enQueue(12));
        System.out.println(obj.deQueue());
        System.out.println(obj.Front());

        System.out.println(obj.enQueue(3));
        System.out.println(obj.enQueue(3));
        System.out.println(obj.Rear());
        System.out.println(obj.isFull());

        System.out.println(obj.enQueue(4));
        System.out.println(obj.Rear());


    }
}