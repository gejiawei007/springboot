package com.example.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.Utils.HttpsUtil;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

public class DemoContrroller {

    Logger logger = Logger.getLogger(HelloController.class);

    public  void weixin(Map map,Map mapp,String appid,String secret,String touser){

        try {

            String apiurl = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid="+appid+"&secret="+secret;

            HttpsUtil httpsUtil = new HttpsUtil();
            JSONObject jsonObject = httpsUtil.HttpsUtil(apiurl,"POST",null);

            String access_token = jsonObject.getString("access_token");

            Map map1 = (Map) map.get("_S_SERIAL");
            Map map2 = (Map)map.get("_S_DATE");
            Map map3 =(Map)map.get("_S_TITLE");

            Map mapp1 = (Map)mapp.get("myPersonInfo");
            Map mapp2 = (Map)mapp.get("myDeptInfo");

            SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日 hh:mm:ss");
            Date datee = new Date((long)map2.get("value"));

            //String url2 = "2f1942833p.imwork.net/view?flowInstId="+mapp.get("flowInstId")+"&formInstId="+mapp.get("formInstId")+"&formCodeId="+mapp.get("formCodeId")+"&formDefId="+mapp.get("formDefId");

            String url2 = "http://2f1942833p.imwork.net/boot/view?flowInstId="+mapp.get("flowInstId")+"&formInstId="+mapp.get("formInstId")+"&formCodeId="+mapp.get("formCodeId")+"&formDefId="+mapp.get("formDefId");

            String url = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=" + access_token;
            String temp = "{\"touser\": \""+touser+"\"," +
                    "\"template_id\": \"TmkgAQSiGet3Nw2lQXLCGecEtSpVoBwLeaFdn7B-EyQ\", " +
                    "\"topcolor\": \"#FF0000\", " +
                    "\"url\": \""+url2+"\", " +
                    "\"data\": " +
                    "{\"first\": {\"value\": \"您有一个待审批事项!\"}," +
                    "\"keyword1\": { \"value\": \""+map3.get("value")+"\"}," +
                    "\"keyword2\": { \"value\": \""+map1.get("value")+"\"}," +
                    "\"keyword3\": { \"value\": \""+format.format(datee)+"\"}," +
                    "\"keyword4\": { \"value\": \""+mapp1.get("name")+"\"}," +
                    "\"keyword5\": { \"value\": \""+mapp2.get("name")+"\"}," +
                    "\"remark\": {\"value\": \""+mapp.get("flowInstId")+"\" }}}";

            System.out.println("url:"+url2);
            String date = sendPost(url,temp);
            System.out.println(jsonObject);
            logger.setLevel(Level.INFO);
            logger.info(jsonObject);

        }catch (Exception e){
            logger.debug("微信出错："+e.getMessage().toString());
        }
    }


    /**
     * 向指定 URL 发送POST方法的请求
     * @param url 发送请求的 URL
     * @param param 请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @return 所代表远程资源的响应结果
     */
    public static String sendPost(String url, String param) {
        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
        try {
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            URLConnection conn = realUrl.openConnection();
            // 设置通用的请求属性
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            //1.获取URLConnection对象对应的输出流
            out = new PrintWriter(conn.getOutputStream());
            //2.中文有乱码的需要将PrintWriter改为如下

            //InputStreamReader reder = new InputStreamReader(conn.getInputStream(), "utf-8");
            out=new PrintWriter(new OutputStreamWriter(conn.getOutputStream(), "utf-8"));
            // 发送请求参数
            out.print(param);
            // flush输出流的缓冲
            out.flush();
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            System.out.println("发送 POST 请求出现异常！" + e);
            e.printStackTrace();
        }
        //使用finally块来关闭输出流、输入流
        finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        System.out.println("post推送结果：" + result);
        return result;
    }
}
