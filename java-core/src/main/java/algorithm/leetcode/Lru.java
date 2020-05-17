package algorithm.leetcode;

import java.util.HashMap;
import java.util.Map;

/**
 * @author zucker
 * @description 淘汰算法：最近最少使用
 * @date: 2020/4/20 5:05 PM
 */
public class Lru<K, V> {
    class Node{
        Lru.Node pre;
        Lru.Node next;
        Integer key;
        Integer value;
        Node(Integer key,Integer value){
            this.key = key;
            this.value = value;
        }
    }

    private Lru.Node head = null;
    private Lru.Node tail = null;
    private Map<Integer, Lru.Node> map = null;
    private int capacity;

    public Lru(int capacity) {
        this.capacity = capacity;
        map = new HashMap();

        //初始化辅助节点
        head = new Lru.Node(-1,-1);
        tail = new Lru.Node(-1,-1);
        head.next = tail;
        tail.pre = head;
    }

    public int get(int key) {
        if(map.containsKey(key)){
            Lru.Node node = map.get(key);
            moveToHead(node);
            return node.value;
        }else{
            return -1;
        }
    }

    public void put(int key, int value) {
        Lru.Node node = map.get(key);
        if(null == node){
            if(capacity <= map.size()){
                map.remove(removeTail().key);
            }
            node = new Lru.Node(key,value);
            addToHead(node);
            map.put(key,node);
        }else{
            node.value = value;
            moveToHead(node);
        }
    }

    private void moveToHead(Lru.Node node){
        //前驱和后驱接上
        node.pre.next = node.next;
        node.next.pre = node.pre;

        addToHead(node);
    }

    private void addToHead(Lru.Node node){
        Lru.Node oldHead = head.next;

        oldHead.pre = node;
        node.next = oldHead;
        head.next = node;
        node.pre = head;

    }

    private Lru.Node removeTail(){
        Lru.Node oldTail = tail.pre;
        Lru.Node newTail = oldTail.pre;

        newTail.next = tail;
        tail.pre = newTail;

        oldTail.pre = null;
        oldTail.next = null;
        return oldTail;
    }

    private void print() {
        Lru.Node tmp = head;
        while (tmp != null) {
            System.out.println(tmp.key + ":" + tmp.value + "----" + tmp + "----" + tmp.pre + "----" + tmp.next);
            if (tmp == tail) {
                System.out.println();
                break;
            }
            tmp = tmp.next;
        }
        System.out.println("----------");
    }


    public static void main(String[] args) {

//        //LinkedHashMap实现
//        int cacheSize = 5;
//        LinkedHashMap lru = new LinkedHashMap(cacheSize,0.75f,true){
//            @Override
//            protected boolean removeEldestEntry(Map.Entry eldest) {
//                return this.size()>=cacheSize;
//            }
//        };

        Lru lru = new Lru(2);
        lru.put(1, 1);
        lru.print();
        lru.put(2, 2);
        lru.print();
        lru.get(1);
        lru.print();
        lru.put(3,3);
        lru.print();
        lru.get(2);
        lru.print();
        lru.put(4,4);
        lru.print();
        lru.get(1);
        lru.print();
        lru.get(3);
        lru.print();
        lru.get(4);
        lru.print();
    }

}
