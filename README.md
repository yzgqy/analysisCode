**静态分析步骤：**

1、asm分析出调用关系图入库

`http://localhost:8080/graph`

**加入动态分析**

1、动态结果加入

`http://localhost:8080/dynamic`

**算法分析**


2、得到边文件，（用于算法的输入文件）

`http://localhost:8080/edges`

3、com.gqy.analysis_code.louvain.Main,算法

**注：**

1、算法要求id从1开始

`truncate table classnode; ` 将classnode清空执行，然后自增长id初始化为1开始


**问题**

1、动态方法需要用" "分割，不能用"，"