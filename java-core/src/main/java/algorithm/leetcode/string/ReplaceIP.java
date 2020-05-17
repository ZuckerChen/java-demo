package algorithm.leetcode.string;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * @author zucker
 * @description
 * @date: 2020/5/11 11:35 PM
 */
public class ReplaceIP {
    public static void main(String[] args) {
        List<String> strings = restoreIpAddresses("0000");
        strings.forEach(s-> System.out.println(s));
    }


    public static List<String> restoreIpAddresses(String s) {
        List<String> segments = new LinkedList();
        List<String> ans = new ArrayList();

        back(s,0,ans,segments);
        return ans;
    }

    private static void back(String s ,int curPos,List<String> ans,List<String> segments){
        //符合要求条件 1.正好分为4段  2.s正好遍历到末尾
        if(segments.size() == 4){
            if(curPos == s.length()){
                ans.add(String.join(".",segments));
            }
            return ;
        }

        //循环移动curPos
        for(int i =1;i<=3;i++){
            //跳出循环条件
            if(s.length()<curPos+i){
                break;
            }

            //剪枝条件 1.segment<0 or segment>255  2.若segment.length()>1 && s.startWith("0")
            String segment = s.substring(curPos,curPos+i);
            if(Integer.parseInt(segment)>255 || (segment.length()>1&&segment.startsWith("0"))){
                return;
            }

            //添加段到segments
            segments.add(segment);
            //继续搜索下一个段
            back(s,curPos+i,ans,segments);
            //回溯到上一个段
            segments.remove(segments.size()-1);
        }

    }
}
