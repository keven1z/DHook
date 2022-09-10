package com.keven1z.dao;

import com.keven1z.entity.ClassMapEntity;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @author keven1z
 * @date 2022/01/05
 */
@Mapper
public interface IClassMapDao {
    @Insert("<script>"  +
            "INSERT or ignore INTO class_map(className,packageName,agentId) VALUES" +
            "<foreach collection=\"classMaps\" item=\"item1\" index=\"index\"  separator=\",\">" +
            "(#{item1.className},#{item1.packageName},#{item1.agentId})" +
            "</foreach>" +
            "</script>")
    int insert(@Param(value="classMaps") List<ClassMapEntity> classMaps);
    @Select("select * from class_map where agentId=#{agentId}")
    List<ClassMapEntity> findClassMapAll(String agentId);
}
