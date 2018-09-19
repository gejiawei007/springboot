package com.example.controller;

import com.example.eneity.Students;
import com.example.service.StudentsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.List;

@RestController
@RequestMapping("/test")
@Api(value = "TestController", description = "测试Controller")
public class TestController {

    public  static Logger logger = Logger.getLogger(HelloController.class);

    @Autowired
    public StudentsService studentsService;



    //@RequestMapping("/test")
    /*public void test(HttpServletRequest request, HttpServletResponse response) throws Exception{

        String signature = request.getParameter("signature");

        String timestamp = request.getParameter("timestamp");

        String nonce = request.getParameter("nonce");

        String echostr = request.getParameter("echostr");

        if (echostr.isEmpty()){
            echostr = "6700614368844681085";
        }

        //String Token = request.getParameter("Token");

        System.out.println("微信参数:signature---"+signature);
        System.out.println("微信参数:timestamp---"+timestamp);
        System.out.println("微信参数:nonce---"+nonce);
        System.out.println("微信参数:echostr---"+echostr);
        //System.out.println("微信参数:Token---"+Token);

        PrintWriter out = response.getWriter();

        out.print(echostr);
    }*/

    @ApiOperation(value = "添加一个学生")
    @ApiImplicitParam(dataType = "Students",name = "students",value = "学生实体",required = true)
    @RequestMapping(value = "/insert",method = RequestMethod.POST)
    public String insert(@RequestBody Students students){

        int count = studentsService.add(students);

        if (count>0){
            return "success";
        }else {
            return "fail";
        }
    }

    @ApiOperation("获取学生列表")
    @RequestMapping(value = "select",method = RequestMethod.GET)
    public List<Students> select(){

        List<Students> list = studentsService.list();

        return list;
    }

    /*public static  void main(String[] args) {

        try {
            //访问地址
            *//*String TOKEN_URL = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";
            //appid
            String APPID = "wx1fc314f0b34db154";
            //appsecret
            String APPSECRET = "78282cb463a145ce8aea4d9d58a0e716";

            String request_url = TOKEN_URL.replace("APPID", APPID).replace("APPSECRET", APPSECRET);
            HttpsUtil httpsUtil = new HttpsUtil();
            ;
            System.out.println(request_url);
            int i = 0;
            boolean test = true;

            while (test) {
                JSONObject jsonObject = httpsUtil.HttpsUtil(request_url, "GET", null);
                if (null != jsonObject) {
                    String access_tocken = jsonObject.getString("access_token");
                    String expires_in = jsonObject.getString("expires_in");
                    //获取到的access_tocken值可以写入到文本文件中供其他业务逻辑调用，实例中只打印了没有保存到文件
                    System.out.println("获取成功" + "access_tocken=" + access_tocken + "||expires_in=" + expires_in);
                    i = Integer.parseInt(expires_in);
                }
                //休眠1小时57分钟，提前三分钟获取新的access_token
                //sleep((7200-180)*1000);
                test = false;
            }*//*
            //使用默认的配置信息，不需要写log4j.properties
            BasicConfigurator.configure();
            //设置日志输出级别为info，这将覆盖配置文件中设置的级别
            logger.setLevel(Level.INFO);
            //下面的消息将被输出
            logger.info("this is an info");
            logger.warn("this is a warn");
            logger.error("this is an error");
            logger.fatal("this is a fatal");
            logger.debug("this is an debug");




        }catch (Exception e){


        }
    }*/
}
