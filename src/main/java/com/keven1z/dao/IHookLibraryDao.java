package com.keven1z.dao;

import com.keven1z.entity.HookLibraryEntity;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface IHookLibraryDao {


    @Options(useGeneratedKeys=true, keyProperty="id")
    @Insert({"INSERT INTO hook_library(alias,class_name,method,desc,notes) ",
            "VALUES (#{alias},#{className},#{method},#{desc},#{notes})"})
    int insert(HookLibraryEntity hookLibraryEntity);


    /**
     * @param alias 别名
     * 根据别名查询对应的hook点
     */
    @Result(column = "class_name", property = "className")
    @Select("select * FROM hook_library WHERE alias = #{alias}")
    HookLibraryEntity query(String alias);

    /**
     * 查询所有hook库
     */
    @Result(column = "class_name", property = "className")
    @Select("select * FROM hook_library")
    List<HookLibraryEntity> queryAll();

    @Update("update hook_library set alias = #{alias},class_name=#{className},desc=#{desc},method=#{method},notes=#{notes} WHERE id = #{id}")
    int update(HookLibraryEntity hookLibraryEntity);

    @Delete("DELETE FROM hook_library WHERE id = #{id}")
    int delete(int id);
}
