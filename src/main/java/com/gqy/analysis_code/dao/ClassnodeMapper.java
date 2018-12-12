package com.gqy.analysis_code.dao;

import com.gqy.analysis_code.entity.Classnode;
import java.util.List;

public interface ClassnodeMapper {

    List<Classnode> selectByfullname(String fullname);


    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table classnode
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table classnode
     *
     * @mbggenerated
     */
    int insert(Classnode record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table classnode
     *
     * @mbggenerated
     */
    Classnode selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table classnode
     *
     * @mbggenerated
     */
    List<Classnode> selectAll();

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table classnode
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(Classnode record);
}