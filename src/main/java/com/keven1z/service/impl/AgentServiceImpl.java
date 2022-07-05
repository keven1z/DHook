package com.keven1z.service.impl;

import com.keven1z.entity.AgentEntity;
import com.keven1z.dao.IAgentDao;
import com.keven1z.service.IAgentService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author keven1z
 * @date 2021/12/27
 */
@Service
public class AgentServiceImpl implements IAgentService {
    @Resource
    private IAgentDao agentDao;

    @Override
    public AgentEntity findAgentById(String id) {
        return agentDao.findAgentById(id);
    }

    @Override
    public List<AgentEntity> findAgentAll() {
        return agentDao.findAgentAll();
    }

    @Override
    public void register(AgentEntity agentEntity) {
        agentDao.insert(agentEntity);
    }

    @Override
    public void register(String id, String name) {
        AgentEntity entity = new AgentEntity();
        entity.setId(id);
        entity.setName(name);
        agentDao.insert(entity);
    }


    @Override
    public int update(AgentEntity agentEntity) {
        return agentDao.update(agentEntity);
    }

    @Override
    public int delete(String agentId) {
        return agentDao.delete(agentId);
    }

    @Override
    public void updateState(String id, int state, String time) {
        agentDao.updateState(id, state, time);
    }


}
