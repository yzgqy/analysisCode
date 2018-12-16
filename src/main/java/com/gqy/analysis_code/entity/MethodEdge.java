package com.gqy.analysis_code.entity;

import java.io.Serializable;

public class MethodEdge implements Serializable {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column methodedge.id
     *
     * @mbggenerated
     */
    private Integer id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column methodedge.sourceid
     *
     * @mbggenerated
     */
    private Integer sourceid;
    private String source;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column methodedge.targetid
     *
     * @mbggenerated
     */
    private Integer targetid;
    private String target;

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public String getTarget() {
        return target;
    }

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column methodedge.weight
     *
     * @mbggenerated
     */
    private Integer weight;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table methodedge
     *
     * @mbggenerated
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column methodedge.id
     *
     * @return the value of methodedge.id
     *
     * @mbggenerated
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column methodedge.id
     *
     * @param id the value for methodedge.id
     *
     * @mbggenerated
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column methodedge.sourceid
     *
     * @return the value of methodedge.sourceid
     *
     * @mbggenerated
     */
    public Integer getSourceid() {
        return sourceid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column methodedge.sourceid
     *
     * @param sourceid the value for methodedge.sourceid
     *
     * @mbggenerated
     */
    public void setSourceid(Integer sourceid) {
        this.sourceid = sourceid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column methodedge.targetid
     *
     * @return the value of methodedge.targetid
     *
     * @mbggenerated
     */
    public Integer getTargetid() {
        return targetid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column methodedge.targetid
     *
     * @param targetid the value for methodedge.targetid
     *
     * @mbggenerated
     */
    public void setTargetid(Integer targetid) {
        this.targetid = targetid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column methodedge.weight
     *
     * @return the value of methodedge.weight
     *
     * @mbggenerated
     */
    public Integer getWeight() {
        return weight;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column methodedge.weight
     *
     * @param weight the value for methodedge.weight
     *
     * @mbggenerated
     */
    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table methodedge
     *
     * @mbggenerated
     */
    @Override
    public String toString() {
        return "MethodEdge{" +
                "id=" + id +
                ", sourceid=" + sourceid +
                ", source='" + source + '\'' +
                ", targetid=" + targetid +
                ", target='" + target + '\'' +
                ", weight=" + weight +
                '}';
    }
}