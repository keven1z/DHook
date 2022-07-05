package com.keven1z.service.impl;

import com.keven1z.dao.IPluginDao;
import com.keven1z.entity.PluginEntity;
import com.keven1z.service.IPluginService;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author keven1z
 * @date 2021/12/27
 */

@Service
public class PluginServiceImpl implements IPluginService {
    @Resource
    private IPluginDao pluginDao;

    @Override
    public void insert(PluginEntity pluginEntity) {
        pluginDao.insert(pluginEntity);
    }

    @Override
    public List<PluginEntity> queryAll() {
        return pluginDao.queryAll();
    }

    @Override
    public int delete(String fileName, String agentId) {
        return pluginDao.delete(fileName, agentId);
    }

    @Override
    public PluginEntity select(String fileName) {
        return pluginDao.select(fileName);
    }

    @Override
    public List<PluginEntity> selectById(String agentId) {
        return pluginDao.selectById(agentId);
    }

}
