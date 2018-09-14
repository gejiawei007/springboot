<!DOCTYPE html>
<html lang="zh-CN" xmlns:th="http://www.thymeleaf.org">
<meta name="viewport" content="width=device-width, initial-scale=1">
<#--<script th:src="@{/js/jquery-3.3.1.js}" src="/js/jquery-3.3.1.js"></script>
<script th:src="@{/js/bootstrap.min.js}" src="/js/bootstrap.min.js"></script>
<link type="text/css" th:src="@{/css/bootstrap.css}" href="/css/bootstrap.css" />-->
<script typet="text/javascript" src="http://code.jquery.com/jquery-latest.js"></script>
<link href="https://cdn.bootcss.com/bootstrap/3.3.7/css/bootstrap.min.css" rel="stylesheet">
<script src="https://cdn.bootcss.com/jquery/1.12.4/jquery.min.js"></script>
<!-- 加载 Bootstrap 的所有 JavaScript 插件。你也可以根据需要只加载单个插件。 -->
<script src="https://cdn.bootcss.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
<!-- 引入 jQuery Mobile 样式 -->
<link rel="stylesheet" href="http://apps.bdimg.com/libs/jquerymobile/1.4.5/jquery.mobile-1.4.5.min.css">
<!-- 引入 jQuery Mobile 库 -->
<#--<script src="http://apps.bdimg.com/libs/jquerymobile/1.4.5/jquery.mobile-1.4.5.min.js"></script>-->
<body>
<div class="table-responsive">
    <#if status==0>
        <img src="/static/images/FINISH.png">
    <#elseif status=1>
        <img src="/static/images/DISAGREE.png">
    <#elseif status=2>
        <img src="/static/images/RUNNING.png">
    <#else >

    </#if>

    <form method="post" enctype="multipart/form-data" id="Submission">

    <#--<p>msg:${hello}</p>-->
        <table class="table table-bordered">
            <caption style="text-align: center">${_S_TITLE!''}</caption>
            <tbody>
            <tr>
                <td>客户联系人：</td>
                <td>${Te_1!''}</td>
            </tr>
            <tr>
                <td>客户联系电话：</td>
                <td>${Te_0!''}</td>
            </tr>
            <tr>
                <td>客户地址：</td>
                <td>${Te_2!''}</td>
            </tr>
            <tr>
                <td>客户名称：</td>
                <td>${Te_3!''}</td>
            </tr>
            <tr>
                <td>客户模块：</td>
                <td>${Te_4!''}</td>
            </tr>
            <tr>
                <td>数据库账号：</td>
                <td>${Te_5!''}</td>
            </tr>
            <tr>
                <td>数据库密码：</td>
                <td>${Te_6!''}</td>
            </tr>
            <tr>
                <td>流水号：</td>
                <td>${_S_SERIAL!''}</td>
            </tr>
            <tr>
                <td>申请日期：</td>
                <td>${_S_DATE!''}</td>
            </tr>
            <tr>
                <td>注意事项：</td>
                <td>${Ta_1!''}</td>
            </tr>
            <tr>
                <td>实施转服务未完成问题：</td>
                <td>${Ta_0!''}</td>
            </tr>
            <tr>
                <td>提交人：</td>
                <td>${personInfo!''}</td>
            </tr>
            <tr>
                <td>提交人所属部门：</td>
                <td>${deptInfo!''}</td>
            </tr>
            <tr>
                <td>
                    <#--<div class="btn btn-success btn-lg" id="ss" style="cursor: pointer" onclick="ToExamine()">审核</div>-->
                        <#if status==2>
                            <input type="submit" value="审核" class="btn btn-danger btn-lg" id="ss" style="cursor: pointer" onclick="ToExamine()">
                        </#if>

                </td>
                <td>
                        <#--<div class="btn btn-danger btn-lg" id="but"style="cursor:pointer">退回</div>-->
                        <#if status==2>
                            <input type="button" value="退回" class="btn btn-danger btn-lg" id="but" style="cursor: pointer">
                        </#if>

                </td>
            </tr>
            </tbody>
        </table>
        <input type="hidden" value="${flowInstId!''}" name="flowInstId"/>
        <input type="hidden" value="${formInstId!''}" name="formInstId"/>
        <input type="hidden" value="${formCodeId!''}" name="formCodeId"/>
        <input type="hidden" value="${formDefId!''}" name="formDefId"/>
    </form>
</div>
</body>
<style type="text/css">
    td {
        text-align: center;
    }
    .table-responsive{
        position:relative;
    }
    img{
        position:absolute;
        right:0;
        top:0;
    }
</style>
<script>
    function ToExamine() {
        //alert($('#Submission').serialize())
        $.ajax({
            type: "POST",
            dataType: "text",
            url: '/boot/ToExamine',
            data: $('#Submission').serialize(),
            async:false,
            success: function (result) {
                var strresult = result;
                console.info(strresult)
                if (strresult=="true"){

                    alert("审核成功");
                }else {
                    alert("审核失败")
                }
                //加载最大可退金额
                //$("#spanMaxAmount").html(strresult);
            },
            error: function (data) {
                alert("error:" + data.responseText);
            }
        });

    }

    $(function () {

        $("#but").click(function () {

            $.ajax({
                type: "POST",
                dataType: "html",
                url: '/boot/Return',
                data: $('#Submission').serialize(),
                success: function (result) {
                    var strresult = result;
                    if (strresult=="true"){

                        alert("退回成功");
                    }else {
                        alert("退回失败")
                    }
                },
                error: function (data) {
                    alert("error:" + data.responseText);
                }
            });

        });
    })

</script>
</html>