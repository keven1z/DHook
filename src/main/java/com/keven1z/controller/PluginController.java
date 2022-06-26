package com.keven1z.controller;


import com.keven1z.entity.PluginEntity;
import com.keven1z.exception.HttpResponseException;
import com.keven1z.http.ErrorEnum;
import com.keven1z.service.IPluginService;
import com.keven1z.utils.HttpUtil;
import com.keven1z.utils.JarUtil;
import dHook.IDHookExtenderCallbacks;

import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


/**
 * @author keven1z
 * @date 2021/12/27
 */
@RestController
@RequestMapping("/plugin")
public class PluginController {
    @Resource
    private IPluginService pluginService;
    @PostMapping("/add")
    public String addPlugin(@RequestParam("plugin") MultipartFile file,String agentId) throws Exception {
        // 设置上传至项目文件夹下的uploadFile文件夹中，没有文件夹则创建
        File dir = new File("plugins");
        if (!dir.exists()) {
            dir.mkdirs();
        }
        String filename = file.getOriginalFilename();
        if (filename == null || !filename.endsWith(".jar")){
            throw new HttpResponseException(ErrorEnum.E_10000);
        }
        String path = dir.getAbsolutePath() + File.separator + file.getOriginalFilename();
        try{
            file.transferTo(new File(path));
            IDHookExtenderCallbacks extenderCallbacks = JarUtil.loadJar("file:"+path);
            PluginEntity pluginEntity = new PluginEntity();
            pluginEntity.setPluginName(extenderCallbacks.getExtensionName());
            pluginEntity.setDesc(extenderCallbacks.getExtensionDesc());
            pluginEntity.setFileName(file.getOriginalFilename());
            pluginEntity.setAgentId(agentId);
            pluginEntity.setFilePath(path);
            pluginService.insert(pluginEntity);
        }
        catch (IOException e){
            throw  new HttpResponseException(ErrorEnum.E_20001);
        }
        catch (Exception e){
            throw  new HttpResponseException(ErrorEnum.E_10001);
        }

        return "1";
    }
    @GetMapping("/all")
    public List<PluginEntity> all(){
        List<PluginEntity> pluginEntities = pluginService.queryAll();
        return pluginEntities;
    }
    @GetMapping("/select")
    public ArrayList<String> select(String agentId){
        List<PluginEntity> pluginEntities = pluginService.selectById(agentId);
        ArrayList<String> arrayList = new ArrayList<>();
        for (PluginEntity entity:pluginEntities){
            arrayList.add(entity.getFileName());
        }
        return arrayList;
    }
    @GetMapping("/download")
    public ResponseEntity<Object> getPlugins(String fileName) {
        PluginEntity pluginEntity = pluginService.select(fileName);
        String path = pluginEntity.getFilePath();
        File file = new File(path);
        try {
            InputStreamResource inputStreamResource = new InputStreamResource(new FileInputStream(file));
            return HttpUtil.responseSource(file.getName(), inputStreamResource, file.length());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("0");
        }
    }
    @GetMapping("/uninstall")
    public boolean uninstall(String pluginName){
        int delete = pluginService.delete(pluginName);
        if (delete == 1){
            PluginEntity pluginEntity = pluginService.select(pluginName);
            String filePath = pluginEntity.getFilePath();
            File file = new File(filePath);
            if (file.exists()) return file.delete();
        }
        return false;
    }

}