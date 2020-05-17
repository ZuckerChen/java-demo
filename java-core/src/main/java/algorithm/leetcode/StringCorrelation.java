package algorithm.leetcode;

import java.util.HashSet;
import java.util.Set;
import java.util.Stack;

/**
 * @author zucker
 * @description 字符串轮转
 * @date: 2020/4/7 4:23 PM
 */
public class StringCorrelation {
    static StringCorrelation stringCorrelation = new StringCorrelation();
    public static void main(String[] args) {
//        testIsRotate();
//        testReverseWords();
//        testlengthOfLongestSubstring();

        String s1 = "ab";
        String s2 = "eidbaooo";
        System.out.println(stringCorrelation.checkInclusion(s1,s2));
    }

    private static void testLengthOfLongestSubstring() {
        String s = "afaf12e23";
        int length = stringCorrelation.lengthOfLongestSubstring(s);
        System.out.println(length);
    }

    private static void testReverseWords() {
        String s = "the sky is blue";
        StringCorrelation stringCorrelation = new StringCorrelation();
        String res = stringCorrelation.reverseWords(s);
        System.out.println(res);
    }

    /**
     * 给定一个字符串，逐个翻转字符串中的每个单词
     * 1.无空格字符构成一个单词。
     * 2.输入字符串可以在前面或者后面包含多余的空格，但是反转后的字符不能包括。
     * 3.如果两个单词间有多余的空格，将反转后单词间的空格减少到只含一个。
     */
    String reverseWords(String s) {
        //解析单词存入栈
        Stack<String> stack = new Stack();
        //单词临时存储空间
        String tmpStr = "";
        //标识是否开始解析单词
        boolean flag = false;
        for (int i = 0; i < s.length(); i++) {
            if (flag) {
                char c = s.charAt(i);
                if (c != ' ') {
                    tmpStr += c;
                } else {
                    flag = false;
                    stack.push(tmpStr);
                    tmpStr = "";
                }
            } else {
                char c = s.charAt(i);
                if (c != ' ') {
                    flag = true;
                    tmpStr += c;
                }
            }
        }
        if (!tmpStr.isEmpty()) {
            stack.push(tmpStr);
        }

        String res = "";
        while (!stack.isEmpty()) {
            if (res.length() == 0) {
                res += stack.pop();
            } else {
                res += " ";
                res += stack.pop();
            }
        }

        return res;
    }

    private static void testIsRotate() {
        String s1 = "helloworld";
        String s2 = "lloworldhee";
        StringCorrelation stringCorrelation = new StringCorrelation();
        stringCorrelation.isRotate(s1, s2);
    }

    boolean isRotate(String s1, String s2) {
        if (s1.length() != s2.length()) {
            return false;
        }

        s1 += s1;

        if (s1.contains(s2)) {
            return true;
        }
        return false;
    }


    /**
     * 给定一个字符串，请你找出其中不含有重复字符的 最长子串 的长度。
     *
     * @param s
     * @return
     */
    public int lengthOfLongestSubstring(String s) {
        if(s==null){
            return 0;
        }
        if(s.length()==1){
            return 1;
        }

        int max_ans=0;
        Set set = new HashSet();
        for(int i = 0;i<s.length();i++){
            set.clear();
            set.add(s.charAt(i));
            for(int j = i+1;j<s.length();j++){
                if(!set.contains(s.charAt(j))){
                    set.add(s.charAt(j));
                }else{
                    break;
                }
            }
            max_ans = Math.max(max_ans,set.size());
        }
        return max_ans;
    }


    /**
     * 给定两个字符串 s1 和 s2，写一个函数来判断 s2 是否包含 s1 的排列。
     * 换句话说，第一个字符串的排列之一是第二个字符串的子串。
     */
    public boolean checkInclusion(String s1, String s2) {
        int[] a = new int[26];
        int[] b = new int[26];

        if(s1.length()>s2.length()){
            return false;
        }

        for(int i =0;i<s1.length();i++){
            a[s1.charAt(i)-'a'] ++;
        }

        for(int i= 0;i<s1.length();i++){
            b[s2.charAt(i)-'a'] ++ ;
        }

        for(int i = 0;i<s2.length()-s1.length();i++){
            if(match(a,b)){
                return true;
            }
            //滑动s2
            b[s2.charAt(i)-'a']--;
            b[s2.charAt(i+s1.length())-'a']++;
        }
        return match(a,b);
    }

    private boolean match(int[] a, int[] b){
        for(int i =0;i<a.length;i++){
            if(a[i] != b[i]){
                return false;
            }
        }
        return true;
    }
}
