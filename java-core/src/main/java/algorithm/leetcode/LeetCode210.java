package algorithm.leetcode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

/**
 * @author zucker
 * @description
 * 现在你总共有 n 门课需要选，记为 0 到 n-1。
 * 在选修某些课程之前需要一些先修课程。 例如，想要学习课程 0 ，你需要先完成课程 1 ，我们用一个匹配来表示他们: [0,1]
 * 给定课程总量以及它们的先决条件，返回你为了学完所有课程所安排的学习顺序。
 * 可能会有多个正确的顺序，你只要返回一种就可以了。如果不可能完成所有课程，返回一个空数组。
 *
 * 示例 1:
 * 输入: 2, [[1,0]]
 * 输出: [0,1]
 * 解释: 总共有 2 门课程。要学习课程 1，你需要先完成课程 0。因此，正确的课程顺序为 [0,1] 。
 *
 * 示例 2:
 * 输入: 4, [[1,0],[2,0],[3,1],[3,2]]
 * 输出: [0,1,2,3] or [0,2,1,3]
 * 解释: 总共有 4 门课程。要学习课程 3，你应该先完成课程 1 和课程 2。并且课程 1 和课程 2 都应该排在课程 0 之后。
 *      因此，一个正确的课程顺序是 [0,1,2,3] 。另一个正确的排序是 [0,2,1,3] 。
 */
public class LeetCode210 {
    public static int[] findOrder(int numCourses, int[][] prerequisites) {
        // array存放 课程的初始入度
        // hash表存放 k课程 v依赖他的后续课程

        int array[] = new int[numCourses];
        Map<Integer, List<Integer>> map = new HashMap<>();

        for(int i = 0; i<prerequisites.length; i++){
            int course = prerequisites[i][1];
            List<Integer> tmpList = map.containsKey(course)?map.get(course):new ArrayList<>();
            tmpList.add(prerequisites[i][0]);
            map.put(course,tmpList);

            array[prerequisites[i][0]] = array[prerequisites[i][0]]+1;
        }

        int ans[] = new int[numCourses];
        //把入度为0的全部放到queue中
        Queue<Integer> queue = new LinkedList<>();
        for(int i = 0; i<array.length; i++){
            if(array[i]==0){
                queue.offer(i);
            }
        }

        //取出度为0的按顺序放入结果集
        //受影响的课程的度-1，如果-1后为0，则放入队列中。
        int a = 0;
        while(!queue.isEmpty()){
            int course = queue.poll();
                ans[a] = course;
            a++;
            List<Integer> list = map.get(course);
            if(list == null ){
                continue;
            }
            for(Integer nextCourse : list){
                if(--array[nextCourse]==0){
                    queue.offer(nextCourse);
                }
            }
        }
        return a==numCourses ? ans : new int[]{};
    }

    public static void main(String[] args) {
        findOrder(2,new int[][]{{0,1},{1,0}});
    }
}
