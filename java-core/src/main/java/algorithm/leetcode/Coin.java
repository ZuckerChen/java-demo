package algorithm.leetcode;

import java.util.HashSet;
import java.util.Set;

/**
 * @author zucker
 * @description
 * @date: 2020/4/23 3:38 PM
 */
public class Coin {

    public boolean isHappy(int n) {

        Set set = new HashSet();

        boolean flag = false;

        int sum =0;
        while(n != 0){
            int i = n%10;
            n = n/10;

            sum += i*i;


            if(n==0){
                if(set.contains(sum)){
                    break;
                }
                if(sum==1){
                    flag = true;
                    break;
                }
                set.add(sum);
                n = sum;
                sum = 0;
            }
        }

        return flag;
    }

    public static void main(String[] args) {
        Coin coin = new Coin();
        System.out.println(coin.isHappy(7));
    }
    /**
     * 给定数量不限的硬币，币值为25分、10分、5分和1分，编写代码计算n分有几种表示法。
     */
    public int waysToChange(int n) {
     return 0;
    }
}
