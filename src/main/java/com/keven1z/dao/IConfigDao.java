package com.keven1z.dao;

import com.keven1z.entity.ConfigEntity;
import org.apache.ibatis.annotations.*;
import java.util.List;

@Mapper
public interface IConfigDao {

    /**
     * 插入config配置，若已存在该配置则会覆盖配置信息
     */
    @Insert({"INSERT OR REPLACE INTO config(name,value) ",
            "VALUES (#{name},#{value})"})
    int insert(ConfigEntity configEntity);


    /**
     * @param name 配置名称
     * 获取该配置名对应的配置值
     */
    @Select("select value FROM config WHERE name = #{name}")
    String get(String name);

    /**
     * 查询所有配置信息
     */
    @Select("select * FROM config")
    List<ConfigEntity> query();

}
