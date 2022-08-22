package com.keven1z.dao;

import com.keven1z.entity.FieldEntity;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @author keven1z
 * @date 2022/01/05
 */
@Mapper
public interface IFieldDao {

    @Select("select * from field where hook_id=#{id}")
    @Result(id = true,column = "field_id",property = "fieldId")
    @Result(column = "hook_id",property = "hookId")
    List<FieldEntity> findFieldAll(int id);
    @Insert({"INSERT INTO field(name, value,hook_id,sort,type) ",
            "VALUES (#{name},#{value},#{hookId},#{sort},#{type})"})
    @Options(useGeneratedKeys=true, keyProperty="fieldId")
    int insert(FieldEntity fieldEntity);
    @Delete("DELETE FROM field WHERE hook_id = #{hookId}")
    int delete(int hookId);
    @Delete("DELETE FROM field WHERE field_id = #{fieldId}")
    int deleteByFieldId(int fieldId);
    @Update({"update field set name=#{name}, value=#{value} where field_id=#{fieldId}"})
    int update(FieldEntity fieldEntity);
}
