package com.example.yzj;

import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * 数据互联控件demo
 * User: fire
 * Date: 2018-07-05
 */
public class ApiIntegrationDemo extends HttpServlet {
    /**
     * 假设将该servlet映射在/api上面，那么数据接口地址就是http://${host}:${port}/api
     * 将这个地址配置到数据互联控件的外部数据源接口地址中。
     * 注意：接口地址必须能通过外网访问
     */

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 解析请求参数
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try (ServletInputStream sis = req.getInputStream()) {
            byte[] buffer = new byte[2048];
            int length;
            while ((length = sis.read(buffer)) != -1) {
                baos.write(buffer, 0, length);
            }
        }
        byte[] bytes = baos.toByteArray();
        String param = new String(bytes, "utf-8");
        System.out.println("received param:");
        System.out.println(param);

        // 自定义逻辑
        // 请求参数中包含有当前表单上的所有能见值，你可以根据这些值编写自定义逻辑
        // 然后返回数据
        // 注意：返回数据格式必须符合开发文档的要求

        // 返回结果
        String response = "{\"pageList\": {\"pageCount\": 2,\"curPage\": 1,\"rowsCount\": 12,\"colList\": [{\"colEnName\": \"id\"}, {\"showName\": \"true\",\"colZhName\": \"供应商名称\",\"widgetType\": \"textWidget\",\"colEnName\": \"gysmc\"}, {\"colZhName\": \"单选框\",\"widgetType\": \"radioWidget\",\"colEnName\": \"Ra_0\"}],\"dataList\": [{\"gysmc\": \"中国石油化工股份有限公司\",\"Ra_0\": {\"options\": [{\"value\": \"11\",\"key\": \"AaBaCcDd\"}]}}]},\"error\": null,\"errorCode\": 0,\"success\": true}";
        try (OutputStream os = resp.getOutputStream()) {
            os.write(response.getBytes("utf-8"));
            os.flush();
        }
    }
}
