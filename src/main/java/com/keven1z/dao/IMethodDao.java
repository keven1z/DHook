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

    @Select("select * from method where ma_id=#{id}")
    @Result(id = true,column = "method_id",property = "methodId")
    @Result(column = "class_name",property = "className")
    @Result(column = "method_name",property = "methodName")
    @Result(column = "ma_id",property = "maId")
    List<MethodEntity> findMethodAll(int id);

    @Insert({"INSERT INTO method(class_name, method_name, desc,ma_id,sort) ",
            "VALUES (#{className},#{methodName},#{desc},#{maId},#{sort})"})
    @Options(useGeneratedKeys=true, keyProperty="methodId")
    int insert(MethodEntity methodEntity);

    @Delete("DELETE FROM method WHERE ma_id = ${maId}")
    int delete(int maId);
}
