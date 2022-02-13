package com.keven1z.dao;

import com.keven1z.entity.MethodActionEntity;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;

import java.util.List;

/**
 * @author keven1z
 * @date 2022/01/05
 */
@Mapper
public interface IMethodActionDao {

    @Select("select * from method_action where hook_id=#{hookId}")
    @Results(id="MethodActionMap",value={
    @Result(id = true,column = "ma_id",property = "maId"),
            @Result(column = "type",property = "type"),
            @Result(column = "hook_id",property = "hookId"),
            @Result(column = "ma_id",property = "fields",
            many = @Many(select = "com.keven1z.dao.IFieldDao.findFieldAll",fetchType = FetchType.EAGER)),
            @Result(column = "ma_id",property = "methods",
                    many = @Many(select = "com.keven1z.dao.IMethodDao.findMethodAll",fetchType = FetchType.EAGER)),
    })
    List<MethodActionEntity> findMethodActionAll(int hookId);
    @Insert({"INSERT INTO method_action(type,hook_id) ",
            "VALUES (#{type},#{hookId})"})
    @Options(useGeneratedKeys=true, keyProperty="maId")
    int insert(MethodActionEntity methodAction);

    @Delete("DELETE FROM method_action WHERE hook_id = ${hookId}")
    int delete(int hookId);
}
