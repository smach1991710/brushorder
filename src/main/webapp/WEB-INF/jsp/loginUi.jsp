<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ include file="common.jsp"%>
<!DOCTYPE HTML>
<html style="height:100%;"><head><meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    
    <title>评论加+ 亚马逊测评</title>
    <link rel="icon" href="http://book.geekey.cn/page/image/ceping.png" type="image/x-icon">
    <link rel="stylesheet" type="text/css" href="<%=path%>/resource/login/stylesheet.css">

	<script src="<%=path%>/resource/brush/push.js"></script>
	<script src="<%=path%>/resource/brush/jquery-2.1.1.min.js" type="text/javascript"></script>
	<script src="<%=path%>/resource/brush/bootstrap.min.js" type="text/javascript"></script>
	<script src="<%=path%>/resource/brush/bootstrap-datetimepicker.min.js" type="text/javascript"></script>
	<script src="<%=path%>/resource/brush/common.js" type="text/javascript"></script>
	<script src="<%=path%>/resource/brush/dates.js" type="text/javascript"></script>
	<script src="<%=path%>/resource/brush/echarts.common.min.js" type="text/javascript"></script>

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
	</style>    
	<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=2">
    <meta http-equiv="X-UA-Compatible" content="IE=10">
    
	<link href="<%=path%>/resource/brush/general.css" rel="stylesheet" type="text/css">
    <link href="<%=path%>/resource/brush/menu.css" rel="stylesheet" type="text/css">
    <script src="<%=path%>/resource/brush/jquery.min.js" type="text/javascript"></script>
    <script src="<%=path%>/resource/brush/comm.js" type="text/javascript"></script>
    <script src="<%=path%>/resource/brush/dates.js" type="text/javascript"></script>

    <script>
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
    </script>

</head>
<body data-spm="1">
<div>
    <a href="#">
        <img src="<%=path%>/resource/brush/logo-top.png" style="width:100%;">
    </a>
</div>
<form action="<%=path%>/user/login" id="loginform" method="post">
    <table class="center">
        <tbody><tr>
            <td>
                <a href="#">
                    <img src="<%=path%>/resource/brush/login.png" style="width:688px; margin-left:20px">
                </a>
            </td>
            <td style="width:500px;">
                <div style="font-size: 60px;width: 260px;color: #1ab394;font-weight: bold;">
                    3.0
                </div>

                <div class="login-box-warp" style="margin-left:0 auto;">
                    <div class="login-box no-longlogin " id="J_LoginBox">
                                                请输入账号及密码:
                <div class="field username-field">
                    <label for="TPL_username_1"> <img src="<%=path%>/resource/brush/user.png"></label>
                    <input id="user_user" name="user_user" id="user_user" class="login-text J_UserName" maxlength="32" tabindex="1" type="text" placeholder="账号邮箱">
                </div>

                <div class="field pwd-field">
                    <label id="password-label" for="TPL_password_1"><img src="<%=path%>/resource/brush/lock.png"></label>
                    <span id="J_StandardPwd">
                        <input aria-labelledby="password-label" name="user_pass" id="user_pass" class="login-text" maxlength="16" tabindex="2" type="password" placeholder="密码">
                    </span>
                    <span id="J_PasswordEdit" class="password-edit" style="display:none;"></span>
                </div>
                
                <span id="error"><font color='red'>${error}</font></span>
	
                <div class="submit">
                    <button type="submit" class="J_Submit" tabindex="5" id="J_SubmitStatic">登　录</button>
                    <div style="text-align:right;margin-top:6px;"><!-- <a href="<%=path%>/home/registerUi">会员注册</a></div>-->
                </div>
                </div>
                </div>
            </td>
        </tr>

        <tr style="height:60px;">
            <td colspan="2">
                <div style="text-align:center;">版权所有© 2015-2017  粤ICP备17058969号-1</div>
            </td>
        </tr>
    </tbody></table>

</form>

</body></html>