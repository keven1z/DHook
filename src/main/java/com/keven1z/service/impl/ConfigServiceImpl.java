package com.keven1z.service.impl;

import com.keven1z.dao.IConfigDao;
import com.keven1z.entity.ConfigEntity;
import com.keven1z.service.IConfigService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author keven1z
 * @date 2022/07/28
 */
@Service
public class ConfigServiceImpl implements IConfigService {
    @Resource
    private IConfigDao configDao;


    @Override
    public int add(ConfigEntity configEntity) {
        return configDao.insert(configEntity);
    }

    @Override
    public List<ConfigEntity> query() {
        return configDao.query();
    }
}
