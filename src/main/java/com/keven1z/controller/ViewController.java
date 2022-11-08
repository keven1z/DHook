package com.keven1z.controller;

import com.keven1z.entity.AgentEntity;
import com.keven1z.entity.HookDetailEntity;
import com.keven1z.entity.HookEntity;
import com.keven1z.exception.HttpResponseException;
import com.keven1z.http.ErrorEnum;
import com.keven1z.service.IAgentService;
import com.keven1z.service.IHookService;
import org.springframework.stereotype.Controller;
import org.springframework.util.SerializationUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * @author keven1z
 * @date 2021/12/27
 */
@Controller
@RequestMapping("/")
public class ViewController {
    @Resource
    public IAgentService agentService;
    @Resource
    public IHookService hookService;

    /**
     * 推送给所有用户
     */
    @GetMapping("/index.html")
    public String index() {
        return "index";
    }

    @GetMapping("/hook.html")
    public String hook(HttpServletRequest request, @RequestParam(value = "id", required = true) String id) {
        AgentEntity agentById = agentService.findAgentById(id);
        request.setAttribute("id", id);
        if (agentById == null) throw new HttpResponseException(ErrorEnum.E_30001);
        request.setAttribute("applicationName", agentById.getName());
        return "hook";
    }

    @GetMapping("/hookDetail")
    public String hookDetail(HttpServletRequest request, String hookId) {
        if(hookId == null){
            throw new HttpResponseException(ErrorEnum.E_40001);
        }

        HookEntity hookEntity = hookService.findHookById(hookId);
        List<HookDetailEntity> detailEntities = hookService.findHookDetailByHookId(Integer.parseInt(hookId));
        request.setAttribute("hookId", hookId);
        hookEntity.setClassName(hookEntity.getClassName().replaceAll("/","."));
        request.setAttribute("hook", hookEntity);

        request.setAttribute("hd_data",detailEntities);
        return "hook_detail";
    }

}