package com.keven1z.service;

import com.keven1z.entity.ConfigEntity;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author keven1z
 * @date 2021/12/27
 */
@Service
public interface IConfigService {
    int add(ConfigEntity configEntity);
    List<ConfigEntity> query();
}
