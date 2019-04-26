package com.gqy.analysis_code.controller;

import com.gqy.analysis_code.asm.ClassAdapter;
import com.gqy.analysis_code.asm.MethodAdapter;
import com.gqy.analysis_code.dao.ClassnodeMapper;
import com.gqy.analysis_code.dao.EdgeMapper;
import com.gqy.analysis_code.entity.Classnode;
import com.gqy.analysis_code.entity.Edge;
import com.gqy.analysis_code.entity.User;
import org.springframework.asm.ClassReader;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@RestController
@EnableAutoConfiguration

public class TestBootController {
    @RequestMapping("getuser")
    public User getUser() {
        User user = new User();
        user.setName("test");
        return user;
    }

    @Autowired
    private ClassnodeMapper cm;

    @Autowired
    private EdgeMapper em;

    // 生产调用图并入库
    @ResponseBody
    @RequestMapping(value = "/graph", method = RequestMethod.GET)
    public HashMap<String, Object> addgraph() throws IOException {
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
        System.out.println(ClassAdapter.classnoedes.size());
        System.out.println(MethodAdapter.edges.size());
        HashMap<String, Classnode> classnoedes = ClassAdapter.classnoedes;
        HashMap<String, Edge> edges = MethodAdapter.edges;
        for (Map.Entry<String, Classnode> entry : classnoedes.entrySet()) {
            System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
            List<Classnode> cnodes = cm.selectByfullname(entry.getValue().getFullname());
            if (cnodes.size() == 0 || cnodes == null) {
                cm.insert(entry.getValue());
            }
        }

        for (Map.Entry<String, Edge> entry : edges.entrySet()) {
            System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
            int sourceid = 0;
            int targetid = 0;
            List<Classnode> sourceNodes = cm.selectByfullname(entry.getValue().getSource());
            System.out.println(sourceNodes);
            if (!sourceNodes.isEmpty()) {
                Classnode mynode = sourceNodes.get(0);
                sourceid = mynode.getId();

            }
            List<Classnode> targetNodes = cm.selectByfullname(entry.getValue().getTarget());
            System.out.println(targetNodes);
            if (!targetNodes.isEmpty()) {
                Classnode mynode = targetNodes.get(0);
                targetid = mynode.getId();
            }

            if (sourceid != 0 && targetid != 0) {
                List<Edge> myedge = em.selectBynode(targetid, sourceid);
                if (myedge.size() == 0 || myedge == null) {
                    Edge record = new Edge();
                    record.setSourceid(sourceid);
                    record.setTargetid(targetid);
                    record.setWeight(entry.getValue().getWeight());

                    em.insert(record);
                }
            }

        }

        result.put("nodes", classnoedes);
        result.put("edges", edges);
        return result;
    }

    // 查看文件的个数，点和边的个数
    @ResponseBody
    @RequestMapping(value = "/find", method = RequestMethod.GET)
    public HashMap<String, Object> find() throws IOException {
        HashMap<String, Object> result = new HashMap<String, Object>();
        String path = "/Users/yzgqy/Desktop/demomd/Jpetstore/org/mybatis";
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
        System.out.println(ClassAdapter.classnoedes.size());
        System.out.println(MethodAdapter.edges.size());
        result.put("nodes", ClassAdapter.classnoedes.size());
        result.put("edges", MethodAdapter.edges.size());
        return result;
    }

    // 从库中读到边文件
    @ResponseBody
    @RequestMapping(value = "/edges", method = RequestMethod.GET)
    public HashMap<String, Object> readEdges() throws IOException {
        HashMap<String, Object> result = new HashMap<String, Object>();
        List<Classnode> nodes = cm.selectAll();
        List<Edge> edges = em.selectall();
        List<String> lines = new ArrayList<String>();
        String count = (nodes.size()+1)+" "+edges.size();
        lines.add(count);
        for (Edge edge : edges) {
            String line = edge.getSourceid() + " " + edge.getTargetid() + " " + edge.getWeight();
            lines.add(line);
        }
        writeFile02(lines, "/Users/yaya/Desktop/classEdges.txt");
        return result;
    }

    // 动态结果入库
    @ResponseBody
    @RequestMapping(value = "/dynamic", method = RequestMethod.GET)
    public HashMap<String, Object> addDynamic() throws IOException {
        HashMap<String, Object> result = new HashMap<String, Object>();
        List<String> lines = readFile02("/Users/yzgqy/Desktop/statics.txt");
        for (String line : lines) {
            String[] edges = line.split(",");
            String sourceName = edges[0].replace(".", "/");
            String targetName = edges[1].replace(".", "/");
            int weight = Integer.valueOf(edges[2]);
            int sourceid = 0;
            int targetid = 0;
            System.out.println(line);
            System.out.println(sourceName);
            System.out.println(targetName);
            System.out.println(weight);
            System.out.println("++++++++++++++");
            List<Classnode> sourceNodes = cm.selectByfullname(sourceName);

            List<Classnode> targetNodes = cm.selectByfullname(targetName);

            if (!sourceNodes.isEmpty() && !targetNodes.isEmpty()) {
                Classnode mynode1 = sourceNodes.get(0);
                sourceid = mynode1.getId();
                Classnode mynode2 = targetNodes.get(0);
                targetid = mynode2.getId();

                List<Edge> myedge = em.selectBynode(targetid, sourceid);
                if (myedge.isEmpty()) {
                    Edge record = new Edge();
                    record.setSourceid(sourceid);
                    record.setTargetid(targetid);
                    record.setWeight(weight);
                    em.insert(record);
                    result.put(String.valueOf(record.getId()), record);
                } else {
                    Edge edge = myedge.get(0);
                    int egdeweigth = edge.getWeight() + weight;
                    edge.setWeight(egdeweigth);
                    em.updateByPrimaryKey(edge);
                    result.put(String.valueOf(edge.getId()), edge);
                }
            }
        }
        return result;
    }

    // 有效类
    @ResponseBody
    @RequestMapping(value = "/dynamicClass", method = RequestMethod.GET)
    public HashMap<String, Object> dynamicClass() throws IOException {
        HashMap<String, Object> result = new HashMap<String, Object>();
        List<String> lines = readFile02("/Users/yzgqy/Desktop/statics.txt");
        for (String line : lines) {
            String[] edges = line.split(",");
            String sourceName = edges[0].replace(".", "/");
            String targetName = edges[1].replace(".", "/");
            int weight = Integer.valueOf(edges[2]);
            int sourceid = 0;
            int targetid = 0;
            System.out.println(line);
            System.out.println(sourceName);
            System.out.println(targetName);
            System.out.println(weight);
            System.out.println("++++++++++++++");
            List<Classnode> sourceNodes = cm.selectByfullname(sourceName);

            List<Classnode> targetNodes = cm.selectByfullname(targetName);

            if (!sourceNodes.isEmpty() && !targetNodes.isEmpty()) {
                Classnode mynode1 = sourceNodes.get(0);
                sourceid = mynode1.getId();
                Classnode mynode2 = targetNodes.get(0);
                targetid = mynode2.getId();
                result.put(String.valueOf(sourceid), sourceid);
                result.put(String.valueOf(targetid), targetid);
            }
        }
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


}