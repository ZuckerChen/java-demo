package algorithm.leetcode.string;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zucker
 * @description
 * @date: 2020/5/11 5:04 PM
 */
public class SimplifyPath {
    public static void main(String[] args) {
        System.out.println(simplifyPath("/a/./b/../../c/"));
    }


    public static String simplifyPath(String path) {

        String[] paths = path.split("/");

        if(paths.length==0){
            return "/";
        }

        List<String> list = new ArrayList<>();

        int jump=0;
        for(int i = paths.length-1;i>=0;i--){
            if(paths[i].equals(".")|| paths[i].equals("")){
                continue;
            }

            if(paths[i].equals("..")){
                jump ++;
                continue;
            }

            if(jump>0){
                jump--;
                continue;
            }
            list.add(0, paths[i]);
        }

        String ans = "";
        for(String s : list){
            ans = ans+"/"+s;
        }
        if(ans.equals("")){
            ans="/";
        }

        return ans;
    }
}
