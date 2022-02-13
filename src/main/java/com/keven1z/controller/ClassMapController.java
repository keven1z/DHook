package com.keven1z.controller;

import com.keven1z.entity.ClassMapEntity;
import com.keven1z.entity.HookEntity;
import com.keven1z.service.IClassMapService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author keven1z
 * @date 2022/01/05
 */
@RestController
@RequestMapping("/classmap")
public class ClassMapController {
    @Resource
    private IClassMapService classMapService;
    /*
     * 添加classMap
     */
    @PostMapping("/add")
    public int add(@RequestBody List<ClassMapEntity> classMap){
        return classMapService.insert(classMap);
    }
    @GetMapping("/all")
    public List<ClassMapEntity> allHook(@RequestParam String agentId){
        return classMapService.findClassMapAll(agentId);
    }
}
