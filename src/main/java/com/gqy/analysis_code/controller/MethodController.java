package com.gqy.analysis_code.controller;

import com.gqy.analysis_code.asm.ClassAdapter;
import com.gqy.analysis_code.asm.MethodAdapter;
import com.gqy.analysis_code.dao.MethodEdgeMapper;
import com.gqy.analysis_code.dao.MethodNodeMapper;
import com.gqy.analysis_code.entity.*;
import org.springframework.asm.ClassReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@EnableAutoConfiguration
public class MethodController {

    @Autowired
    private MethodEdgeMapper em;

    @Autowired
    private MethodNodeMapper nm;


    // 生产调用图并入库
    @ResponseBody
    @RequestMapping(value = "/method/graph", method = RequestMethod.GET)
    public HashMap<String, Object> addMethodGraph() throws IOException {
        HashMap<String, Object> result = new HashMap<String, Object>();
        String path = "/Users/yzgqy/Desktop/demo/Jpetstore/org/mybatis";
        ArrayList<String> myfiles = new ArrayList<String>();
        traverseFolder2(path, myfiles);
        System.out.println("class文件数：" + myfiles.size());
        for (String file : myfiles) {
            if (file.endsWith(".class")) {
                InputStream inputstream = new FileInputStream(new File(file));
                ClassReader cr = new ClassReader(inputstream);
                ClassAdapter ca = new ClassAdapter();
                cr.accept(ca, ClassReader.EXPAND_FRAMES);
            }
        }
        System.out.println("++++++++++++++");
        System.out.println(ClassAdapter.methodNodes.size());
        System.out.println(MethodAdapter.methodEdges.size());
        System.out.println("++++++++++++++");
        HashMap<String, MethodNode> methodnoedes = ClassAdapter.methodNodes;
        HashMap<String, MethodEdge> methodedges = MethodAdapter.methodEdges;
        for (Map.Entry<String, MethodNode> entry : methodnoedes.entrySet()) {
            List<MethodNode> mnodes = nm.selectByfullname(entry.getValue().getFullname());
            if (mnodes.size() == 0 || mnodes == null) {
                nm.insert(entry.getValue());
            }
        }

        for (Map.Entry<String, MethodEdge> entry : methodedges.entrySet()) {
            int sourceid = 0;
            int targetid = 0;
            System.out.println("getSource   :    "+entry.getValue().getSource());
            List<MethodNode> sourceNodes = nm.selectByfullname(entry.getValue().getSource());
            if (!sourceNodes.isEmpty()) {
                MethodNode mynode = sourceNodes.get(0);
                sourceid = mynode.getId();

            }
            System.out.println("getTarget   :   "+entry.getValue().getTarget());
            List<MethodNode> targetNodes = nm.selectByfullname(entry.getValue().getTarget());
//            System.out.println(targetNodes);
            if (!targetNodes.isEmpty()) {
                MethodNode mynode = targetNodes.get(0);
                targetid = mynode.getId();
            }

            if (sourceid != 0 && targetid != 0) {
                List<MethodEdge> myedge = em.selectBynode(targetid, sourceid);
                if (myedge.size() == 0 || myedge == null) {
                    MethodEdge record = new MethodEdge();
                    record.setSourceid(sourceid);
                    record.setTargetid(targetid);
                    record.setWeight(entry.getValue().getWeight());

                    em.insert(record);
                }
            }

        }

        result.put("nodes", methodnoedes);
        result.put("edges", methodedges);
        return result;
    }

//    // 查看文件的个数，点和边的个数
//    @ResponseBody
//    @RequestMapping(value = "/find", method = RequestMethod.GET)
//    public HashMap<String, Object> find() throws IOException {
//        HashMap<String, Object> result = new HashMap<String, Object>();
//        String path = "/Users/yzgqy/Desktop/demo/Jpetstore/org/mybatis";
//        ArrayList<String> myfiles = new ArrayList<String>();
//        traverseFolder2(path, myfiles);
//        System.out.println("class文件数：" + myfiles.size());
//        for (String file : myfiles) {
//            if (file.endsWith(".class")) {
//                InputStream inputstream = new FileInputStream(new File(file));
//                ClassReader cr = new ClassReader(inputstream);
//                ClassAdapter ca = new ClassAdapter();
//                cr.accept(ca, ClassReader.EXPAND_FRAMES);
//            }
//        }
//        System.out.println(ClassAdapter.classnoedes.size());
//        System.out.println(MethodAdapter.edges.size());
//        result.put("nodes", ClassAdapter.classnoedes.size());
//        result.put("edges", MethodAdapter.edges.size());
//        return result;
//    }

    // 从库中读到边文件
    @ResponseBody
    @RequestMapping(value = "/method/edges", method = RequestMethod.GET)
    public HashMap<String, Object> readEdges() throws IOException {
        HashMap<String, Object> result = new HashMap<String, Object>();
        List<MethodNode> nodes = nm.selectAll();
        List<MethodEdge> edges = em.selectAll();
        List<String> lines = new ArrayList<String>();
        String count = (nodes.size()+1)+" "+edges.size();
        lines.add(count);
        for (MethodEdge edge : edges) {
            String line = edge.getSourceid() + " " + edge.getTargetid() + " " + edge.getWeight();
            lines.add(line);
        }
        writeFile02(lines, "/Users/yzgqy/Desktop/methodEdges.txt");


        return result;
    }

    // 算法后的结果变为name
    @ResponseBody
    @RequestMapping(value = "/method/name", method = RequestMethod.GET)
    public HashMap<String, Object> toname() throws IOException {
        HashMap<String, Object> result = new HashMap<String, Object>();

        String idPath = "/Users/yzgqy/Desktop/circle.txt";
        String namePath =  "/Users/yzgqy/Desktop/circleName.txt";

        List<String> idList = readFile02(idPath);
        List<String> nameList = new ArrayList<>();
        for (String ids:idList) {
            String[] idsLine = ids.split(" ");
            StringBuilder nameLine = new StringBuilder();
            for (String id :idsLine) {
                MethodNode methodNode = nm.selectByPrimaryKey(Integer.valueOf(id));
                if(methodNode!=null) {
//                    nameLine.append(" ");
//                    nameLine.append(methodNode.getFullname());
                    nameList.add(methodNode.getFullname());
                }
            }
//            System.out.println(nameLine.toString());
//            nameList.add(nameLine.toString());
            nameList.add("");
        }
        writeFile02(nameList,namePath);

        return result;
    }

    // 动态结果入库
    @ResponseBody
    @RequestMapping(value = "/method/dynamic", method = RequestMethod.GET)
    public HashMap<String, Object> addDynamic() throws IOException {
        HashMap<String, Object> result = new HashMap<String, Object>();
        List<String> lines = readFile02("/Users/yzgqy/Desktop/statics-method-org.mybatis.jpetstore.txt");
        for (String line : lines) {
            String[] edges = line.split(" ");
            String sourceName = edges[0];
            String targetName = edges[1];
            int weight = Integer.valueOf(edges[2]);
            int sourceid = 0;
            int targetid = 0;

            List<MethodNode> sourceNodes = nm.selectByfullname(sourceName);

            List<MethodNode> targetNodes = nm.selectByfullname(targetName);

            if (!sourceNodes.isEmpty() && !targetNodes.isEmpty()) {
                MethodNode mynode1 = sourceNodes.get(0);
                sourceid = mynode1.getId();
                MethodNode mynode2 = targetNodes.get(0);
                targetid = mynode2.getId();

                List<MethodEdge> myedge = em.selectBynode(targetid, sourceid);
                if (myedge.isEmpty()) {
                    MethodEdge record = new MethodEdge();
                    record.setSourceid(sourceid);
                    record.setTargetid(targetid);
                    record.setWeight(weight);
                    em.insert(record);
                    result.put(String.valueOf(record.getId()), record);
                } else {
                    MethodEdge edge = myedge.get(0);
                    int egdeweigth = edge.getWeight() + weight;
                    edge.setWeight(egdeweigth);
                    em.updateByPrimaryKey(edge);
                    result.put(String.valueOf(edge.getId()), edge);
                }
            }
        }
        return result;
    }

    // 有效方法
    @ResponseBody
    @RequestMapping(value = "/method/dynamicMethod", method = RequestMethod.GET)
    public HashMap<String, Object> dynamicClass() throws IOException {
        HashMap<String, Object> result = new HashMap<String, Object>();
        List<String> arrs = new ArrayList<>();
        List<String> lines = readFile02("/Users/yzgqy/Desktop/statics-method-org.mybatis.jpetstore.txt");
        for (String line : lines) {
            String[] edges = line.split(" ");
            String sourceName = edges[0];
            String targetName = edges[1];
            int weight = Integer.valueOf(edges[2]);
            int sourceid = 0;
            int targetid = 0;

            List<MethodNode> sourceNodes = nm.selectByfullname(sourceName);

            List<MethodNode> targetNodes = nm.selectByfullname(targetName);


            if (!sourceNodes.isEmpty() && !targetNodes.isEmpty()) {
                MethodNode mynode1 = sourceNodes.get(0);
                sourceid = mynode1.getId();
                MethodNode mynode2 = targetNodes.get(0);
                targetid = mynode2.getId();
                result.put(String.valueOf(sourceid), mynode1.getFullname());
                result.put(String.valueOf(targetid), mynode2.getFullname());
                arrs.add(mynode1.getFullname()+" "+mynode2.getFullname()+" "+weight);
                arrs.add(sourceid+" "+targetid+" "+weight);
            }else{
                System.out.println(line);
            }
        }
        writeFile02(arrs,"/Users/yzgqy/Desktop/number.txt");
        return result;
    }

    public void writeFile02(List<String> arrs, String path) throws IOException {

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

    public static void traverseFolder2(String path, ArrayList<String> myfiles) {

        File file = new File(path);
        if (file.exists()) {
            File[] files = file.listFiles();
            if (null == files || files.length == 0) {
                // System.out.println("文件夹是空的!");
                return;
            } else {
                for (File file2 : files) {
                    if (file2.isDirectory()) {
                        traverseFolder2(file2.getAbsolutePath(), myfiles);
                    } else {

                        if (file2.getName().endsWith(".class")) {
                            myfiles.add(file2.getAbsolutePath());
                            System.out.println("文件:" + file2.getAbsolutePath());
                        }

                    }
                }
            }
        } else {
            System.out.println("文件不存在!");
        }
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
//
//    public void idToName(String idPath,String namePath)throws IOException {
//        List<String> idList = readFile02(idPath);
//        List<String> nameList = new ArrayList<>();
//        for (String ids:idList) {
//            String[] idsLine = ids.split(" ");
//            StringBuilder nameLine = new StringBuilder();
//            for (String id :idsLine) {
//                MethodNode methodNode = nm.selectByPrimaryKey(Integer.valueOf(id));
//                nameLine.append(" ");
//                nameLine.append(methodNode.getFullname());
//            }
//            nameList.add(nameLine.toString());
//        }
//        writeFile02(nameList,namePath);
//
//    }
}
