package com.keven1z.controller;

import com.keven1z.entity.HookEntity;
import com.keven1z.service.IHookService;
import com.keven1z.service.IMethodActionService;
import com.keven1z.utils.GsonUtil;
import com.keven1z.utils.HttpUtil;
import com.keven1z.utils.JarUtil;
import com.keven1z.utils.PluginUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author keven1z
 * @date 2021/12/27
 */
@RestController
@RequestMapping("/hook")
public class HookController {
    private static final Logger logger = LoggerFactory.getLogger(HookController.class);

    @Resource
    private IMethodActionService methodActionService;
    @Resource
    private IHookService hookService;

    @PostMapping("/add")
    public int addHook(@RequestBody HookEntity hookEntity) {
        return hookService.addHook(hookEntity);
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
     * @param id dHook.jar id
     * @return
     */
    @GetMapping("/export")
    public ResponseEntity<String> produce(@RequestParam(value = "id") String id) {
        List<HookEntity> hookEntities = hookService.findHooksByAgentId(id);
        String jsonString = GsonUtil.toJsonString(hookEntities);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment;filename=hook.json")
                .body(jsonString);
    }

    @GetMapping("/export-offline")
    public ResponseEntity<Object> export(@RequestParam(value = "id") String id) throws FileNotFoundException {
        String fileName = "dHook.jar";
        List<HookEntity> hookEntities = hookService.findHooksByAgentId(id);
        String jsonString = GsonUtil.toJsonString(hookEntities);
        byte[] jar_new;
        try {
            jar_new = JarUtil.updateHook(jsonString);
        } catch (Exception e) {
            logger.error("导出agent失败:" + e.getMessage());
            return ResponseEntity.badRequest().body("0");
        }
        InputStreamResource resource = new InputStreamResource(new ByteArrayInputStream(jar_new));
        return HttpUtil.responseSource(fileName, resource, jar_new.length);
    }

    @GetMapping("/get-plugins")
    public String getPlugins(HttpServletResponse response) throws IOException {
        Map<String, String> pluginJarMap = PluginUtil.pluginJarMap;
        ArrayList<String> list = new ArrayList<>();
        for (Map.Entry<String, String> plugin : pluginJarMap.entrySet()) {
            String name = plugin.getKey();
            list.add(name);
        }
        return GsonUtil.toJsonString(list);
    }

    @GetMapping("/getPluginByName")
    public ResponseEntity<Object> getPlugins(String name) {
        Map<String, String> pluginJarMap = PluginUtil.pluginJarMap;
        String path = pluginJarMap.get(name);
        if (path == null) return ResponseEntity.badRequest().body("0");

        File file = new File(path);

        try {
            InputStreamResource inputStreamResource = new InputStreamResource(new FileInputStream(file));
            return HttpUtil.responseSource(name + ".jar", inputStreamResource, file.length());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("0");
        }
    }



}
