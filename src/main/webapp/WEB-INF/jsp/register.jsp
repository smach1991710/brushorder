<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ include file="common.jsp"%>
<!DOCTYPE HTML>
<html><head><meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>会员注册 - 评论+</title>

    <link rel="stylesheet" type="text/css" href="<%=path%>/resource/brush/stylesheet.css">
	<script src="<%=path%>/resource/brush/push.js"></script>
	<script src="<%=path%>/resource/brush/jquery-2.1.1.min.js" type="text/javascript"></script>
	<script src="<%=path%>/resource/brush/bootstrap.min.js" type="text/javascript"></script>
	<script src="<%=path%>/resource/brush/bootstrap-datetimepicker.min.js" type="text/javascript"></script>
	<script src="<%=path%>/resource/brush/common.js" type="text/javascript"></script>
	<script src="<%=path%>/resource/brush/dates.js" type="text/javascript"></script>
	<script src="<%=path%>/resource/brush/echarts.common.min.js" type="text/javascript"></script>

	<!-- multiSelect -->

	<script src="<%=path%>/resource/brush/bootstrap-multiselect.js" type="text/javascript"></script>
	<link href="<%=path%>/resource/brush/bootstrap-multiselect.css" rel="stylesheet">

	<script type="text/javascript">
		$(document).ready(function() {
			$('.multiselect').multiselect({
				 buttonWidth: '80%'
			 });

		});
	</script>

	<style>
		.multiselect{
				 height: 30px;
				 text-align: right;
			  }
		body {font-size: 4px;}
	</style>
	
	<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=2">
    <meta http-equiv="X-UA-Compatible" content="IE=10">
    <link href="<%=path%>/resource/brush/general.css" rel="stylesheet" type="text/css">
    <link href="<%=path%>/resource/brush/menu.css" rel="stylesheet" type="text/css">
    <script src="<%=path%>/resource/brush/jquery.min.js" type="text/javascript"></script>
    <script src="<%=path%>/resource/brush/comm.js" type="text/javascript"></script>
    <script src="<%=path%>/resource/brush/dates.js" type="text/javascript"></script>
    <link rel="icon" href="http://book.geekey.cn/page/image/ceping.png" type="image/x-icon">

    <script language="javascript">
        (function(){
            var bp = document.createElement('script');
            var curProtocol = window.location.protocol.split(':')[0];
            if (curProtocol === 'https'){
                bp.src = 'https://zz.bdstatic.com/linksubmit/push.js';
            }
            else{
                bp.src = 'http://push.zhanzhang.baidu.com/push.js';
            }
            var s = document.getElementsByTagName("script")[0];
            s.parentNode.insertBefore(bp, s);
        })();
        function checkform() {
            if ($("#user_name").val() == "") {
                alert("请输入昵称");
                $("#user_name").focus();
                return false;
            }
            if ($("#user_email").val() == "") {
                alert("请输入Email");
                $("#user_email").focus();
                return false;
            }
            if ($("#user_phone").val() == "") {
                alert("请输入电话号码");
                $("#user_phone").focus();
                return false;
            }
            if ($("#user_qq").val() == "") {

                alert("请输入QQ号");
                $("#user_qq").focus();
                return false;
            }
            if ($("#user_pwd").val() == "") {
                alert("请输入密码");
                $("#user_pwd").focus();
                return false;
            }

            if ($("#reuser_pwd").val() != $("#user_pwd").val()) {

                alert("两次输入的密码不一样，请重新输入");
                $("#user_pwd").focus();
                return false;
            }
            $("#mainform").submit();
        }
    </script>
    <style>
        img{width: 100%}
        .redister{width:460px; height:450px;  margin:70px auto;  border: 1px solid #DCDCDC;  }
        table.Edit{width:400px;margin:10px auto;}
        .Edit th{text-align: center;font-size: 18px;color: #333333;height: 60px;padding-left: 40px}
        .Edit tr td:first-child{width:80px;text-align:right;padding-right: 10px}
        .Edit tr td input{margin-top: 10px;width: 100%}
        small{font-size: 12px;color: #666666;font-weight: normal}
        small span{font-weight: bold}
    </style><style type="text/css"></style>
</head>

<body>
<div>
    <img src="<%=path%>/resource/brush/logo-top.png"/>
</div>
<form id="mainform" action="http://book.geekey.cn/goto.php?op=User.Action&ac=register" method="POST">
                <div class="redister">
                    <table class="Edit">
                        <tbody><tr>
                            <th colspan="2">新用户注册 <small>(<span class="required">*</span> 为必填项目)</small></th>
                        </tr>
                        <tr>
                            <td><label>昵称：<span class="required">*</span></label>
                            </td>
                            <td>
                                <input id="user_name" name="user[user_name]" type="text" value="" maxlength="16" placeholder="建议和QQ名称保持一致">
                            </td>
                        </tr>
                        <tr>
                            <td><label>手机号码：<span class="required">*</span></label>
                            </td>
                            <td>
                                <input id="user_phone" name="user[user_phone]" type="text" value="" maxlength="11" placeholder="手机号码">
                            </td>
                        </tr>
                        <tr>
                            <td><label>邮箱：<span class="required">*</span></label>
                            </td>
                            <td>
                                <input id="user_email" name="user[user_email]" type="text" value="" maxlength="32">
                                <div style="color:red;"></div>
                            </td>
                        </tr>
                        <tr>
                            <td><label>QQ：<span class="required">*</span></label>
                            </td>
                            <td>
                                <input id="user_qq" name="user[user_qq]" type="text" value="" maxlength="32">
                            </td>
                        </tr>
                        <tr>
                            <td><label>密码：<span class="required">*</span></label>
                            </td>
                            <td>
                                <input id="user_pwd" name="user[user_pwd]" type="password" value="" maxlength="16">
                            </td>
                        </tr>
                        <tr>
                            <td><label>重复密码：<span class="required">*</span></label>
                            </td>
                            <td>
                                <input id="reuser_pwd" type="password" value="">
                            </td>
                        </tr>
                        <tr>
                            <td></td>
                            <td>
                                <input type="button" style="margin-top: 20px" value="提交" onclick="return checkform();">
                            </td>
                        </tr>
                    </tbody></table>
                </div>

        <div style="height:60px;">
                <div style="text-align:center;">2017-2018 即刻跨境 <a href="http://book.geekey.cn/">评论+</a></div>
        </div>

</form>

</body></html>