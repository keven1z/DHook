package com.keven1z.utils;

import com.keven1z.entity.AgentEntity;
import com.keven1z.service.IAgentService;
import com.keven1z.service.IClassMapService;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

/**
 * @author keven1z
 * @date 2022/01/04
 */
@Component
public class AgentUtil {
    @Resource
    private IAgentService agentService;
    @Resource
    private IClassMapService classMapService;

    public static AgentUtil agentUtil;

    @PostConstruct
    public void init() {
        agentUtil = this;
    }

    public static void update(String id, int state, String time) {
        agentUtil.agentService.updateState(id, state, time);
    }
    public static void register(AgentEntity agentEntity) {
        agentUtil.agentService.register(agentEntity);
        agentUtil.classMapService.delete(agentEntity.getId());
    }
}
