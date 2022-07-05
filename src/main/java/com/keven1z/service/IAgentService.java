package com.keven1z.service;

import com.keven1z.entity.AgentEntity;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author keven1z
 * @date 2021/12/27
 */
@Service
public interface IAgentService {
    AgentEntity findAgentById(String id);
    List<AgentEntity> findAgentAll();
    void register(AgentEntity agentEntity);
    void register(String id, String name);
    int update(AgentEntity agentEntity);
    int delete(String agentId);
    void updateState(String id, int state, String time);
}
