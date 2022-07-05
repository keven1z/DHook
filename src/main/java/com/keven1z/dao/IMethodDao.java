package com.keven1z.dao;

import com.keven1z.entity.MethodEntity;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @author keven1z
 * @date 2022/01/05
 */
@Mapper
public interface IMethodDao {

    @Select("select * from method where hook_id=#{id}")
    @Result(id = true,column = "method_id",property = "methodId")
    @Result(column = "class_name",property = "className")
    @Result(column = "method_name",property = "methodName")
    @Result(column = "hook_id",property = "hookId")
    List<MethodEntity> findMethodAll(int id);

    @Insert({"INSERT INTO method(class_name, method_name, desc,hook_id,sort,type,parameters) ",
            "VALUES (#{className},#{methodName},#{desc},#{hookId},#{sort},#{type},#{parameters})"})
    @Options(useGeneratedKeys=true, keyProperty="methodId")
    int insert(MethodEntity methodEntity);

    @Delete("DELETE FROM method WHERE hook_id = #{hookId}")
    int delete(int hookId);
    @Delete("DELETE FROM method WHERE method_id = #{methodId}")
    int deleteByMethodId(int methodId);

    @Update("update method set  class_name = #{className},method_name=#{methodName},desc=#{desc},parameters=#{parameters},sort=#{sort} WHERE method_id = #{methodId}")
    int update(MethodEntity methodEntity);
}
