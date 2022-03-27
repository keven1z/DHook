package com.keven1z.dao;

import com.keven1z.entity.AgentEntity;
import com.keven1z.entity.HookEntity;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;

import java.util.List;

@Mapper
public interface IHookDao {
    @Select("SELECT * FROM hook WHERE id = #{id}")
    @Results(value={
            @Result(id = true,column = "id",property = "id"),
            @Result(column = "class_name",property = "className"),
            @Result(column = "agent_id",property = "agentId"),
            @Result(column = "return_value",property = "returnValue"),
            @Result(column = "id",property = "onMethodAction",
                    many = @Many(select = "com.keven1z.dao.IMethodActionDao.findMethodActionAll",fetchType = FetchType.LAZY)),
    })
    HookEntity findHookById(String id);
    @Select("select * from hook")
    @Results(value={
            @Result(id = true,column = "id",property = "id"),
            @Result(column = "class_name",property = "className"),
            @Result(column = "agent_id",property = "agentId"),
            @Result(column = "return_value",property = "returnValue"),
            @Result(column = "id",property = "onMethodAction",
                    many = @Many(select = "com.keven1z.dao.IMethodActionDao.findMethodActionAll",fetchType = FetchType.EAGER)),
    })
    List<HookEntity> findHookAll();

    /**
     * @return 增加hook的数量
     */
    @Insert({"INSERT INTO Hook(class_name, method, desc, parameters,return_Value,agent_id) ",
            "VALUES (#{className},#{method},#{desc},#{parameter},#{returnValue},#{agentId})"})
    @Options(useGeneratedKeys=true, keyProperty="id")
    int addHook(HookEntity hookEntity);

    @Select("SELECT * FROM hook WHERE agent_id = #{agentId}")
    @Results(value={
            @Result(id = true,column = "id",property = "id"),
            @Result(column = "class_name",property = "className"),
            @Result(column = "agent_id",property = "agentId"),
            @Result(column = "return_value",property = "returnValue"),
            @Result(column = "id",property = "onMethodAction",
                    many = @Many(select = "com.keven1z.dao.IMethodActionDao.findMethodActionAll",fetchType = FetchType.EAGER)),
    })
    List<HookEntity> findHooksByAgentId(String agentId);

    @Delete("DELETE FROM hook WHERE id = #{id}")
    int delete(int id);
}
