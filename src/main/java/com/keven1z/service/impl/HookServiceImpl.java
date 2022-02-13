package com.keven1z.service.impl;

import com.keven1z.dao.IFieldDao;
import com.keven1z.dao.IHookDao;
import com.keven1z.dao.IMethodActionDao;
import com.keven1z.dao.IMethodDao;
import com.keven1z.entity.*;
import com.keven1z.service.IHookService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author keven1z
 * @date 2021/12/27
 */
@Service
public class HookServiceImpl implements IHookService {
    @Resource
    private IHookDao hookDao;
    @Resource
    private IMethodDao methodDao;
    @Resource
    private IFieldDao fieldDao;
    @Resource
    private IMethodActionDao methodActionDao;

    @Override
    public HookEntity findHookById(String id) {
        return hookDao.findHookById(id);
    }

    @Override
    public List<HookEntity> findHookAll() {
        return hookDao.findHookAll();
    }

    @Override
    public int addHook(HookEntity hookEntity) {
        List<MethodActionEntity> onMethodActionEntity = hookEntity.getOnMethodAction();
        int insert_count = hookDao.addHook(hookEntity);
        if (onMethodActionEntity == null) return insert_count;

        int hook_id = hookEntity.getId();
        for (MethodActionEntity methodActionEntity : onMethodActionEntity) {
            methodActionEntity.setHookId(hook_id);
            methodActionDao.insert(methodActionEntity);
            int maId = methodActionEntity.getMaId();

            List<MethodEntity> methods = methodActionEntity.getMethods();
            if (methods != null) {
                for (MethodEntity method : methods) {
                    method.setMaId(maId);
                    methodDao.insert(method);
                }
            }

            List<FieldEntity> fields = methodActionEntity.getFields();
            if (fields == null) continue;
            for (FieldEntity fieldEntity : fields) {
                fieldEntity.setMaId(maId);
                fieldDao.insert(fieldEntity);
            }
        }
        return insert_count;
    }

    @Override
    public List<HookEntity> findHooksByAgentId(String agentId) {
        return hookDao.findHooksByAgentId(agentId);
    }

    @Override
    public int deleteHook(int hookId) {
        List<MethodActionEntity> methodActionAll = methodActionDao.findMethodActionAll(hookId);
        for (MethodActionEntity methodAction : methodActionAll) {
            int maId = methodAction.getMaId();
            fieldDao.delete(maId);
            methodDao.delete(maId);
        }
        methodActionDao.delete(hookId);
        return hookDao.delete(hookId);
    }

    @Override
    public int deleteAll(String agentId) {
        int delCount = 0;
        List<HookEntity> hooks = hookDao.findHooksByAgentId(agentId);
        for (HookEntity hookEntity : hooks) {
            int id = hookEntity.getId();
            delCount += deleteHook(id);
        }
        return delCount;
    }

}
