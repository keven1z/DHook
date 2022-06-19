package com.keven1z.dao;

import com.keven1z.entity.PluginEntity;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface IPluginDao {
//    @Select("SELECT * FROM agent WHERE id = #{id}")
//    @Result(column = "bind_process_name",property = "bindProcessName")
//    AgentEntity findAgentById(String id);

    @Select("select * from plugins")
    @Result(id = true,column = "plugin_name",property = "pluginName")
    @Result(column = "file_name",property = "fileName")
    List<PluginEntity> queryAll();
    @Insert({"INSERT OR REPLACE INTO plugins(plugin_name, file_name,desc) ",
            "VALUES (#{pluginName},#{fileName},#{desc})"})
    void insert(PluginEntity pluginEntity);

//    @Update({ "update agent set time = #{time},state = #{state},javaVersion=#{javaVersion},os=#{os},bind_process_name=#{bindProcessName} where id = #{id}" })
//    int update(PluginEntity pluginEntity);
    @Delete("DELETE FROM plugins WHERE plugin_name = #{pluginName}")
    int delete(String pluginName);
}
