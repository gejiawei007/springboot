package com.example.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.yzj.Cloudflow;
import com.example.yzj.CloudflowConfiguration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
public class ToExamine {

    @Value("${approver}")
    public String approver;

    private String appId ="SP50641";

    private String secret ="GYFedwFJV9qNRHWzr5dBb7TfZiEUAc";

    private String key ="zkcyot8OGYRCk3uX";

    private String eid ="50641";

    private Cloudflow cloudflow;

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
     *  查询单据
     * @param response
     * @param request
     * @param map
     * @return
     */
    @RequestMapping("view")
    public String test2(HttpServletResponse response, HttpServletRequest request,HashMap<String, Object> map){

        Cloudflow cloudflow= play();

        String flowInstId = request.getParameter("flowInstId");

        String formInstId = request.getParameter("formInstId");

        String formCodeId = request.getParameter("formCodeId");

        String formDefId = request.getParameter("formDefId");

//        获取单据实例
        Map<String,String> pares = new HashMap<String,String>();
        pares.put("formInstId",formInstId);
        pares.put("formCodeId",formCodeId);

       JSONObject jsonObject = cloudflow.getFormInstance(pares);

        SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日 hh:mm:ss");

       if (jsonObject!=null){
           Map date = (Map) JSON.parse(jsonObject.toString());
           Map map1 = (Map) date.get("data");
           Map map2 = (Map)map1.get("formInfo");
           Map map3 =(Map)map2.get("widgetMap");

           Map Te_0 = (Map) map3.get("Te_0");//客户联系电话
           Map Te_1 = (Map) map3.get("Te_1");//客户联系人
           Map Te_2 = (Map) map3.get("Te_2");//客户地址
           Map Te_3 = (Map) map3.get("Te_3");//客户名称
           Map Te_4 = (Map) map3.get("Te_4");//客户模块
           Map Te_5 = (Map) map3.get("Te_5");//数据库账号
           Map Te_6 = (Map) map3.get("Te_6");//数据库密码
           Map _S_SERIAL = (Map) map3.get("_S_SERIAL");//流水号
           Map _S_DATE = (Map) map3.get("_S_DATE");//申请日期
           Map Ta_1 = (Map) map3.get("Ta_1");//注意事项
           Map Ta_0 = (Map) map3.get("Ta_0");//实施转服务未完成问题
           Map _S_TITLE = (Map) map3.get("_S_TITLE");//标题

           Map _S_DEPT = (Map) map3.get("_S_DEPT");
           List<Map> deptIn = (List<Map>) _S_DEPT.get("deptInfo");//所属部门
           Map deptInfo = deptIn.get(0);

           Map _S_APPLY = (Map) map3.get("_S_APPLY");
           List<Map> personIn = (List<Map>) _S_APPLY.get("personInfo");
           Map personInfo = personIn.get(0);//提交人

           Date datee = new Date((long)_S_DATE.get("value"));

           map.put("Te_0",Te_0.get("value"));
           map.put("Te_1",Te_1.get("value"));
           map.put("Te_2",Te_2.get("value"));
           map.put("Te_3",Te_3.get("value"));
           map.put("Te_4",Te_4.get("value"));
           map.put("Te_5",Te_5.get("value"));
           map.put("Te_6",Te_6.get("value"));
           map.put("_S_SERIAL",_S_SERIAL.get("value"));
           map.put("_S_DATE",format.format(datee));
           map.put("Ta_1",Ta_1.get("value"));
           map.put("Ta_0",Ta_0.get("value"));
           map.put("_S_TITLE",_S_TITLE.get("value"));
           map.put("deptInfo",deptInfo.get("name"));
           map.put("personInfo",personInfo.get("name"));

           map.put("flowInstId",flowInstId);
           map.put("formInstId",formInstId);
           map.put("formCodeId",formCodeId);
           map.put("formDefId",formDefId);

//           查询审批状态
           Map<String,Object> pares2 = new HashMap<String,Object>();
           pares2.put("flowInstId",flowInstId);
           JSONObject jsonObject2 = cloudflow.getFlowStatus(pares2);

           Map dataStatus = (Map) JSON.parse(jsonObject2.toString());
           String status = (String) dataStatus.get("data");

           if ("FINISH".equals(status)){//同意已完成
               map.put("status",0);
           }else  if ("DISAGREE".equals(status)){//不同意已完成
               map.put("status",1);
           }else if ("RUNNING".equals(status)){//待审批
               map.put("status",2);
           }else {
               map.put("status",3);
           }

       }

        return "index";
    }

}
