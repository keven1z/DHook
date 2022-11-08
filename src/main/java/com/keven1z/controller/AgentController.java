package com.keven1z.controller;

import com.keven1z.entity.AgentEntity;
import com.keven1z.entity.ConfigEntity;
import com.keven1z.service.IAgentService;
import com.keven1z.service.IClassMapService;
import com.keven1z.service.IConfigService;
import com.keven1z.utils.HttpUtil;
import com.keven1z.utils.JarUtil;
import com.keven1z.utils.UUidUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;

/**
 * @author keven1z
 * @date 2021/12/23
 */
@RestController
@RequestMapping("/agent")
public class AgentController {
    private static final Logger logger = LoggerFactory.getLogger(AgentController.class);

    @Resource
    private IAgentService agentService;
    @Resource
    private IConfigService configService;

    @Resource
    private IClassMapService classMapService;
    /*
     * 查找所有agent
     */
    @GetMapping("/all")
    public List<AgentEntity> index() {
        List<AgentEntity> agentEntities = agentService.findAgentAll();
        return agentEntities;
    }

    @GetMapping("/update")
    public int update(@RequestParam(value = "id") String id, String name) {
        AgentEntity entity = agentService.findAgentById(id);
        if (entity != null) {
            entity.setName(name);
            agentService.update(entity);
            return 1;
        } else {
            return 0;
        }
    }

    /*
     * 删除agent
     */
    @GetMapping("/del")
    public int del(String agentId) {
        return agentService.delete(agentId);
    }

    /*
     * 注册agent
     */
    @PostMapping("/register")
    public int register(@RequestBody AgentEntity agentEntity) {
        logger.info("注册agent:" + agentEntity);
        AgentEntity entity = agentService.findAgentById(agentEntity.getId());
        if (entity != null) {
            agentEntity.setName(entity.getName());

            //删除classmap
            classMapService.delete(agentEntity.getId());
            return agentService.update(agentEntity);
        } else {
            return 0;
        }
    }

    @PostMapping("/add")
    public int add(@RequestParam String name) {
        String uuid = UUidUtil.getUUID();
        try {
            agentService.register(uuid, name);
        } catch (Exception e) {
            return 0;
        }
        return 1;
    }

    /**
     * 导出agent
     *
     * @param id agent id
     * @return
     */
    @GetMapping("/export")
    public ResponseEntity<Object> export(@RequestParam(value = "id") String id) {
        String fileName = "dHook.jar";
        byte[] jar_new;
        try {
            List<ConfigEntity> configEntities = configService.query();
            jar_new = JarUtil.updateConfig(id, configEntities);
        } catch (Exception e) {
            logger.error("导出agent失败:" + e.getMessage());
            return ResponseEntity.badRequest().body("0");
        }
        InputStreamResource resource = new InputStreamResource(new ByteArrayInputStream(jar_new));

        return HttpUtil.responseSource(fileName, resource, jar_new.length);
    }

}
