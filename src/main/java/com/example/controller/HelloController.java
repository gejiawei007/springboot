package com.example.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.yzj.Cloudflow;
import com.example.yzj.CloudflowConfiguration;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

@RestController
@ConfigurationProperties(prefix = "student")
public class HelloController {

    private String appId ="SP50641";

    private String secret ="GYFedwFJV9qNRHWzr5dBb7TfZiEUAc";

    private String key ="zkcyot8OGYRCk3uX";

    private String eid ="50641";

    @Value("${appid}")
    public String appid2;

    @Value("${secret}")
    public String secret2;

    @Value("${touser}")
    public String touser;

    @Value("${approver}")
    public String approver;

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
     *  提交审核后
     * @param request
     * @throws ServletException
     * @throws IOException
     */
    @RequestMapping("/yzj")
    public  void hello(HttpServletRequest request, HttpServletResponse response)throws ServletException, Exception{

        Cloudflow cloudflow= play();

        ServletInputStream ris = request.getInputStream();
        StringBuilder content = new StringBuilder();
        byte[] b = new byte[1024];
        int lens = -1;
        while ((lens = ris.read(b)) > 0) {
            content.append(new String(b, 0, lens));
        }
        String strcont = content.toString();// 内容

        if(strcont!=null||!strcont.equals("")){

            response.getWriter().print("success");
        }

        JSONObject jsonObject = cloudflow.decryptNotification(strcont);

        Map map = (Map) JSON.parse(jsonObject.toString());

        Map map1 = (Map) map.get("data");
        Map map2 = (Map)map1.get("formInfo");
        Map map3 =(Map)map2.get("widgetMap");
        Map map4 = (Map)map3.get("_S_SERIAL");

        Map mapp = (Map)map1.get("basicInfo");

        String _S_SERIAL = (String) map4.get("value");

        if (_S_SERIAL!=null){
            System.out.println("提交完成后推送解密数据:\n" + jsonObject);

            logger.setLevel(Level.INFO);
            logger.info("提交完成后推送解密数据:\n" + jsonObject);

            DemoContrroller demoContrroller = new DemoContrroller();
            demoContrroller.weixin(map3,mapp,appid2,secret2,touser);

            File fp=new File("c:\\test\\"+new Date().getTime()+".txt");

            PrintWriter pfp= new PrintWriter(fp);
            pfp.print(jsonObject);
            pfp.close();

        }else {
            logger.info("模板数据");
        }
    }

    /**
     *  审核完成后
     * @param request
     * @throws ServletException
     * @throws IOException
     */
    @RequestMapping("/Examine")
    public void Examine(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException{

        Cloudflow cloudflow= play();

        ServletInputStream ris = request.getInputStream();
        StringBuilder content = new StringBuilder();
        byte[] b = new byte[1024];
        int lens = -1;
        while ((lens = ris.read(b)) > 0) {
            content.append(new String(b, 0, lens));
        }
        String strcont = content.toString();// 内容

        if(strcont!=null||!strcont.equals("")){

            response.getWriter().print("success");
        }

        JSONObject jsonObject = cloudflow.decryptNotification(strcont);

        System.out.println("审核完成后推送解密数据:\n" + jsonObject);
        logger.setLevel(Level.INFO);
        logger.info("审核完成后推送解密数据:\n" + jsonObject);

        File fp=new File("c:\\test\\"+new Date().getTime()+".txt");

        PrintWriter pfp= new PrintWriter(fp);
        pfp.print(jsonObject);
        pfp.close();
    }


}
