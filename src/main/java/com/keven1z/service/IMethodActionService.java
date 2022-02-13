package com.keven1z.service;

import com.keven1z.entity.MethodActionEntity;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author keven1z
 * @date 2022/01/05
 */
@Service
public interface IMethodActionService {
    public List<MethodActionEntity> findMethodActionAll(int id);
    int delete(int hookId);
}
