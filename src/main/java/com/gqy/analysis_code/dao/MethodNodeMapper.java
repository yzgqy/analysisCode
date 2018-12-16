package com.gqy.analysis_code.dao;

import com.gqy.analysis_code.entity.MethodNode;
import java.util.List;

public interface MethodNodeMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table methodnode
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table methodnode
     *
     * @mbggenerated
     */
    int insert(MethodNode record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table methodnode
     *
     * @mbggenerated
     */
    MethodNode selectByPrimaryKey(Integer id);

    List<MethodNode> selectByfullname(String fullname);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table methodnode
     *
     * @mbggenerated
     */
    List<MethodNode> selectAll();

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table methodnode
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(MethodNode record);
}