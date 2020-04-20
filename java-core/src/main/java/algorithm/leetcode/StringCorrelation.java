package algorithm.leetcode;

import java.util.Stack;

/**
 * @author zucker
 * @description 字符串轮转
 * @date: 2020/4/7 4:23 PM
 */
public class StringCorrelation {
    public static void main(String[] args) {
//        testIsRotate();
        testreverseWords();
    }

    private static void testreverseWords() {
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
        if(!tmpStr.isEmpty()){
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
}
