<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="../common.jsp"%>
<!DOCTYPE HTML>
<html lang="en">
<head>
  <meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>卓游自助查询系统</title>

  	<link rel="stylesheet" type="text/css" href="<%=path%>/resource/css/globle.css" />
  	<link rel="stylesheet" href="<%=path%>/resource/css/bootstrap.min.css" />

	<script type="text/javascript" src="<%=path%>/resource/table/js/jquery-1.8.3.min.js"></script>
    <link href="<%=path%>/resource/table/css/main.css" type="text/css" rel="Stylesheet"/>
    <link href="<%=path%>/resource/table/css/query_startic_pro.css" type="text/css" rel="Stylesheet"/>
    <script type="text/javascript" src="<%=path%>/resource/table/js/main.js"></script>
    
    <link href="<%=path%>/resource/css/dpl-min.css" rel="stylesheet" type="text/css" />
    <link href="<%=path%>/resource/css/bui-min.css" rel="stylesheet" type="text/css" />
  
    <style type="text/css">
    	html { 
			overflow-x: hidden; overflow-y: auto; 
		} 
    
    	body{
    		margin-bottom: 100px;
    	}
    </style>
   
    <script type="text/javascript">
   		//表格数据     
 		var json = [];
    	
   		//请求参数保存
 		var roleid=0;
    	var name="";
    	var email="";
    	var qq="";
   		
    	$(function(){
    		//查询第几页
    		var pageNo = 1;
    		//一页显示多少条数据
    		var pageSize = 20;
    		//设置请求的url
    		var url = "<%=path%>/user/userList";
    		freshPage(url,pageNo,pageSize);
    	});
    	
    	/**
    	 * 刷新页面数据
    	 * @param pageSize   每页显示条数
    	 * @param pageNum    第几页
    	 */
    	function refreshData(pageSize, pageNo) {
    		//设置请求的url
    		var url = "<%=path%>/user/userList";
    		freshPage(url,pageNo,pageSize)
    	}
    	
    	/**
    	 * 刷新页面数据
    	 */
    	var freshPage = function (url,pageNo,pageSize) {
    		var querydata = {
    				roleid:roleid,
    				name:name,
    				email:email,
    				qq:qq,
    				page:pageNo,
    				size:pageSize
    		};
    		$.ajax({
    	        type: "GET",
    	        url: url,
    	        contentType: "application/json; charset=utf-8",
    	        dataType: "json",
    	        data:querydata,
    	        success: function(msg) {
    	        	json = eval(msg.rows)
    	        	builderUQTQueryMsg(json);
    	        	
    	        	var totalRecords = msg.total
    	        	var totalPage = getPageCount(pageSize,totalRecords);
    	        	
    	    		//生成分页控件 根据分页的形式在这里设置
    	    		kkpager.init({
    	    			pno: pageNo,
    	    			//总页码
    	    			total: totalPage,
    	    			//总数据条数
    	    			totalRecords: totalRecords,
    	    			//页面条数
    	    			pageSize: pageSize
    	    		});
    	    		kkpager.generPageHtml();
    	        },
    	        error: function (msg) {
    	        	var data = msg.responseText; 
        	    	redirect(data);
    	        }
    		});
    	}
    	
    	var getPageCount = function (pageSize,totalRecords) {
    	    var shang = totalRecords/pageSize;
    	    var yushu = totalRecords%pageSize;
    	    if(yushu >0){
    	        shang ++;
    	    }
    	    return shang;
    	}
    	
        /**
         * 构建表格数据
         */
        var builderUQTQueryMsg = function (UQTQueryMsg){
            var UQT_detailTable = $('#UQT_detailTable');
            UQT_detailTable.empty();
            var th = '<tr>' +
            		'<th style="width:3%"><input id="chooseAll" onclick="chooseAll()" name="chooseAll" type="checkbox"/></th>'+
            		'<th>ID</th>' + 
            		'<th>姓名</th>' +
            		'<th>Email</th>' + 
            		'<th>QQ</th>' + 
            		'<th>角色</th>' +
            		'<th>账户余额</th></tr>';
            UQT_detailTable.append(th);
            var tr;
            $.each(UQTQueryMsg, function(i,eachData){
                tr = $('<tr>');
                var id = eachData.id;
                var name = eachData.name;
                var email = eachData.email;
                var qq = eachData.qq;
                var roleid = eachData.roleid;
                var accoutmoney = eachData.accoutmoney;
                
                tr.append('<td style="width:3%"><input id="' + id + '" name="chooseTd" type="checkbox"/></td>' + 
                		'<td>' + id + '</td>' + 
                 		'<td>' + name + '</td>' +
                 		'<td>' + email+'</td>' + 
                 		'<td>' + qq+'</td>' +
                 		'<td>' + roleid +'</td>' +
                 		'<td style="width:15%;">' + accoutmoney + '</td>');
                UQT_detailTable.append(tr);
            });
            if(json.length == 0){
            	tr = $('<tr>');
                tr.append('<td class="chi_name" style="text-align: center;" colspan="7">未查询到数据</td>');
                UQT_detailTable.append(tr);
            }
        }
        
        function query(){
        	//获取用户输入的参数信息
        	roleid = $("#rolelist").val();
        	name = $("#name").val();
        	email = $("#email").val();
        	qq = $("#qq").val();
        	
        	//查询第几页
    		var pageNo = 1;
    		//一页显示多少条数据
    		var pageSize = 20;
    		//设置请求的url
    		var url = "<%=path%>/user/userList";
    		freshPage(url,pageNo,pageSize);
        }
        
        function reset(){
        	//设置下拉框的内容为空
        	$("#rolelist").val("");
        	//设置input输入框的内容为空
        	$("input").val(""); 
        }
        
        function chooseAll(){
        	//判断是选中,还是取消选中
        	if(!$("[name = chooseAll]:checkbox").attr("checked")){
        		$("[name = chooseTd]:checkbox").attr("checked", false);
        	}else{
        		$("[name = chooseTd]:checkbox").attr("checked", true);
        	}
        }
        
        
    </script>
</head>
<body style="background:#fff;overflow-y:auto">

	<div style="padding-top:20px;padding-left:15px">
		<label>角色：</label>
		<select id="rolelist" name="rolelist" style="width:160px;height:28px">
			<option value=""></option>
			<option value="1">卖家</option>
			<option value="2">系统管理员</option>
		</select>
		
		<label style="padding-left:15px">姓名：</label>
		<input type="text" id="name" name="name" style="width:160px;height:28px"/>
		
		<label style="padding-left:15px">Email：</label>
		<input type="text" id="email" name="email" style="width:160px;height:28px"/>
		
		<label style="padding-left:15px">QQ：</label>
		<input type="text" id="qq" name="qq" style="width:160px;height:28px"/>
		
		<button class="button button-primary" style="background:#3C8DBC;margin-left:15px" onclick="query()">查询</button>	
		<button class="button button-primary" style="background:#3C8DBC" onclick="reset()">重置</button>
	</div>


  <!-- 头部{ -->
	<div class="phl">
		<div class="panel panel-default own-panel">
			<div class="panel-body">
				<div id="browser" class="tbtree" style="text-align: center;">
					<div  id="mainTbtable" class='uqt_detail'>
						<table id="UQT_detailTable" class="" style="width:100%" border="0" cellpadding="0" cellspacing="0">
						</table>
					</div>
					<div id="div_pager" class="page_con" >
					</div>
			 	</div>
			</div>
		</div>
	</div>
</body>
</html>
