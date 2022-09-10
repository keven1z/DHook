package com.keven1z.controller;

import com.keven1z.entity.HookEntity;
import com.keven1z.entity.HookLibraryEntity;
import com.keven1z.entity.PluginEntity;
import com.keven1z.exception.HttpResponseException;
import com.keven1z.http.ErrorEnum;
import com.keven1z.service.IHookLibraryService;
import com.keven1z.service.IHookService;
import com.keven1z.service.IPluginService;
import com.keven1z.utils.GsonUtil;
import com.keven1z.utils.HttpUtil;
import com.keven1z.utils.JarUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.*;
import java.util.List;

/**
 * @author keven1z
 * @date 2021/12/27
 */
@RestController
@RequestMapping("/hook")
public class HookController {
    private static final Logger logger = LoggerFactory.getLogger(HookController.class);

    @Resource
    private IHookService hookService;
    @Resource
    private IPluginService pluginService;
    @Resource
    private IHookLibraryService hookLibraryService;

    @PostMapping("/add")
    public int addHook(@RequestBody HookEntity hookEntity) {
        String className = hookEntity.getClassName();
        if (!className.contains("/")) {
            HookLibraryEntity hookLibraryEntity = hookLibraryService.query(className);
            if (hookLibraryEntity == null) throw new HttpResponseException(ErrorEnum.E_40001);

            hookEntity.setClassName(hookLibraryEntity.getClassName());
            hookEntity.setMethod(hookLibraryEntity.getMethod());
            hookEntity.setDesc(hookLibraryEntity.getDesc());
        }
        return hookService.addHook(hookEntity);
    }

    @PostMapping("/update")
    public int updateHook(@RequestBody HookEntity hookEntity) {
        return hookService.updateHook(hookEntity);
    }

    @GetMapping("/all")
    public List<HookEntity> allHook() {
        return hookService.findHookAll();
    }

    /**
     * 通过agent id查找hook数据
     *
     * @param id dHook.jar id
     * @return hook的json格式数据
     */
    @GetMapping("/find")
    public List<HookEntity> hook(String id) {
        return hookService.findHooksByAgentId(id);
    }

    @GetMapping("/get")
    public HookEntity find(String hookId) {
        return hookService.findHooksByHookId(hookId);
    }

    @GetMapping("/delAll")
    public int delAll(String agent_id) {
        return hookService.deleteAll(agent_id);
    }

    @GetMapping("/del")
    public int del(String hookId) {
        return hookService.deleteHook(Integer.parseInt(hookId));
    }


    /**
     * 导出配置文件
     *
     * @param id agent id
     * @return
     */
    @GetMapping("/export-config")
    public ResponseEntity<String> exportConfig(@RequestParam(value = "id") String id) {
        List<HookEntity> hookEntities = hookService.findHooksByAgentId(id);
        String jsonString = GsonUtil.toJsonString(hookEntities);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment;filename=hook.json")
                .body(jsonString);
    }

    @PostMapping("/export-offline")
    public ResponseEntity<Object> export(@RequestParam(value = "id") String id, String name, String file_name) {
        String fileName = name+".jar";
        PluginEntity entity = pluginService.select(file_name);
        String plugin_path = null;
        if (entity != null) {
            plugin_path = entity.getFilePath();
        }
        List<HookEntity> hookEntities = hookService.findHooksByAgentId(id);
        String jsonString = GsonUtil.toJsonString(hookEntities);
        byte[] jar_new;
        try {
            jar_new = JarUtil.updateHook(jsonString, plugin_path);
        } catch (Exception e) {
            logger.error("导出agent失败:" + e.getMessage());
            return ResponseEntity.badRequest().body("0");
        }
        InputStreamResource resource = new InputStreamResource(new ByteArrayInputStream(jar_new));
        return HttpUtil.responseSource(fileName, resource, jar_new.length);
    }

}
