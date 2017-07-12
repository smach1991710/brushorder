<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@page import="java.util.*"%>  

<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<script type="text/javascript">
	function redirect(data){
    	if(data == "loseSession"){
            window.location.href="<%=path%>/index.jsp"
            return;
        }
    	alert("请求失败了,刷新下页面");
	}
</script>

<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<c:set var="path" value="<%=path%>"/>