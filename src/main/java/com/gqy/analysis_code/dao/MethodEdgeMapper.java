package com.gqy.analysis_code.dao;

import com.gqy.analysis_code.entity.MethodEdge;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface MethodEdgeMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table methodedge
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table methodedge
     *
     * @mbggenerated
     */
    int insert(MethodEdge record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table methodedge
     *
     * @mbggenerated
     */
    MethodEdge selectByPrimaryKey(Integer id);
    List<MethodEdge> selectBynode(@Param("targetid") Integer targetid, @Param("sourceid") Integer sourceid);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table methodedge
     *
     * @mbggenerated
     */
    List<MethodEdge> selectAll();

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table methodedge
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(MethodEdge record);
}