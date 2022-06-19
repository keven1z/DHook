package com.keven1z.controller;


import com.keven1z.entity.PluginEntity;
import com.keven1z.exception.HttpResponseException;
import com.keven1z.handler.ErrorEnum;
import com.keven1z.service.IPluginService;
import com.keven1z.utils.GsonUtil;
import com.keven1z.utils.JarUtil;
import dHook.IDHookExtenderCallbacks;
import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
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
    public String addPlugin(@RequestParam("plugin") MultipartFile file) throws Exception {
        // 设置上传至项目文件夹下的uploadFile文件夹中，没有文件夹则创建
        File dir = new File("plugins");
        if (!dir.exists()) {
            dir.mkdirs();
        }
        String filename = file.getOriginalFilename();
        if (filename == null || !filename.endsWith(".jar")){
            throw new HttpResponseException(ErrorEnum.E_20011);
        }
        String path = dir.getAbsolutePath() + File.separator + file.getOriginalFilename();
        file.transferTo(new File(path));
        try{
            IDHookExtenderCallbacks extenderCallbacks = JarUtil.loadJar(path);
            PluginEntity pluginEntity = new PluginEntity();
            pluginEntity.setPluginName(file.getOriginalFilename());
            pluginEntity.setDesc(extenderCallbacks.getExtensionDesc());
            pluginEntity.setFileName(path);
            pluginService.insert(pluginEntity);
        }
        catch (Exception e){
            return e.getMessage();
        }

        return "上传完成！文件名：" + file.getName();
    }
    @GetMapping("/all")
    public List<PluginEntity> all(){
        List<PluginEntity> pluginEntities = pluginService.queryAll();
        return pluginEntities;
    }

}