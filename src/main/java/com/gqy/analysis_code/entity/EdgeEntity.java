package com.gqy.analysis_code.entity;

public class EdgeEntity {

    private int weight;
    private Classnode sourcenode;
    private Classnode targetnode;
    public int getWeight() {
        return weight;
    }
    public void setWeight(int weight) {
        this.weight = weight;
    }
    public Classnode getSourcenode() {
        return sourcenode;
    }
    public void setSourcenode(Classnode sourcenode) {
        this.sourcenode = sourcenode;
    }
    public Classnode getTargetnode() {
        return targetnode;
    }
    public void setTargetnode(Classnode targetnode) {
        this.targetnode = targetnode;
    }



}
