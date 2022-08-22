package com.keven1z.controller;

import com.keven1z.entity.ConfigEntity;
import com.keven1z.service.IConfigService;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.util.List;

/**
 * @author keven1z
 * @date 2022/07/28
 */
@RestController
@RequestMapping("/config")
public class ConfigController {
    @Resource
    private IConfigService configService;
    @PostMapping("/add")
    public String add(@RequestBody ConfigEntity configEntity){
        return String.valueOf(configService.add(configEntity));
    }
    @GetMapping("/query")
    public List<ConfigEntity> query(){
        return configService.query();
    }
}
