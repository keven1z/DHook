package com.keven1z.service;

import com.keven1z.entity.PluginEntity;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author keven1z
 * @date 2021/12/27
 */
@Service
public interface IPluginService {
    void insert(PluginEntity pluginEntity);
    List<PluginEntity> queryAll();
    int delete(String fileName,String agentId);
    PluginEntity select(String fileName);
    List<PluginEntity> selectById(String agentId);
}
