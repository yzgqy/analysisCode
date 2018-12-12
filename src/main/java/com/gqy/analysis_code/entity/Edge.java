package com.gqy.analysis_code.entity;

import java.io.Serializable;

public class Edge implements Serializable {
    private  String source;
    private String target;
    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }


    private Integer id;


    private Integer sourceid;


    private Integer targetid;


    private Integer weight;


    private static final long serialVersionUID = 1L;


    public Integer getId() {
        return id;
    }


    public void setId(Integer id) {
        this.id = id;
    }


    public Integer getSourceid() {
        return sourceid;
    }


    public void setSourceid(Integer sourceid) {
        this.sourceid = sourceid;
    }


    public Integer getTargetid() {
        return targetid;
    }


    public void setTargetid(Integer targetid) {
        this.targetid = targetid;
    }


    public Integer getWeight() {
        return weight;
    }


    public void setWeight(Integer weight) {
        this.weight = weight;
    }


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", sourceid=").append(sourceid);
        sb.append(", targetid=").append(targetid);
        sb.append(", weight=").append(weight);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}