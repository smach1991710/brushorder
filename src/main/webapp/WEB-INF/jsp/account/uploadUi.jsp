<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="../common.jsp"%>
<!DOCTYPE HTML>
<html lang="en">
<head>
  	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title></title>

  	<link rel="stylesheet" type="text/css" href="<%=path%>/resource/css/globle.css" />
  	<link rel="stylesheet" href="<%=path%>/resource/css/bootstrap.min.css" />

    <script id="jquery_172" type="text/javascript" class="library" src="<%=path%>/resource/js/jquery-1.7.2.min.js"></script>

    <style type="text/css">
    	html { 
			overflow-x: hidden; overflow-y: auto; 
		} 
    
    	body{
    		margin-bottom: 100px;
    	}

		table {border-collapse:collapse;}  
		td{  
			border-left:0;  
			border-top:0;  
			border-right:1px solid;  
			border-bottom:1px solid;  
			font-family:"Courier New", Courier, mono;  
			font-size:11px;  
			height:25px;  
			padding:0 12px 0 12px;  
			border-collapse:collapse;  
		}  
    </style>
    
    <script type="text/javascript">
		function upload(){
			var filename = $("input[name=files]").val();
			//检查下文件扩展名
			var flag = false; 
			var arr = ["xls"]; 
			var index = filename.lastIndexOf(".");  
			var ext = filename.substr(index+1);  
			for(var i=0;i<arr.length;i++)  {   
				if(ext == arr[i])   {   
					flag = true; 
					break;   
				} 
			}  
			if(!flag)  { 
				alert("文件名不合法,只支持xls格式的文件");  
				return;
			}
			$("#uploadaccount").submit();
		}
    
    	function reset(){
    		$("input[name=files]").val("No file selected...");
    	}
    </script>
</head>
<body style="background:#fff;overflow-y:auto">
  <!-- 头部{ -->
	<div class="phl">
		<div class="panel panel-default own-panel">
			<div class="panel-body">
				<form id="uploadaccount" action="<%=path%>/account/uploadAccounts" method="post" enctype="multipart/form-data" accept-charset="UTF-8">
					<div><label><a href="<%=path%>/upload/downtemplate">下载买家账号模板文件</a></label></div>
					<div class="uploader white">
						<input type="text" class="filename" readonly="readonly"/>
						<input type="button" name="file" class="button" value="Browse..."/>
						<input type="file" name="files" size="30" onchange="fileChange(this);"/>
					</div>
					<div>
						<input type="button" onclick="upload()" style="margin-left:60px;width:80px;height:30px;" value="上传"/>
						<input type="button" onclick="reset()" style="margin-left:30px;width:80px;height:30px;" value="重置"/>
					</div>
				</form>
		</div>
		
		<div id="returnback" style="padding-left:15px">
			<label>上传结果反馈</label>
			<table border="1" style="width:20%">
				<thead>
					<tr>
						<td style="text-align:center">行号</td>
						<td style="text-align:center">上传结果处理</td>
					</tr>
				</thead>
				<tbody>
					<tr>
						<td>成功总数</td>
						<td>${successline}</td>
					</tr>
				</tbody>
			</table>
		</div>
	</div>
</body>
</html>
<style>
.uploader{
position:relative;
display:inline-block;
overflow:hidden;
cursor:default;
padding:0;
margin:10px 0px;
-moz-box-shadow:0px 0px 5px #ddd;
-webkit-box-shadow:0px 0px 5px #ddd;
box-shadow:0px 0px 5px #ddd;

-moz-border-radius:5px;
-webkit-border-radius:5px;
border-radius:5px;
}

.filename{
float:left;
display:inline-block;
outline:0 none;
height:32px;
width:180px;
margin:0;
padding:8px 10px;
overflow:hidden;
cursor:default;
border:1px solid;
border-right:0;
font:9pt/100% Arial, Helvetica, sans-serif; color:#777;
text-shadow:1px 1px 0px #fff;
text-overflow:ellipsis;
white-space:nowrap;

-moz-border-radius:5px 0px 0px 5px;
-webkit-border-radius:5px 0px 0px 5px;
border-radius:5px 0px 0px 5px;

background:#f5f5f5;
background:-moz-linear-gradient(top, #fafafa 0%, #eee 100%);
background:-webkit-gradient(linear, left top, left bottom, color-stop(0%,#fafafa), color-stop(100%,#f5f5f5));
filter:progid:DXImageTransform.Microsoft.gradient(startColorstr='#fafafa', endColorstr='#f5f5f5',GradientType=0);
border-color:#ccc;

-moz-box-shadow:0px 0px 1px #fff inset;
-webkit-box-shadow:0px 0px 1px #fff inset;
box-shadow:0px 0px 1px #fff inset;

-moz-box-sizing:border-box;
-webkit-box-sizing:border-box;
box-sizing:border-box;
}

.button{
float:left;
height:32px;
display:inline-block;
outline:0 none;
padding:8px 12px;
margin:0;
cursor:pointer;
border:1px solid;
font:bold 9pt/100% Arial, Helvetica, sans-serif;

-moz-border-radius:0px 5px 5px 0px;
-webkit-border-radius:0px 5px 5px 0px;
border-radius:0px 5px 5px 0px;

-moz-box-shadow:0px 0px 1px #fff inset;
-webkit-box-shadow:0px 0px 1px #fff inset;
box-shadow:0px 0px 1px #fff inset;
}


.uploader input[type=file]{
position:absolute;
top:0; right:0; bottom:0;
border:0;
padding:0; margin:0;
height:30px;
cursor:pointer;
filter:alpha(opacity=0);
-moz-opacity:0;
-khtml-opacity: 0;
opacity:0;
}

input[type=button]::-moz-focus-inner{padding:0; border:0 none; -moz-box-sizing:content-box;}
input[type=button]::-webkit-focus-inner{padding:0; border:0 none; -webkit-box-sizing:content-box;}
input[type=text]::-moz-focus-inner{padding:0; border:0 none; -moz-box-sizing:content-box;}
input[type=text]::-webkit-focus-inner{padding:0; border:0 none; -webkit-box-sizing:content-box;}

/* White Color Scheme ------------------------ */

.white .button{
color:#555;
text-shadow:1px 1px 0px #fff;
background:#ddd;
background:-moz-linear-gradient(top, #eeeeee 0%, #dddddd 100%);
background:-webkit-gradient(linear, left top, left bottom, color-stop(0%,#eeeeee), color-stop(100%,#dddddd));
filter:progid:DXImageTransform.Microsoft.gradient(startColorstr='#eeeeee', endColorstr='#dddddd',GradientType=0);
border-color:#ccc;
}

.white:hover .button{
background:#eee;
background:-moz-linear-gradient(top, #dddddd 0%, #eeeeee 100%);
background:-webkit-gradient(linear, left top, left bottom, color-stop(0%,#dddddd), color-stop(100%,#eeeeee));
filter:progid:DXImageTransform.Microsoft.gradient(startColorstr='#dddddd', endColorstr='#eeeeee',GradientType=0);
}

</style>
	    		<script>$(function(){
	$("input[type=file]").change(function(){$(this).parents(".uploader").find(".filename").val($(this).val());});
	$("input[type=file]").each(function(){
	if($(this).val()==""){$(this).parents(".uploader").find(".filename").val("No file selected...");}
	});
});
</script>
