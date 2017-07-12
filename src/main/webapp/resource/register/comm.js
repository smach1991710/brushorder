/**
* 使Disable的Select Enable，因传不过去值
*/
function EnableSelect()
{
	$('select[disabled]').removeAttr("disabled"); ;
}

function goBack()
{
	history.back();
}

/*
获取表格值
row 行号，col 列名 key； name中的第一部分
*/
function getCellValue(row,col,key)
{
	if(row=='')
	{
		return document.getElementsByName(key+'['+col+']')[0].value;
	}
	return document.getElementsByName(key+'['+row+']['+col+']')[0].value;
}

/*
设置单元格的值
*/
function setCellValue(row,col,key,v)
{
	if(row=='')
	{
		document.getElementsByName(key+'['+col+']')[0].value = v;
		return;
	}
	document.getElementsByName(key+'['+row+']['+col+']')[0].value = v;
}

/**
* 添加行
*/
function AddRow(e)
{
	var lrow = CopyRow(e);
	//初始化值
	$('#tr'+lrow+' input.edit_text_list').val('');
	$('#tr'+lrow+' input.edit_number_list').val('0');
	$('#tr'+lrow+' td.edit_text_list').html('');
	var add = orow.otable.attr['add'];
	if(add) SetRowValues(lrow,orow.key,add);
}

/**
* 获取表格的行信息和key值，返回Json
*/
function GetTableRowKey(e)
{
	var table = $(e).parents('table');
	var tableid ='#'+table.attr('id');
	var orow = $(e).parents('tr');
	var row = orow.attr('row');
	var key = table.attr('dataname');
	var lastrow = $(tableid+'>tbody>tr:last').attr('row');
	var del_id = table.attr('del_id'); 
	if (del_id==undefined) del_id= 'lines_del';

	return {"row": row,"key":key,"id":tableid,"otable":table,"orow":orow,"lastrow":lastrow,"del_id":del_id};
}

/**
* 复制行
*/
function CopyRow(e)
{
	var orow = GetTableRowKey(e);
	var rowhtml = orow.orow.html();
	var row = orow.row;

	lrow = orow.lastrow;
	lrow++;
	rowhtml = '<tr row="'+lrow+'" id="tr'+lrow+'">'+rowhtml.replace(new RegExp("\\["+row+"\\]", 'gm'),'['+lrow+']')+'</tr>';

	orow.otable.append(rowhtml);

	var copy = orow.otable.attr['copy'];
	if(copy) SetRowValues(lrow,orow.key,copy);

	return lrow;
}

/**
* 设置给定行的值
*/
function SetRowValues(row,key,jsonvalue)
{
	for(var k in jsonvalue)
	{
		setCellValue(row,k,key,jsonvalue[k]);
	}
}

/*
* 删除行，同时记录删除行的ID
*/
function DelRow(e,col)
{
	var orow = GetTableRowKey(e);
	var row = orow.row;

	var v = getCellValue(row,col,orow.key);

	$('#tr'+row).remove();
	if(v)
	{
		var odel = $('#'+orow.del_id);
		odel.val(odel.val()+','+v);
	}
	return true;
}

/*
全选或者清空Check
*/
function selectCheck(name,checked)
{
	var ids = document.getElementsByName(name);
	for(var i=0;i<ids.length;i++)
	{
		ids[i].checked = checked;
	}
}

/*
获取选中的Check的值，以逗号分隔返回
*/
function getCheckedValue(name)
{
	var retArray = new Array();
	var ids = document.getElementsByName(name);
	for(var i=0;i<ids.length;i++)
	{
		if(ids[i].checked) retArray.push(ids[i].value);
	}
	return retArray.join();
}

/*
排序
*/
function sort(orderby_col)
{
	var orderby_asc=$("#orderby_asc").val();
	$("#orderby_asc").val(orderby_asc=="asc"?"desc":"asc");
	$("#orderby_col").val(orderby_col);
	$("#mainform").submit();
}

/**
* 清空过滤条件
*/
function resetfilter(containerid)
{
	if(typeof(containerid)=='undefined')  containerid='filtertable';
	$('#'+containerid+" [name^='Filter[']").each(function() {
		$(this).attr("value", '');   
	});
}

/**
* 分页
*/
function goPage(pageaction)
{
	var page = parseInt($("#page_page").val());
	switch(pageaction)
	{
		case 'first_page':
			if(page==1) return false;
			page = 1;
			break;
		case 'prev_page':
			page = page-1;
			if(page<=0) return false;
			break;
		case 'next_page':
			page = page+1;
			break;
		case 'last_page':
			page = 0;	//最后设置为0
			break;
	}
	$("#page_page").val(page);
	$("#mainform").submit();
}

/*
获取指定控件的Url参数
参数 ids 为JSON对象数组,其格式为：
{
	id:控件Id,
	required:1/0 ,是否必须该条件，默认为0，如果没有的话，将提示错误
	msg:错误提示信息
}
返回以 & 分隔的 Filter[key]=value 值对，便于Get和Post传输
*/
function GetFilterValue(ids)
{
	var fa = Array();
	var v = '';
	var c ;
	for (var k in ids)
	{
		c = ids[k];
		if(c.id == undefined) continue;
		fv = $('#'+c.id);
		v = jQuery.trim(fv.val()); 
		if(v=='')
		{
			if(c.required)
			{
				alert(c.msg);
				return '';
			}
			continue;
		}
		fa.push(fv.attr('name')+'='+escape(v));
	}
//	alert(fa.join('&'));
	return fa.join('&');
}

/**
* 获取指定容器控件中的所有name=Filter的Url参数
* 参数 reqids 为JSON对象数组,其格式为：
{
	id:msg,...  id为控件id属性值, msg为提示消息
}
返回以 & 分隔的 Filter[key]=value 值对，便于Get和Post传输
*/
function GetFilterValueAll(reqids,containerid)
{
	if(typeof(containerid)=='undefined')  containerid='filtertable';	 //解决IE不支持缺省参数的问题
	var fa = Array();
	var v = '';
	var msg='';
	$('#'+containerid+" [name^='Filter\[']").each(function()
	{
		v = $(this).val();
		if(v=='')
		{
			if(reqids!=undefined) msg = reqids[$(this).attr('id')];
			if(msg) return false;
			return true;
		}
		fa.push($(this).attr('name')+'='+escape(v));
	});   
	if(msg) 
	{
		alert(msg);
		return '';
	}
	return fa.join('&');
}

/**
* 获取服务器的时间
*/
function getServerDate(fmt)
{
	if(fmt==undefined) fmt = 'Y-m-d';
	var dt ='' ;
	$.ajax({
			type: "post",
			url: "include/serverfunctions.php",
			data:'ac=GetCurrentTime&fmt='+fmt,
			async: false,
			success: function(data, textStatus){
			},
			complete: function(xmlhttp, textStatus){
					dt = xmlhttp.responseText;
			}
			});
	return dt;
}

/* --------------- 以上由 ranxl 添加函数 ----------*/

function isIE()
{
	is_opera = /opera/i.test(navigator.userAgent); 
	var is_ie = (/msie/i.test(navigator.userAgent) && !is_opera)
	return is_ie; 	 
}

/**
* 设置给定对象的X、Y坐标为父控件的左下角位置
* 如果对象的X\Y及宽度和高度超过浏览器的上下边界，采用居中方式
*/
function SetPosition(pobj,tobj)
{
	var wobj = $(window); 
	var top = pobj.offset().top  +pobj.height();
	var left = pobj.offset().left; 
	var wtop = wobj.scrollTop();
	if((top+tobj.height()-wtop)>wobj.height()) top =  wobj.height() - tobj.height()-10;
	if((left+tobj.width())>wobj.width()) left = wobj.width() - tobj.width() - 10;
	tobj.css('top',top);
	tobj.css('left',left);
}

/**
* 初始化下拉选择框
*/
var DropDown={
Init:function()
{
	$('input[lookkey][looktree!="1"]').each(
	function()
	{
		var lookkey=$(this).attr("lookkey");
		var id = "lookkey_"+lookkey;
		if($('#'+id).length==0)	//判断是否存在该类别的选择框
		{
			var lookvalues = LKJson[lookkey];
			var li='';
			var lic=0;
			for (var key in lookvalues) 
			{
				li+='<li lookvalue="'+key+'">'+lookvalues[key]+'</li>';
				lic+=1;
			}
			//创建Div弹出层
			var w=181;h=41;col=1;
			if(lic<=5) col=1;
			else if(lic<=10) col = 2;
			else col=3;
			var row = Math.ceil(lic/col);
			var style="width:"+((w*col)+(row>10?18:0))+'px;height:'+h*(row>10?10:row)+'px';
			$(document.body).append('<div id="'+id+'" class="dropdown" style="'+style+'"><ul>'+li+'</ul></div>'); 
		}
	});

	//设置单击下拉列表框
	$('input[lookkey][looktree!="1"]').click(
	function(ev)
	{
		var lookkey=$(this).attr("lookkey");
		var id = "#lookkey_"+lookkey;
		select_target = $(this);
		SetPosition(select_target,$(id));
		$(document.body).css('overflow','hidden');
		$('#bg_noinput,'+id).show();
	});

	$('.dropdown li').click(
	function(){
		select_target.val($(this).html());
		select_target.attr('lookvalue',$(this).attr('lookvalue'));
		$(this).parent().parent().hide();
		$('#bg_noinput').hide();
		$(document.body).css('overflow','auto');
	});
}	//end init
};

var DropDownTree={
	/**
	* 根据lookkey获取相应的层次数据
	* parentid 父级
	*/
	GetTreeData:function(lookkey,parentid)
	{
		var treedata={};
		switch(lookkey)
		{
			case 'd_region':	//获取地区层次数据,添加d.表示需要动态获取数据
			treedata = DataInterface.GetRegionByParent(parentid);
			break;
		}
		if(treedata['data']==undefined || treedata['data']['row']==undefined) return undefined;
		return treedata['data']['row'];
	},
	
	SetTargetValue:function(e)
	{
		var lh='';
		var id = 'lookkey_'+select_target.attr('lookkey');
		var lookhtml='';
		var lookvalue='0';
		if(e[0].tagName=='LI')
		{
			lookvalue = e.attr('lookvalue');
			lookhtml = e.html();
			e.parent().parent().hide();
		}
		else
		{
			lookvalue = $('#tv_'+id).attr('lookvalue');
			e.parent().hide();
		}
		$('#tv_'+id+' li').each(function()
		{
			if($(this).attr('lookvalue')>0)
				lh+=$(this).html();
		});
		select_target.val(lh+lookhtml);
		select_target.attr('lookvalue',lookvalue);
		$('#bg_noinput').hide();
		$(document.body).css('overflow','auto');
	},
	/**
	* 设置显示下一级数据
	*/
	ShowChildTree:function(lookkey,parentid)
	{
		var TDS = DropDownTree.GetTreeData(lookkey,parentid);
		if(TDS==undefined) return 0;
		var lic = TDS.length;
		if(lic==0) return 0;
		var li='';
		for (i=0;i<lic;i++) 
		{
			li+='<li lookvalue="'+TDS[i][LKCJson[lookkey]['key']]+'">'+TDS[i][LKCJson[lookkey]['value']]+'</li>';
		}
		var id = "lookkey_"+lookkey;
		$('#ul_'+id).html(li);

		if(parentid == 0)
		{
			//创建Div弹出层，统一采用3行的格式
			var w=181;h=41;col=3;
			var row = Math.ceil(lic/col)+1;
			var style={};
			style["width"]=((w*col)+(row>10?18:0))+'px';
			style["height"]=h*(row>10?10:row)+'px';
			style["overflow"]=row>10?"auto":"hidden";
			$('#'+id).css(style);
		}

		//设置li的click事件
		$('#ul_'+id+' li').click(
		function()
		{
			var t=$(this);
			var id = 'lookkey_'+select_target.attr('lookkey');
			var lookvalue = t.attr('lookvalue');
			var lookhtml = t.html();
			//设置下级数据
			if(DropDownTree.ShowChildTree(select_target.attr('lookkey'),lookvalue)>0)
			{
				$('#tv_'+id).html($('#tv_'+id).html()+'<li lookvalue="'+lookvalue+'">'+lookhtml+"</li>");
				$('#tv_'+id).attr('lookvalue',lookvalue);
				$('#tv_'+id + ' li').click(
				function()
				{
					$(this).nextAll().remove();
					DropDownTree.ShowChildTree(select_target.attr('lookkey'),$(this).attr('lookvalue'));
				});
			}
			else
			{
				DropDownTree.SetTargetValue(t);
			}
		});
		return TDS.length;
	},

/**
* 初始化层次结构下拉选择框
* 通过looktree与简单的下拉进行区别
*/
	Init:function()
	{
		$('[lookkey][looktree]').each(
		function()
		{
			var lookkey=$(this).attr("lookkey");
			var id = "lookkey_"+lookkey;
			if($('#'+id).length==0)	//判断是否存在该类别的选择框
			{
				$(document.body).append('<div id="'+id+'" class="dropdowntree"'+'><div class="select" id="tv_'+id+'"><li lookvalue="0">中国</li></div><input id="btn_dtr_ok" type=button value="确定"/><input id="btn_dtr_cancel" type=button value="取消"/><ul id="ul_'+id+'"></ul></div>'); 
				$('#btn_dtr_cancel').click(function(){
					$(this).parent().hide();
					$('#bg_noinput').hide();
					$(document.body).css('overflow','auto');
				});
				$('#btn_dtr_ok').click(function(){
					DropDownTree.SetTargetValue($(this));
				});
				DropDownTree.ShowChildTree(lookkey,0);	//初始化
			}
		});

		//设置单击下拉列表框
		$('[lookkey][looktree]').click(
		function(ev)
		{
			var lookkey=$(this).attr("lookkey");
			var id = "#lookkey_"+lookkey;
			select_target = $(this);
			SetPosition(select_target,$(id));
			$(document.body).css('overflow','hidden');
			$('#bg_noinput,'+id).show();
		});
	}
};

/**
* 数据校验
* 1、Required
* 2、function 回掉
*/
function validData()
{
	var b = true;
	$("[req]").each(function()
	{
		var t = $(this);
		if(t.css('display')!='none')
		{
			if(t.val()=='' || (t.attr('minv') && parseFloat(t.val())<parseFloat(t.attr('minv'))))
			{
				alert(t.attr('req'));
				t.focus();
				b = false;
				return false;
			}
		}
	});
	return b;
}

/**
* 设置所有控件不可编辑
*/
function AllReadOnly()
{
	$("input[type='text']").attr('readonly','yes');
	$('textarea').attr('readonly','yes');
	$('select').attr('disabled','disabled');
	$('#save').attr('disabled','disabled');
}

/**
* 获取Jquery对象
* e 为 this 或者 控件的 name 值
*/
function GetJObject(e)
{
	if(typeof(e)=='string')
	{
		return $("[name='"+e+"']");
	}
	else
	{
		return $(e);
	}
}

/**
* 复制指定文本的内容到剪贴板
*/
function copyToClipBoard(id)
{
	if (window.clipboardData)
	{
		window.clipboardData.clearData();
        window.clipboardData.setData("Text", $('#'+id).val());
	}
	else
	{	//调用浏览器的复制功能
		var e=document.getElementById(id);
		e.select(); // 选择对象
		document.execCommand("Copy");
	}
}

/// <summary>
/// 根据国家获取对应Amazon的URL
/// </summary>
/// <returns></returns>
function GetAmazonURL(country)
{
	switch (country)
	{
		case "US":
			return "https://www.amazon.com/";
		case "CA":
			return "https://www.amazon.ca/";
		case "UK":
			return "https://www.amazon.co.uk/";
		case "DE":
			return "https://www.amazon.de/";
		case "FR":
			return "https://www.amazon.fr/";
		case "IT":
			return "https://www.amazon.it/";
		case "JP":
			return "https://www.amazon.co.jp/";
		case "ES":
			return "https://www.amazon.es/";
		case "AU":
			return "https://www.amazon.com.au/";
		case "BR":
			return "https://www.amazon.com.br/";
		case "CN":
			return "https://www.amazon.cn/";
		case "IN":
			return "https://www.amazon.in/";
		case "MX":
			return "https://www.amazon.com.mx/";
		case "NL":
			return "https://www.amazon.nl/";
	}
	return "https://www.amazon.com/";
}
