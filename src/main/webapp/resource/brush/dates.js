/**********************   日期选择  ***************************
 *	@Author	: ranxl
 *	@Company: Acepurchase
 *	@EMail: xiaolin.ran@acepurchase.com
 '**************************************************************************/
var MonthDays = [0,31,28,31,30,31,30,31,31,30,31,30,31];
var MonthText = ["","1","2","3","4","5","6","7","8","9","10","11","12"];
var WeekName = ["","日","一","二","三", "四", "五", "六"];
var TitleClass=['week','Holi','','','','','','Holi','Sday'];
var dtp_from,dtp_to;
var SelectedDay = 1;

function Calendar_YMChange()
{
	var y = document.getElementById('Calendar_Y').value;
	var m = document.getElementById('Calendar_M').value;
	Calendar_Show(y,m,SelectedDay);
}

function Calendar_StopHide(e)
{
	var elm=e.srcElement || e.target;
	if ( e.stopPropagation )
		e.stopPropagation();
	else
		e.cancelBubble = true;
}
function formatDate(dt,fmt)
{
	var m = dt.getMonth()+1; d = dt.getDate();
	return dt.getFullYear()+'-'+(m<10?'0':'')+m+'-'+(d<10?'0':'')+d;
}
function Calendar_Select(e)
{
//	也可以通过如下语句获得事件的参数
//	var e=window.event || arguments.callee.caller.arguments[0];
	var elm=e.srcElement || e.target;

	if(elm.id=='Calendar_ChangeDay')
	{
		Calendar_StopHide(e);
		return true;
	}
	var y = document.getElementById('Calendar_Y').value;
	var m = document.getElementById('Calendar_M').value;
	if(elm.className==TitleClass[0])
	{	//week
		var d2=elm.attributes["weekday"].value;//记录周的最后一个值
		var dt2 = new Date(y,m-1,d2);
		var w = dt2.getDay();
		if(w<6)
		{
			dt2 = addDays(dt2,6-w);
		}
		var dt1 = addDays(dt2,-6);
		dtp_from.value = formatDate(dt1);
		if(dtp_to!=null) dtp_to.value = formatDate(dt2);
		return true;
	}
	var elmv = elm.innerHTML;
	if(elmv=='&lt;')
	{
		m-=1;
		if(m<1)
		{
			m=12;y-=1;
		}
		Calendar_Show(y,m,SelectedDay);
		return true;
	}
	if(elmv=='&gt;')
	{
		m++;
		if(m>12)
		{
			m=1;y++;
		}
		Calendar_Show(y,m,SelectedDay);
		return true;
	}
	var d = 1;
	d = parseInt(elmv);

	if(isNaN(d))
	{
		//判断是否切换相对日期
		var elmv = elm.value;
		if(elmv[0]!='T')
		{
			Calendar_StopHide(e);
			return false;
		}
		elmv = elmv.substr(1);
		if(elmv=='M')
		{
			dtp_from.value = formatDate(new Date(y,m-1,1));
			d = getDayofMonth(y,m);
			dtp_to.value = formatDate(new Date(y,m-1,d));
			return true;
		}
		var dt1 = new Date();
		var dd = parseInt(elmv);
		if(dd>=0)
		{
			dtp_from.value = formatDate(dt1);
			dtp_to.value = formatDate(addDays(dt1,dd));
		}
		else
		{
			dtp_from.value = formatDate(addDays(dt1,dd));
			dtp_to.value = formatDate(dt1);
		}
		return false;
	}
	dtp_from.value = formatDate(new Date(y,m-1,d));
	Calendar_hide();
}

function Calendar_Show(y,m,d)
{
	var Calendar=document.getElementById('divCalendar');
	if(Calendar==null)
	{
		var Calendar=document.createElement("div");
		Calendar.id = 'divCalendar';
		Calendar.className= "DatePicker";
		//注意：通过这种方式可以设置传递事件参数给函数，而通过直接在html属性中通过onclick="function"不行
		Calendar.onclick=Calendar_Select;
		document.body.appendChild(Calendar);
		addMyEvent(document,'click',Calendar_hide);
	}

	var md=getDayofMonth(y,m);
	if(d>md) d = md;
//判断是否日期
	if(m<1 || m>12 || d<1)
	{
		var date=new Date();
		y = date.getFullYear();
		m = date.getMonth()+1;
		d = date.getDate();
		md=getDayofMonth(y,m);
	}
	SelectedDay = d;
	var yopt='';
	for(var i=0;i<=8;i++)
	{
		yi = (y-4+i);
		yopt += '<option value="'+yi+'" '+(yi==y?'selected':'')+'>'+yi+'</option>';
	}
	var html = '<div style="height:22px;padding-top:2px"><a href="#" style="display:inline;width:30px;margin-right:5px" title="Last month">&lt;</a><select id="Calendar_Y" onchange="Calendar_YMChange();">'+yopt+'</select>'+
		'<select id="Calendar_M" onchange="Calendar_YMChange();"><option value="1">1</option><option value="2">2</option><option value="3">3</option><option value="4">4</option><option value="5">5</option><option value="6">6</option><option value="7">7</option><option value="8">8</option><option value="9">9</option><option value="10">10</option><option value="11">11</option><option value="12">12</option></select><a href="#" style="display:inline;width:30px;margin:0px 5px" title="Next month">&gt;</a>'+
		(dtp_to==null?'':'<select id="Calendar_ChangeDay"><option value="T0">当天</option><option value="TM">当月</option><option value="T-7">前7天</option><option value="T-14">前14天</option><option value="T-21">前21天</option><option value="T-30">前30天</option><option value="T-60">前60天</option><option value="T-90">前90天</option><option value="T-180">前180天</option><option value="T-365">前365天</option></select>')+
		'</div><table><thead><tr style="height:22px">';
	for(var k=0;k<8;k++)
	{
		html += '<th' + (TitleClass[k]==''?'':' class="'+TitleClass[k]+'"') + '>' + WeekName[k] + '</th>';
	}
	html += "</tr></thead><tbody>";

	var dt = new Date(y,m-1,1);
	var w = dt.getDay();
	var tb='';
	if(w>0) tb = '<td colspan='+w+'>&nbsp;</td>';
	var di=0;
	var week = getWeekofDate(dt);
	for(var r=1;r<7;r++)
	{
		for(w;w<=6;w++)
		{
			di++;
			if(di>md) break;
			tb += '<td><a href="#"'+  (TitleClass[w+1]!='' || (di==d)?' class="'+TitleClass[((di==d)?8:w+1)]+'"':'')  + '>' +di+'</a></td>';
		}
		if(tb!='') html += '<tr><td><a href="#" class="'+TitleClass[0]+'" weekday="'+(di<=md?di:md)+'">'+week+'</a></td>'+tb+'</tr>';
		if(di>md) break;
		tb='';w=0;week++;
	}
	html += "</tbody></table>";
	Calendar.innerHTML= html;html='';
	document.getElementById('Calendar_M').value=m;

	var top=dtp_from.offsetHeight, left=0;
	var p=dtp_from;
	while(p && p.tagName!="BODY")
	{
		top+=p.offsetTop;
		left+=p.offsetLeft;
		p=p.offsetParent;
	}
	var height = (r+2-(w?0:1))*24+5,width=238;
	Calendar.style.height = height+'px';
	if(top+height>document.body.scrollHeight) top = document.body.scrollHeight - height;
	if(left+width>document.body.scrollWidth) left = document.body.scrollWidth-width;
	Calendar.style.top=top+'px';
	Calendar.style.left=left+'px';
	setTimeout("document.getElementById('divCalendar').style.display='';",1);
}
/*是否润年*/
function getDayofMonth(y,m)
{
	if(m!=2) return MonthDays[m];
	if((y%4==0 && y%100!=0) || (y%400==0)) return MonthDays[m]+1;
	return MonthDays[m];
}
/*返回两个日期间的天数 d2-d1*/
function getDays(d1,d2)
{
	return (d2.getTime()-d1.getTime())/86400000;
}
/*返回给定日期的周数*/
function getWeekofDate(d)
{
	var d1=new Date(d.getFullYear(),0,1);
	var days = getDays(d1,d);
	var day = d1.getDay();
	return Math.ceil((days+day-6) / 7)+1;
}
/*加上日期天数*/
function addDays(d,days)
{
	var dt = new Date();
	dt.setTime(d.getTime()+days*86400000);
	return dt;
}
function Calendar(id_from,id_to)
{
	dtp_from=document.getElementById(id_from);
	dtp_to=document.getElementById(id_to);
	var regex=/\d{4}-\d{1,2}-\d{1,2}/;
	if((dtp_from.value)=='' || regex.exec(dtp_from.value)==null)
	{
		var ymd=new Date();
		Calendar_Show(ymd.getFullYear(),ymd.getMonth()+1,ymd.getDate());
	}
	else
	{
		var ymd=dtp_from.value.split("-");
		Calendar_Show(parseInt(ymd[0],10),parseInt(ymd[1],10),parseInt(ymd[2],10));
	}
}
function addMyEvent(obj,evt,fn)
{
	if(obj.addEventListener){
		obj.addEventListener(evt,fn,false);
	}
	else if(obj.attachEvent){
		obj.attachEvent('on'+evt,fn);
	}}
function Calendar_hide()
{
	document.getElementById("divCalendar").style.display="none";
}