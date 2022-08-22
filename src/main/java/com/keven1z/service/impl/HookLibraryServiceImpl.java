package com.keven1z.service.impl;

import com.keven1z.dao.IHookLibraryDao;
import com.keven1z.entity.HookLibraryEntity;
import com.keven1z.service.IHookLibraryService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author keven1z
 * @date 2022/08/08
 */
@Service
public class HookLibraryServiceImpl implements IHookLibraryService {
    @Resource
    private IHookLibraryDao hookLibraryDao;

    @Override
    public int add(HookLibraryEntity hookLibraryEntity) {
        return hookLibraryDao.insert(hookLibraryEntity);
    }

    @Override
    public HookLibraryEntity query(String alias) {
        return hookLibraryDao.query(alias);
    }

    @Override
    public List<HookLibraryEntity> query() {
        return hookLibraryDao.queryAll();
    }

    @Override
    public int update(HookLibraryEntity hookLibraryEntity) {
        return hookLibraryDao.update(hookLibraryEntity);
    }

    @Override
    public int delete(int id) {
        return hookLibraryDao.delete(id);
    }
}
