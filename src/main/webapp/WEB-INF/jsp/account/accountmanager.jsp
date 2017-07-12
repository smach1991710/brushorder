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
    <script language="javascript" type="text/javascript" src="<%=path%>/resource/My97DatePicker/WdatePicker.js"></script>
  
    <style type="text/css">
    	html { 
			overflow-x: hidden; overflow-y: auto; 
		} 
    
    	body{
    		margin-bottom: 100px;
    	}
    	
    	td{
			white-space:nowrap;overflow:hidden;text-overflow: ellipsis;
		}
		
		th{
			white-space:nowrap;overflow:hidden;text-overflow: ellipsis;
		}
    </style>
   
    <script type="text/javascript">
   		//表格数据     
 		var json = [];
    	
   		//请求参数保存
 		var countrycode="";
    	var stauts="";
    	var email="";
    	var sysid=0;
    	var type="";
    	var ip = "";
    	var buyerId = "";
    	var startbuydate = "";
    	var endbuydate = "";
    	var startcreatedate = "";
    	var endcreatedate = "";
    	
    	$(function(){
    		//查询第几页
    		var pageNo = 1;
    		//一页显示多少条数据
    		var pageSize = 20;
    		//设置请求的url
    		var url = "<%=path%>/account/accountList";
    		freshPage(url,pageNo,pageSize);
    	});
    	
    	/**
    	 * 刷新页面数据
    	 * @param pageSize   每页显示条数
    	 * @param pageNum    第几页
    	 */
    	function refreshData(pageSize, pageNo) {
    		//设置请求的url
    		var url = "<%=path%>/account/accountList";
    		freshPage(url,pageNo,pageSize)
    	}
    	
    	/**
    	 * 刷新页面数据
    	 */
    	var freshPage = function (url,pageNo,pageSize) {
    		var querydata = {
    				countrycode:countrycode,
    				status:status,
    				email:email,
    				sysid:sysid,
    				type:type,
    				ip:ip,
    				buyerId:buyerId,
    				startbuydate:startbuydate,
    				endbuydate:endbuydate,
    				startcreatedate:startcreatedate,
    				endcreatedate:endcreatedate,
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
            		'<th>ID</th>' + 
            		'<th>账号/亚马逊BuyerID</th>' +
            		'<th>密码</th>' + 
            		'<th>国家</th>' + 
            		'<th>状态</th>' +
            		'<th>信用卡信息</th>' +
            		'<th>电话邮编</th>' +
            		'<th>账号余额</th>' +
            		'<th>VPN(IP-User Pwd)</th>' +
            		'<th>最后购买日期</th>' +
            		'<th>创建日期</th>' +
            		'</tr>';
            UQT_detailTable.append(th);
            var tr;
            $.each(UQTQueryMsg, function(i,eachData){
                tr = $('<tr>');
                var id = eachData.id;
                var email = eachData.email;
                var pwd = eachData.pwd;
                var countrycode = eachData.countrycode;
                var status = eachData.status;
                var creditcard = eachData.creditcard;
                var postcode = eachData.postcode;
                var accountmoney = eachData.accountmoney;
                var vpn = eachData.vpn;
                var buydate = eachData.buydate;
                var createdate = eachData.createdate;
                
                if(creditcard == undefined){
                	creditcard = "";
                }
                if(vpn == undefined){
                	vpn = "";
                }
                tr.append('<td>' + id + '</td>' + 
                 		'<td>' + email + '</td>' +
                 		'<td>' + pwd+'</td>' + 
                 		'<td>' + countrycode+'</td>' +
                 		'<td>' + status +'</td>' +
                 		'<td>' + creditcard +'</td>' +
                 		'<td>' + postcode +'</td>' +
                 		'<td>' + accountmoney +'</td>' +
                 		'<td>' + vpn +'</td>' +
                 		'<td>' + buydate +'</td>' +
                 		'<td>' + createdate +'</td>');
                UQT_detailTable.append(tr);
            });
            if(json.length == 0){
            	tr = $('<tr>');
                tr.append('<td class="chi_name" style="text-align: center;" colspan="11">未查询到数据</td>');
                UQT_detailTable.append(tr);
            }
        }
        
        function query(){
        	//获取用户输入的参数信息
        	countrycode = $("#countrylist").val();
        	status = $("#statuslist").val();
        	email = $("#email").val();
        	sysid = $("#sysid").val();
        	type = $("#typelist").val();
        	ip = $("#ip").val();
        	buyerId = $("#buyerID").val();
        	startbuydate = $("#startbuydate").val();
        	endbuydate = $("#endbuydate").val();
        	startcreatedate = $("#startcreatedate").val();
        	endcreatedate = $("#endcreatedate").val(); 
        	
        	//查询第几页
    		var pageNo = 1;
    		//一页显示多少条数据
    		var pageSize = 20;
    		//设置请求的url
    		var url = "<%=path%>/account/accountList";
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
        
        //跳转到上传的页面
        function upload(){
        	window.open("<%=path%>/account/uploadUi","_blank");
        }
        
        //导出买家账号
        function exportaccount(){
        	window.location.href="<%=path%>/account/downaccountlist";
        }
    </script>
</head>
<body style="background:#fff;overflow-y:auto">

	<div style="padding-top:20px;padding-left:15px;padding-right:15px">
		<table style="width:100%;border-collapse:separate; border-spacing:0px 10px;">
			<tr>
				<td>
					<label>国家/状态：</label>
				</td>
				<td>
					<select id="countrylist" name="countrylist" style="width:80px;height:28px">
						<option value=""></option>
						<c:forEach var="type" items="${countryenum}">
    						<option value="${type.getContentLanguage()}" >${type.getChineseName()}</option>
						</c:forEach>
					</select>
					<select id="statuslist" name="statuslist" style="width:125px;height:28px">
						<option value=""></option>
						<c:forEach var="type" items="${statusenum}">
    						<option value="${type.getValue()}" >${type.getCode()}</option>
						</c:forEach>
					</select>
				</td>
				<td>
					<label style="padding-left:15px">Email/ID：</label>
				</td>
				<td>
					<input type="text" id="email" name="email" style="width:120px;height:28px"/>&nbsp;
					<input type="text" id="sysid" name="sysid" placeholder="系统ID" style="width:120px;height:28px"/>
				</td>
				<td>
					<label style="padding-left:15px">类型/IP：</label>
				</td>
				<td colspan="3">
				
					<select id="typelist" name="typelist" style="width:120px;height:28px">
						<option value=""></option>
						<c:forEach var="type" items="${typeenum}">
    						<option value="${type.getValue()}" >${type.getCode()}</option>
						</c:forEach>
					</select>&nbsp;
					<input type="text" id="ip" name="ip" style="width:120px;height:28px"/>
				</td>
			</tr>
			<tr>
				<td>
					<label>BuyerID：</label>
				</td>
				<td>
					<input type="text" id="buyerID" name="buyerID" placeholder="亚马逊BuyerID" style="width:210px;height:28px"/>
				</td>
				<td>
					<label style="padding-left:15px">购买日期：&nbsp;</label>
				</td>
				<td>
					<input class="Wdate" type="text" onClick="WdatePicker()" id="startbuydate" name="startbuydate" style="width:120px;height:28px"/>-
					<input class="Wdate" type="text" onClick="WdatePicker()" id="endbuydate" name="endbuydate" style="width:120px;height:28px"/>
				</td>
				<td>
					<label style="padding-left:15px">创建日期：</label>
				</td>
				<td>
					<input class="Wdate" type="text" onClick="WdatePicker()" id="startcreatedate" name="startcreatedate" style="width:120px;height:28px"/>-
					<input class="Wdate" type="text" onClick="WdatePicker()" id="endcreatedate" name="endbuydate" style="width:120px;height:28px"/>
				</td>
				<td>
					<button class="button button-primary" style="background:#3C8DBC;margin-left:15px" onclick="query()">查询</button>	
					<button class="button button-primary" style="background:#3C8DBC" onclick="reset()">重置</button>
				</td>
				<td style="width:20%;text-align:center">
					<button class="button button-primary" style="background:#3C8DBC;margin-left:15px" onclick="upload()">上传</button>	
					<button class="button button-primary" style="background:#3C8DBC" onclick="exportaccount()">导出买家账号</button>
				</td>
			</tr>
		</table>
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
