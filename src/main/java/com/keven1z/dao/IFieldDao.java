package com.keven1z.dao;

import com.keven1z.entity.FieldEntity;
import com.keven1z.entity.MethodEntity;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @author keven1z
 * @date 2022/01/05
 */
@Mapper
public interface IFieldDao {

    @Select("select * from field where ma_id=#{id}")
    @Result(id = true,column = "field_id",property = "fieldId")
    @Result(column = "ma_id",property = "maId")
    List<FieldEntity> findFieldAll(int id);
    @Insert({"INSERT INTO field(name, type, value,ma_id,sort) ",
            "VALUES (#{name},#{type},#{value},#{maId},#{sort})"})
    @Options(useGeneratedKeys=true, keyProperty="fieldId")
    int insert(FieldEntity fieldEntity);
    @Delete("DELETE FROM field WHERE ma_id = #{maId}")
    int delete(int maId);
}
