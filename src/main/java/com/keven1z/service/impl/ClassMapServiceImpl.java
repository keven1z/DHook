package com.keven1z.service.impl;

import com.keven1z.dao.IClassMapDao;
import com.keven1z.entity.ClassMapEntity;
import com.keven1z.service.IClassMapService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author keven1z
 * @date 2022/01/05
 */
@Service
public class ClassMapServiceImpl implements IClassMapService {
    @Resource
    private IClassMapDao classMapDao;

    @Override
    public int insert(List<ClassMapEntity> classMaps) {
        return classMapDao.insert(classMaps);
    }

    @Override
    public List<ClassMapEntity> findClassMapAll(String agentId) {
        return classMapDao.findClassMapAll(agentId);
    }

    @Override
    public int delete(String agentId) {
        return classMapDao.delete(agentId);
    }
}
