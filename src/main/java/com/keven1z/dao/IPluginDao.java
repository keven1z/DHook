package com.keven1z.dao;

import com.keven1z.entity.PluginEntity;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface IPluginDao {


    /**
     * 查询所有插件信息
     */
    @Select("select * from plugins")
    @Result(id = true,column = "file_name",property = "fileName")
    @Result(column = "file_path",property = "filePath")
    @Result(column = "plugin_name",property = "pluginName")
    @Result(column = "agent_id",property = "agentId")
    List<PluginEntity> queryAll();

    @Insert({"INSERT OR REPLACE INTO plugins(file_name,plugin_name, file_path,desc,agent_id) ",
            "VALUES (#{fileName},#{pluginName},#{filePath},#{desc},#{agentId})"})
    void insert(PluginEntity pluginEntity);

//    @Update({ "update agent set time = #{time},state = #{state},javaVersion=#{javaVersion},os=#{os},bind_process_name=#{bindProcessName} where id = #{id}" })
//    int update(PluginEntity pluginEntity);
    @Delete("DELETE FROM plugins WHERE file_name = #{fileName} and agent_id = #{agentId}")
    int delete(String fileName,String agentId);

    @Select("select * FROM plugins WHERE file_name = #{fileName}")
    @Result(id = true,column = "file_name",property = "fileName")
    @Result(column = "file_path",property = "filePath")
    @Result(column = "plugin_name",property = "pluginName")
    @Result(column = "agent_id",property = "agentId")
    PluginEntity select(String fileName);

    @Result(id = true,column = "file_name",property = "fileName")
    @Result(column = "file_path",property = "filePath")
    @Result(column = "plugin_name",property = "pluginName")
    @Result(column = "agent_id",property = "agentId")
    @Select("select * FROM plugins WHERE agent_id = #{agentId}")
    List<PluginEntity> selectById(String agentId);
}
