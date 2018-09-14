package com.example.yzj;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import okhttp3.*;

import java.io.IOException;
import java.util.Map;
import java.util.UUID;

public class Cloudflow {
    private OkHttpClient client = new OkHttpClient();

    private CloudflowConfiguration configuration;
    private String accessToken;
    private String refreshToken;
    private long expiredUntil;

    public Cloudflow(CloudflowConfiguration configuration) {
        this.configuration = configuration;
    }

    private String post(String url, String param) {
        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create(mediaType, param);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .addHeader("Content-Type", "application/json")
                .addHeader("Cache-Control", "no-cache")
                .build();

        try {
            Response response = client.newCall(request).execute();
            return response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    // 获取accessToken
    // https://yunzhijia.com/cloudflow-openplatform/before/1004
    private void getAccessToken() {
        JSONObject param = new JSONObject();
        param.put("appId", configuration.appId);
        param.put("nonceStr", UUID.randomUUID().toString());
        param.put("timestamp", System.currentTimeMillis());
        String signature = SignUtils.sign(param.getString("appId"),
                param.getString("nonceStr"),
                param.getString("timestamp"),
                configuration.secret);
        param.put("signature", signature);

        String url = "https://yunzhijia.com/gateway/oauth2/token/getTeamAccessToken";
        String retString = post(url, param.toJSONString());

        JSONObject jsonObject = JSON.parseObject(retString);
        System.out.println("获取accessToken:\n" + jsonObject);
        if (jsonObject.getBooleanValue("success")) {
            JSONObject data = jsonObject.getJSONObject("data");
            accessToken = data.getString("accessToken");
            refreshToken = data.getString("refreshToken");
            expiredUntil = System.currentTimeMillis() + data.getIntValue("expireIn") * 1000;
        }
    }

    // 刷新accessToken
    // https://open.yunzhijia.com/openplatform/resourceCenter/doc#/gitbook-wiki/server-api/accessToken.html
    private void refreshAccessToken() {
        JSONObject param = new JSONObject();
        param.put("appId", configuration.appId);
        param.put("eid", configuration.eid);
        param.put("scope", "team");
        param.put("timestamp", System.currentTimeMillis());
        param.put("refreshToken", refreshToken);

        String url = "https://www.yunzhijia.com/gateway/oauth2/token/refreshToken";
        String retString = post(url, param.toJSONString());

        JSONObject jsonObject = JSON.parseObject(retString);
        System.out.println("刷新accessToken:\n" + jsonObject);
        if (jsonObject.getBooleanValue("success")) {
            JSONObject data = jsonObject.getJSONObject("data");
            refreshToken = data.getString("refreshToken");
            accessToken = data.getString("accessToken");
            expiredUntil = System.currentTimeMillis() + data.getIntValue("expireIn") * 1000;
        }
    }

    /**
     * 如果当前时间已经大于accessToken过期时间则刷新accessToken
     */
    private void checkAndRefreshAccessToken() {
        if (expiredUntil == 0L) {
            getAccessToken();
        } else if (System.currentTimeMillis() >= expiredUntil) {
            refreshAccessToken();
        }
    }

    /**
     * 获取单据实例
     * <p>
     * 参考:https://yunzhijia.com/cloudflow-openplatform/other/3003
     *
     * @param param
     * @return
     */
    public JSONObject getFormInstance(Map<String, String> param) {
        checkAndRefreshAccessToken();
        String url = "https://yunzhijia.com/gateway/workflow/form/thirdpart/viewFormInst?accessToken=";
        url += accessToken;
        String retString = post(url, JSON.toJSONString(param));
        JSONObject jsonObject = JSON.parseObject(retString);
        System.out.println("获取单据实例:\n" + jsonObject);
        return jsonObject;
    }

    /**
     * 获取审批痕迹
     * <p>
     * 参考:https://yunzhijia.com/cloudflow-openplatform/other/3004
     *
     * @param param
     * @return
     */
    public JSONObject getFlowRecord(Map<String, String> param) {
        checkAndRefreshAccessToken();
        String url = "https://yunzhijia.com/gateway/workflow/form/thirdpart/getFlowRecord?accessToken=";
        url += accessToken;
        String retString = post(url, JSON.toJSONString(param));
        JSONObject jsonObject = JSON.parseObject(retString);
        System.out.println("获取审批痕迹:\n" + jsonObject);
        return jsonObject;
    }

    /**
     * 发起审批
     * <p>
     * 参考:https://yunzhijia.com/cloudflow-openplatform/other/3005
     *
     * @param param
     * @return
     */
    public JSONObject createInst(Map<String, Object> param) {
        checkAndRefreshAccessToken();
        String url = "https://yunzhijia.com/gateway/workflow/form/thirdpart/createInst?accessToken=";
        url += accessToken;
        String retString = post(url, JSON.toJSONString(param));
        JSONObject jsonObject = JSON.parseObject(retString);
        System.out.println("发起审批:\n" + jsonObject);
        return jsonObject;
    }

    /**
     * 修改表单
     * <p>
     * 注意修改表单不要违反操作人所对应的节点字段权限，
     * 如不能编辑只读字段、必填字段必须要传等等
     * <p>
     * 参考:https://yunzhijia.com/cloudflow-openplatform/other/3006'
     *
     * @param param
     * @return
     */
    public JSONObject modifyInst(Map<String, Object> param) {
        checkAndRefreshAccessToken();
        String url = "https://yunzhijia.com/gateway/workflow/form/thirdpart/modifyInst?accessToken=";
        url += accessToken;
        String retString = post(url, JSON.toJSONString(param));
        JSONObject jsonObject = JSON.parseObject(retString);
        System.out.println("修改表单:\n" + jsonObject);
        return jsonObject;
    }

    /**
     * 获取使用了互联控件的模版列表
     * <p>
     * 参考:https://yunzhijia.com/cloudflow-openplatform/other/3001
     *
     * @param param
     * @return
     */
    public JSONObject getTemplateListByGroupId(Map<String, Object> param) {
        checkAndRefreshAccessToken();
        String url = "https://yunzhijia.com/gateway/workflow/form/thirdpart/getByGroupId?accessToken=";
        url += accessToken;
        String retString = post(url, JSON.toJSONString(param));
        JSONObject jsonObject = JSON.parseObject(retString);
        System.out.println("获取使用了互联控件的模版列表:\n" + jsonObject);
        return jsonObject;
    }

    /**
     * 获取模版
     * <p>
     * 参考:https://yunzhijia.com/cloudflow-openplatform/other/3002
     *
     * @param param
     * @return
     */
    public JSONObject getTemplateByCodeId(Map<String, String> param) {
        checkAndRefreshAccessToken();
        String url = "https://yunzhijia.com/gateway/workflow/form/thirdpart/viewFormDef?accessToken=";
        url += accessToken;
        String retString = post(url, JSON.toJSONString(param));
        JSONObject jsonObject = JSON.parseObject(retString);
        System.out.println("获取模版:\n" + jsonObject);
        return jsonObject;
    }

    /**
     * 解密推送数据()
     * <p>
     * 参考:https://yunzhijia.com/cloudflow-openplatform/external/2003
     *
     * @param cipher
     * @return
     */
    public JSONObject decryptNotification(String cipher) {
        if (cipher == null || cipher.isEmpty()) {
            return null;
        }

        AESEncryptor encryptor = new AESEncryptor(configuration.key);
        String plainText = encryptor.decrypt(cipher);
        return JSON.parseObject(plainText);
    }

    /**
     *  同意接口
     * @param param
     * @return
     */
    public JSONObject agree(Map<String, Object> param) {
        checkAndRefreshAccessToken();
        String url = "http://www.yunzhijia.com/gateway/workflow/form/thirdpart/agree?accessToken=";
        url += accessToken;
        String retString = post(url, JSON.toJSONString(param));
        JSONObject jsonObject = JSON.parseObject(retString);
        System.out.println("同意后返回数据:\n" + jsonObject);
        return jsonObject;
    }

    /**
     *  退回接口
     * @param param
     * @return
     */
    public JSONObject Return(Map<String, Object> param) {
        checkAndRefreshAccessToken();
        String url = "http://www.yunzhijia.com/gateway/workflow/form/thirdpart/return?accessToken=";
        url += accessToken;
        String retString = post(url, JSON.toJSONString(param));
        JSONObject jsonObject = JSON.parseObject(retString);
        System.out.println("退回后返回数据:\n" + jsonObject);
        return jsonObject;
    }

    /**
     *  查询审批状态接口
     * @param param
     * @return
     */
    public JSONObject getFlowStatus(Map<String, Object> param) {
        checkAndRefreshAccessToken();
        String url = "http://www.yunzhijia.com/gateway/workflow/form/thirdpart/getFlowStatus?accessToken=";
        url += accessToken;
        String retString = post(url, JSON.toJSONString(param));
        JSONObject jsonObject = JSON.parseObject(retString);
        System.out.println("查询审批状态后返回数据:\n" + jsonObject);
        return jsonObject;
    }
}
