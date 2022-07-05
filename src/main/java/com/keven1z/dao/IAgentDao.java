package com.keven1z.dao;

import com.keven1z.entity.AgentEntity;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface IAgentDao {
    @Select("SELECT * FROM agent WHERE id = #{id}")
    @Result(column = "bind_process_name",property = "bindProcessName")
    AgentEntity findAgentById(String id);

    @Select("select * from agent")
    @Result(column = "bind_process_name",property = "bindProcessName")
    List<AgentEntity> findAgentAll();
    @Insert({"INSERT INTO agent(id, name, state, time,javaVersion,os) ",
            "VALUES (#{id},#{name},#{state},#{time},#{javaVersion},#{os})"})
    void insert(AgentEntity agentEntity);

    @Update({ "update agent set name=#{name},time = #{time},state = #{state},javaVersion=#{javaVersion},os=#{os},bind_process_name=#{bindProcessName} where id = #{id}" })
    int update(AgentEntity agentEntity);
    @Update({ "update agent set time = #{time},state = #{state} where id = #{id}" })
    int updateState(String  id,int state,String time);
    @Delete("DELETE FROM agent WHERE id = #{agentId}")
    int delete(String agentId);
}
