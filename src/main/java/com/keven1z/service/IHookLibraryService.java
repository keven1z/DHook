package com.keven1z.service;

import com.keven1z.entity.HookLibraryEntity;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author keven1z
 * @date 2021/12/27
 */
@Service
public interface IHookLibraryService {
    int add(HookLibraryEntity hookLibraryEntity);
    HookLibraryEntity query(String alias);
    List<HookLibraryEntity> query();
    int update(HookLibraryEntity hookLibraryEntity);
    int delete(int id);
}
