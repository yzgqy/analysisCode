package com.gqy.analysis_code.louvain;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class WFile {
    public static void main(String[] args) throws IOException {
        List<String> namefile = readFile02("/Users/yaya/Desktop/nodeName.txt");
        HashMap<String,String> nodeNames = new HashMap<String,String>();
        for (String s1 :namefile){
            String[] ss = s1.split(" ");
            nodeNames.put(ss[0],ss[1]);
            System.out.println(ss[0]+" "+ss[1]);
        }

//        List<String> c = readFile02("/Users/yaya/Desktop/数据流/circle.txt");
        List<String> c = readFile02("/Users/yaya/Desktop/result.txt");
        List<String> writeLine = new ArrayList<String>();
        for (String s1 :c){
            StringBuffer stringBuffer = new StringBuffer();
            String[] ss = s1.split(" ");
            for (int i = 0; i<ss.length;i++){
                String idname = nodeNames.get(ss[i]);
                System.out.println(ss[i]+" "+idname);
                stringBuffer.append(idname);
                stringBuffer.append(" ");
            }
            writeLine.add(stringBuffer.toString());
        }
        writeFile02(writeLine,"/Users/yaya/Desktop/resultName.txt");
    }

    public static  void writeFile02(List<String> arrs, String path) throws IOException {

        // 写入中文字符时解决中文乱码问题
        FileOutputStream fos = new FileOutputStream(new File(path));
        OutputStreamWriter osw = new OutputStreamWriter(fos, "UTF-8");
        BufferedWriter bw = new BufferedWriter(osw);

        for (String arr : arrs) {
            bw.write(arr + "\n");
        }

        // 注意关闭的先后顺序，先打开的后关闭，后打开的先关闭
        bw.close();
        osw.close();
        fos.close();
    }

    public static List<String> readFile02(String path) throws IOException {
        // 使用一个字符串集合来存储文本中的路径 ，也可用String []数组
        List<String> list = new ArrayList<String>();
        FileInputStream fis = new FileInputStream(path);
        // 防止路径乱码 如果utf-8 乱码 改GBK eclipse里创建的txt 用UTF-8，在电脑上自己创建的txt 用GBK
        InputStreamReader isr = new InputStreamReader(fis, "UTF-8");
        BufferedReader br = new BufferedReader(isr);
        String line = "";
        while ((line = br.readLine()) != null) {
            // 如果 t x t文件里的路径 不包含---字符串 这里是对里面的内容进行一个筛选
            // if (line.lastIndexOf("---") < 0) {
            list.add(line);
            // }
        }
        br.close();
        isr.close();
        fis.close();
        return list;
    }
}
