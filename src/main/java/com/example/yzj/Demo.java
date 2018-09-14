package com.example.yzj;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * https://yunzhijia.com/cloudflow-openplatform/before/1001
 */
public class Demo {
    public static void main(String[] args) {
        CloudflowConfiguration configuration = new CloudflowConfiguration();
        // 开发者设置页面可查询【请改为自己的appId】
        configuration.appId = "SP50641";
        // 开发者设置页面可查询【请改为自己的开发者secret】
        configuration.secret = "GYFedwFJV9qNRHWzr5dBb7TfZiEUAc";
        // 开发者设置页面可查询【请改为自己的开发者key】
        configuration.key = "zkcyot8OGYRCk3uX";
        // 在云之家首页右上角点击我的团队可查询【请改为自己的eid】
        configuration.eid = "50641";

        Cloudflow cloudflow = new Cloudflow(configuration);

        // 获取表单实例
        Map<String, String> param1 = new HashMap<>();
        param1.put("formInstId", "5b8ca94ee3d2ca4469ec7872");
        param1.put("formCodeId", "40eb9f07a34049b0be216e202af7ceac");
        cloudflow.getFormInstance(param1);

//        // 获取审批痕迹
//        Map<String, String> param2 = new HashMap<>();
//        param2.put("formInstId", "5b88dce3e3d2ca3ef0251134");
//        param2.put("formCodeId", "40eb9f07a34049b0be216e202af7ceac");
//        cloudflow.getFlowRecord(param2);

        // 发起审批
//        Map<String, Object> param3 = new HashMap<>();
//        param3.put("formCodeId", "40eb9f07a34049b0be216e202af7ceac");
//        param3.put("creator", "57eca8a4e4b01336f13a8788");
//        param3.put("oids", Collections.singletonList("57eca8a4e4b01336f13a8788"));
//        Map<String, Object> widgetValue = new HashMap<>();
//        widgetValue.put("_S_TITLE", "我是一个小标题");
//        param3.put("widgetValue", widgetValue);
//        cloudflow.createInst(param3);

//        // 获取使用了互联控件的模版列表
//        Map<String, Object> param4 = new HashMap<>();
//        param4.put("groupId", CloudflowConstants.GroupId.LEAVE);
//        Map<String, Object> pageable = new HashMap<>();
//        pageable.put("id", null);
//        pageable.put("type", CloudflowConstants.PageType.FIRST);
//        pageable.put("pageSize", 10);
//        param4.put("pageable", pageable);
//        cloudflow.getTemplateListByGroupId(param4);
//
        // 获取模版
        Map<String, String> param5 = new HashMap<>();
        param5.put("formCodeId", "40eb9f07a34049b0be216e202af7ceac");
        cloudflow.getTemplateByCodeId(param5);
//
//        // 修改表单
//        Map<String, Object> param6 = new HashMap<>();
//        param6.put("formCodeId", "40eb9f07a34049b0be216e202af7ceac");
//        param6.put("formDefId", "5b8ced524ec5ca7bec6b8bb4");
//        param6.put("formInstId", "5b8ca94ee3d2ca4469ec7872");
//        param6.put("creator", "57eca8a4e4b01336f13a8788");
//        Map<String, Object> widgetValue2 = new HashMap<>();
//        param6.put("widgetValue", widgetValue2);
//        cloudflow.modifyInst(param6);
//
//        // 解密推送数据
//        // 其中cipher为接收到的原始推送数据
//        String cipher ="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";
//        JSONObject jsonObject = cloudflow.decryptNotification(cipher);
//
//        System.out.println("推送解密数据:\n" + jsonObject);

        //  同意审核
        Map<String, Object> param7 = new HashMap<>();
        Map<String, String> param7_1 = new HashMap<>();
        Map<String, String> param7_2 = new HashMap<>();
        param7.put("approver","57eca8a4e4b01336f13a8788");
        param7_1.put("flowInstId","5b8d117d3f18922c841ea6d5");//    流程实例id  只有主动触发接口的数据才有这个值，查询的没有这个值
        param7_1.put("opinion","没有审批意见");
        param7.put("flow",param7_1);
        param7_2.put("formCodeId","40eb9f07a34049b0be216e202af7ceac");
        param7_2.put("formDefId","5b8ced524ec5ca7bec6b8bb4");
        param7_2.put("formInstId","5b8ca94ee3d2ca4469ec7872");
        param7.put("form",param7_2);
        cloudflow.agree(param7);

    }
}
