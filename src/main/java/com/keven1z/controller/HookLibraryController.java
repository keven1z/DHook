package com.keven1z.controller;

import com.keven1z.entity.HookLibraryEntity;
import com.keven1z.service.IHookLibraryService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author keven1z
 * @date 2022/08/10
 */
@RestController
@RequestMapping("/hook_library")
public class HookLibraryController {
    @Resource
    private IHookLibraryService hookLibraryService;
    @GetMapping("/query")
    public List<HookLibraryEntity> query(){
        return hookLibraryService.query();
    }
    @GetMapping("/get")
    public HookLibraryEntity query(String alias){
        return hookLibraryService.query(alias);
    }
    @PostMapping("/update")
    public String update(@RequestBody HookLibraryEntity hookLibraryEntity){
        return String.valueOf(hookLibraryService.update(hookLibraryEntity));
    }
    @GetMapping("/del")
    public String delete(String id){
        return String.valueOf(hookLibraryService.delete(Integer.parseInt(id)));
    }
    @PostMapping("/add")
    public String add(@RequestBody HookLibraryEntity hookLibraryEntity){
        return String.valueOf(hookLibraryService.add(hookLibraryEntity));
    }

}
