package com.keven1z.service;

import com.keven1z.entity.AgentEntity;
import com.keven1z.entity.HookDetailEntity;
import com.keven1z.entity.HookEntity;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author keven1z
 * @date 2021/12/27
 */
@Service
public interface IHookService {
    HookEntity findHookById(String id);
    List<HookEntity> findHookAll();
    int addHook(HookEntity hookEntity);
    int updateHook(HookEntity hookEntity);
    List<HookEntity> findHooksByAgentId(String agentId);
    HookEntity findHooksByHookId(String hookId);
    int deleteHook(int hookId);
    int deleteAll(String agentId);
    List<HookDetailEntity> findHookDetailByHookId(int hookId);
    List<HookDetailEntity> findHookDetails();
    int addHookDetail(HookDetailEntity hookDetail);
}