package com.keven1z.controller;

import com.keven1z.entity.ClassInfoEntity;
import com.keven1z.entity.ClassMapEntity;
import com.keven1z.entity.HookEntity;
import com.keven1z.service.IClassInfoService;
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
    @Resource
    private IClassInfoService classInfoService;
    /*
     * 添加classMap
     */
    @PostMapping("/add")
    public int add(@RequestBody List<ClassMapEntity> classMap,@RequestParam String id){
        for (ClassMapEntity classMapEntity:classMap){
            classMapEntity.setAgentId(id);
        }
        return classMapService.insert(classMap);
    }
    @GetMapping("/all")
    public List<ClassMapEntity> allHook(@RequestParam String agentId){
        return classMapService.findClassMapAll(agentId);
    }

    @GetMapping("/class/target/add")
    public int addTarget(String packageName,String className){
        return classInfoService.addClassName(className,packageName);
    }
    @GetMapping("/class/seek")
    public ClassInfoEntity seek(String packageName,String className){
        return classInfoService.findClassInfo(className,packageName);
    }
    @GetMapping("/class/get")
    public List<ClassInfoEntity> getNoClassInfo(){
        return classInfoService.getClassInfoByFlag(0);
    }
    @PostMapping("/class/info/add")
    public int addClassInfo(@RequestBody List<ClassInfoEntity> classInfoEntities){
        for (ClassInfoEntity classInfo: classInfoEntities) {
            classInfoService.insert(classInfo);
        }
        return classInfoEntities.size();
    }
}
