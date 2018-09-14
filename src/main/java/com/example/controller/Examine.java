package com.example.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.yzj.Cloudflow;
import com.example.yzj.CloudflowConfiguration;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@RestController
public class Examine {

    @Value("${approver}")
    public String approver;

    private String appId ="SP50641";

    private String secret ="GYFedwFJV9qNRHWzr5dBb7TfZiEUAc";

    private String key ="zkcyot8OGYRCk3uX";

    private String eid ="50641";

    private Cloudflow cloudflow;

    Logger logger = Logger.getLogger(HelloController.class);

    /**
     * 初始化Cloudflow
     * @return
     */
    public Cloudflow play(){

        CloudflowConfiguration configuration = new CloudflowConfiguration();

        configuration.appId =appId;
        configuration.secret = secret;
        configuration.key = key;
        configuration.eid = eid;

        cloudflow = new Cloudflow(configuration);

        return cloudflow;
    }


    /**
     *  同意审核
     * @param request
     */
    @RequestMapping("ToExamine")
    public String ToExamine(HttpServletRequest request, HttpServletResponse response){

        Cloudflow cloudflow= play();

        String flowInstId = request.getParameter("flowInstId"); //  流程实例ID
        String formCodeId = request.getParameter("formCodeId");
        String formDefId = request.getParameter("formDefId");
        String formInstId = request.getParameter("formInstId");

        Map<String,Object> map = new HashMap<>();
        map.put("approver",approver);
        Map<String,Object> map1 = new HashMap<String,Object>();
        map1.put("flowInstId",flowInstId);
        map.put("flow",map1);
        Map<String,Object> map2 = new HashMap<String,Object>();
        map2.put("formCodeId",formCodeId);
        map2.put("formDefId",formDefId);
        map2.put("formInstId",formInstId);
        map.put("form",map2);

        JSONObject jsonObject = cloudflow.agree(map);

        String success =jsonObject.getString("success");
        logger.setLevel(Level.INFO);
        logger.info("同意后返回数据:\n" + jsonObject);

        return success;
    }

    /**
     *  退回
     * @param request
     */
    @RequestMapping("Return")
    public String Return(HttpServletRequest request){

        Cloudflow cloudflow= play();

        String flowInstId = request.getParameter("flowInstId"); //  流程实例ID
        String formCodeId = request.getParameter("formCodeId");
        String formDefId = request.getParameter("formDefId");
        String formInstId = request.getParameter("formInstId");

        Map map = new HashMap<String,Object>();
        map.put("approver",approver);
        Map map1 = new HashMap<String,Object>();
        map1.put("flowInstId",flowInstId);
        map1.put("returnStartActivity",true);
        map.put("flow",map1);

        JSONObject jsonObject = cloudflow.Return(map);

        String success =jsonObject.getString("success");
        logger.setLevel(Level.INFO);
        logger.info("退回后返回数据:\n" + jsonObject);

        return success;

    }
}
