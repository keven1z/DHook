package com.keven1z.service.impl;

import com.keven1z.dao.IFieldDao;
import com.keven1z.dao.IMethodActionDao;
import com.keven1z.dao.IMethodDao;
import com.keven1z.entity.MethodActionEntity;
import com.keven1z.service.IMethodActionService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author keven1z
 * @date 2022/01/10
 */
@Service
public class MethodActionService implements IMethodActionService {
    @Resource
    private IMethodActionDao methodActionDao;
    @Resource
    private IMethodDao methodDao;
    @Resource
    private IFieldDao fieldDao;
    @Override
    public List<MethodActionEntity> findMethodActionAll(int id) {
        return methodActionDao.findMethodActionAll(id);
    }

    @Override
    public int delete(int hookId) {
        return methodActionDao.delete(hookId);
    }
}
