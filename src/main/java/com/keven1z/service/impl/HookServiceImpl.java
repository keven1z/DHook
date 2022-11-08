package com.keven1z.service.impl;

import com.keven1z.dao.IFieldDao;
import com.keven1z.dao.IHookDao;
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

    @Override
    public HookEntity findHookById(String id) {
        return hookDao.findHookByHookId(id);
    }

    @Override
    public List<HookEntity> findHookAll() {
        return hookDao.findHookAll();
    }

    @Override
    public int addHook(HookEntity hookEntity) {
        List<MethodEntity> methodEntities = hookEntity.getMethodEntities();
        List<FieldEntity> fieldEntities = hookEntity.getFieldEntities();
        int insertCount = hookDao.addHook(hookEntity);
        if (methodEntities == null && fieldEntities == null) return insertCount;

        int hookId = hookEntity.getId();
        if (methodEntities != null) {
            for (MethodEntity methodEntity : methodEntities) {
                methodEntity.setHookId(hookId);
                methodDao.insert(methodEntity);
            }
        }
        if (fieldEntities != null) {
            for (FieldEntity fieldEntity : fieldEntities) {
                fieldEntity.setHookId(hookId);
                fieldDao.insert(fieldEntity);
            }
        }
        return insertCount;
    }

    @Override
    public int updateHook(HookEntity hookEntity) {
        int updateCount = hookDao.update(hookEntity);
        List<MethodEntity> methodEntities = hookEntity.getMethodEntities();
        List<FieldEntity> fieldEntities = hookEntity.getFieldEntities();
        //更新时发现删除部分方法和字段
        int hookId = hookEntity.getId();
        List<FieldEntity> originFieldEntityList = fieldDao.findFieldAll(hookId);
        if (originFieldEntityList.size() != fieldEntities.size()){
            for (FieldEntity fieldEntity:originFieldEntityList){
                int fieldId = fieldEntity.getFieldId();
                if(!isInField(fieldId,fieldEntities)) fieldDao.deleteByFieldId(fieldId);
            }
        }

        List<MethodEntity> originMethodEntities = methodDao.findMethodAll(hookId);
        if (originMethodEntities.size() != methodEntities.size()){
            for (MethodEntity methodEntity:originMethodEntities){
                int methodId = methodEntity.getMethodId();
                if(!isInMethod(methodId,methodEntities)) methodDao.deleteByMethodId(methodId);
            }
        }

        for (MethodEntity methodEntity : methodEntities) {
            int methodId = methodEntity.getMethodId();
            //更新时新增的方法
            if (methodId == -1){
                methodEntity.setHookId(hookEntity.getId());
                methodDao.insert(methodEntity);
            }
            else methodDao.update(methodEntity);
        }
        for (FieldEntity fieldEntity : fieldEntities) {
            int fieldId = fieldEntity.getFieldId();
            if (fieldId == -1){
                fieldDao.insert(fieldEntity);
            }
            else fieldDao.update(fieldEntity);
        }
        return updateCount;
    }
    @Override
    public List<HookEntity> findHooksByAgentId(String agentId) {
        return hookDao.findHooksByAgentId(agentId);
    }

    @Override
    public HookEntity findHooksByHookId(String hookId) {
        return hookDao.findHookByHookId(hookId);
    }

    @Override
    public int deleteHook(int hookId) {
        fieldDao.delete(hookId);
        methodDao.delete(hookId);
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

    @Override
    public List<HookDetailEntity> findHookDetailByHookId(int hookId) {
        return hookDao.findHookDetailByHookId(hookId);
    }

    @Override
    public HookDetailEntity findHookDetailById(int id) {
        return hookDao.findHookDetailById(id);
    }

    @Override
    public List<HookDetailEntity> findHookDetails() {
        return hookDao.findHookDetails();
    }

    @Override
    public int addHookDetail(HookDetailEntity hookDetail) {
        return hookDao.addHookDetail(hookDetail);
    }

    public boolean isInField(int fieldId,List<FieldEntity> fieldEntityList){
        for (FieldEntity fieldEntity:fieldEntityList){
            int id = fieldEntity.getFieldId();
            if (fieldId == id ) return true;
        }
        return false;
    }
    public boolean isInMethod(int methodId,List<MethodEntity> methodEntityList){
        for (MethodEntity methodEntity:methodEntityList){
            int id = methodEntity.getMethodId();
            if (methodId == id ) return true;
        }
        return false;
    }
}
