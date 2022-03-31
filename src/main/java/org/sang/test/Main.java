package org.sang.test;

import java.io.IOException;

public class Main {


    public static void main(String[] args) throws IOException {
//        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
//        System.out.println("请输入一行字符串:\n");
//        String strline = in.readLine();
//        if(strline==null || strline.length()==0){
//            System.err.println("输入的字符串不能为空");
//            return;
//        }
//        if(strline.length()>5000){
//            System.err.println("输入的字符串长度不能大于5000");
//            return;
//        }
//        String[] words = strline.split(" ");
//        System.out.println("最后一个单词的长度为: "+ words[words.length-1].length());
        String str = "nhrwlbcc8m7c5hih9mhalw98k0322wf2jjm47kk3ntm9snfrflzzundn7d608usy049asxalzjk7izj6amcqhr8uubc04g52mcjboj2fmge2l6iarizfu4yve5o4i3srf5zgqbg82ckcotdeqp760mc9gzei5dzk5gj9x9yj05o3hle0ii64krkkp5i7blh7nbu3gu5vgi2scyn4yqx3z4vcjbyzhnqkh887izotjkg2l0mit0k14vyn39";
        char ca = 't';
        String upperStr = str.toUpperCase();
        int len = upperStr.length();
        int count = 0;
        char[] str1 = new char[]{ca};
        String upperCa = new String(str1).toUpperCase();
        for (int i = 0; i < len - 1; i++) {
            if (upperStr.charAt(i) == upperCa.charAt(0)) {
                count++;
            }
        }
        System.out.println(count);
    }
}
