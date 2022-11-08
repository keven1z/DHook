package com.keven1z.service;

import com.keven1z.entity.ClassMapEntity;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author keven1z
 * @date 2022/01/05
 */
@Service
public interface IClassMapService {
    public int insert(List<ClassMapEntity> classMap);
    public List<ClassMapEntity> findClassMapAll(String agentId);
    int delete(String agentId);
}
