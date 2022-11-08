package com.keven1z.controller;

import com.keven1z.DHookServerApplication;
import com.keven1z.entity.ClassInfoEntity;
import com.keven1z.entity.ClassMapEntity;
import com.keven1z.exception.HttpResponseException;
import com.keven1z.http.ErrorEnum;
import com.keven1z.netty.CustomProtocol;
import com.keven1z.netty.HeartbeatInitializer;
import com.keven1z.service.IClassInfoService;
import com.keven1z.service.IClassMapService;
import com.keven1z.utils.DecompilerUtil;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

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
    public int add(@RequestBody List<ClassMapEntity> classMap, @RequestParam String id) {
        for (ClassMapEntity classMapEntity : classMap) {
            classMapEntity.setAgentId(id);
        }
        return classMapService.insert(classMap);
    }

    @GetMapping("/all")
    public List<ClassMapEntity> allHook(@RequestParam String agentId) {
        return classMapService.findClassMapAll(agentId);
    }

    @GetMapping("/class/detail/get")
    public ClassInfoEntity getClassInfo(String packageName, String className) throws InterruptedException {
        CustomProtocol customProtocol = new CustomProtocol();
        customProtocol.setAction(HeartbeatInitializer.ACTION_GET_CLASS);
        String pc = packageName + "." + className;
        customProtocol.setBody(packageName + "." + className);
        if (!HeartbeatInitializer.ClassMap.contains(pc)) {
            HeartbeatInitializer.HeartQueue.add(customProtocol);
        }
        int count = 0;
        while(!HeartbeatInitializer.ClassMap.contains(pc)){
            Thread.sleep(500);
            if (count > 15){
                throw new HttpResponseException(ErrorEnum.E_50001);
            }
            count ++;
        }
        ClassInfoEntity classInfoEntity = classInfoService.findClassInfo(className, packageName);
        if (classInfoEntity == null) throw new HttpResponseException(ErrorEnum.E_40001);
        return classInfoEntity;

    }

    @GetMapping("/class/seek")
    public ClassInfoEntity seek(String packageName, String className) {
        ClassInfoEntity classInfo = classInfoService.findClassInfo(className, packageName);
        if (classInfo == null) throw new HttpResponseException(ErrorEnum.E_30001);
        return classInfo;
    }

    @GetMapping("/class/get")
    public List<ClassInfoEntity> getNoClassInfo() {

        return classInfoService.getClassInfoByFlag(0);
    }

    @PostMapping("/class/info/add")
    public int addClassInfo(@RequestBody List<ClassInfoEntity> classInfoEntities) {
        for (ClassInfoEntity classInfo : classInfoEntities) {
            if (classInfo != null){
                classInfoService.insert(classInfo);
                HeartbeatInitializer.ClassMap.add(classInfo.getPackageName()+"."+classInfo.getClassName());
            }

        }
        return classInfoEntities.size();
    }

    @PostMapping("/class/code/add")
    public int addClassCode(@RequestBody Map<String, String> map) {
        String cp = map.get("cp");
        String code = map.get("code");
        if (cp != null && code != null) {
            HeartbeatInitializer.CodeMap.put(cp, code);
        }
        return 1;
    }

    @GetMapping("/class/code/get")
    public ResponseEntity<Object> receiveClassCode(String packageName, String className) throws Exception {
        CustomProtocol customProtocol = new CustomProtocol();
        customProtocol.setAction(HeartbeatInitializer.ACTION_GET_CODE);
        String pc = packageName + "." + className;
        customProtocol.setBody(packageName + "." + className);
        if (!HeartbeatInitializer.CodeMap.containsKey(pc)) {
            HeartbeatInitializer.HeartQueue.add(customProtocol);
            Thread.sleep(2000L);
        }
        String json = HeartbeatInitializer.CodeMap.get(packageName + "." + className);
        if (json == null) throw new HttpResponseException(ErrorEnum.E_20002);
        byte[] code = Base64.getDecoder().decode(json);
        String sourceCode = DecompilerUtil.getDecompilerString(code, pc);
        StringBuilder stringBuilder = new StringBuilder();

        Arrays.stream(sourceCode.split("\n")).map(DecompilerUtil::matchStringByRegularExpression).forEach(matched -> stringBuilder.append(matched).append("\n"));

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment;filename=" + className + ".java")
                .body(stringBuilder);
    }

}
