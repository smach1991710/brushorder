/**
 * Created by huipu on 2016/1/28.
 *	分页插件
 */
var kkpager = {
    //divID
    pagerid: 'div_pager',
    //当前页码
    pno: 1,
    //总页码
    total: 1,
    //总数据条数
    totalRecords: 0,
    //是否显示总页数
    isShowTotalPage: true,
    //是否显示总记录数
    isShowTotalRecords: true,
    //是否显示页码跳转输入框
    isGoPage: true,
    //页面条数默认大小
    pagesize : 20,
    //链接前部
    hrefFormer: '',
    //链接尾部
    hrefLatter: '',
    /****链接算法****/
    getLink: function (n) {
        //这里的算法适用于比如：
        //hrefFormer=http://baidu.com/news/20131212
        //hrefLatter=.html
        //那么首页（第1页）就是http://baidu.com/news/20131212.html
        //第2页就是http://baidu.com/news/20131212_2.html
        //第n页就是http://baidu.com/news/20131212_n.html
        if (n == 1) {
            return this.hrefFormer + this.hrefLatter;
        } else {
            return this.hrefFormer + '_' + n + this.hrefLatter;
        }
    },
    //跳转框得到输入焦点时
    focus_gopage: function () {
        var btnGo = $('#btn_go');
        $('#btn_go_input').attr('hideFocus', true);
        btnGo.show();
        btnGo.css('left', '0px');
        $('#go_page_wrap').css('border-color', '#6694E3');
        btnGo.animate({ left: '+=44' }, 50, function () {
            //$('#go_page_wrap').css('width','88px');
        });
    },

    //跳转框失去输入焦点时

    blur_gopage: function () {
        setTimeout(function () {
            var btnGo = $('#btn_go');
            //$('#go_page_wrap').css('width','44px');
            btnGo.animate({
                left: '-=44'
            }, 100, function () {
                $('#btn_go').css('left', '0px');
                $('#btn_go').hide();
                $('#go_page_wrap').css('border-color', '#DFDFDF');
            });
        }, 400);
    },
    //根据页数刷新页面数据
    gopage: function (data) {
        var currentPage = '';
        if(data.tagName == 'A') {
            //点击的是a标签，这里写你的代码
            var tempPage = $(data).html();
            if(tempPage == '下一页'){
                currentPage = parseInt(this.pno)+1;
            }else if(tempPage == '上一页'){
                currentPage = parseInt(this.pno)-1;
            }else{
                currentPage = tempPage;
            }
        }else{
            //手动输入要跳转的页数
            var str_page = $("#btn_go_input").val();
            if (isNaN(str_page)) {
                $("#btn_go_input").val(this.next);
                return;
            }
            var n = parseInt(str_page);
            if (n < 1 || n > this.total) {
                $("#btn_go_input").val(this.next);
                return;
            }
            currentPage = n;
        }
        refreshData(this.pagesize,currentPage);
        //不管是点击页码还是手动输入页码，都要将下一页的页码选中
        //此时，只需要重新加载本组件即可
    },
    changepageSize:function(data){
        //更改每页显示条数
        //刷新数据时初始化页数为1，
        var pageSize = $(data).val();
        //alert('要初始化的页面条数为:'+pageSize);
        refreshData(pageSize,1);
    },
    //分页按钮控件初始化
    init: function (config) {

        //页面条数初始化
        this.pagesize = config.pageSize;
        //赋值
        this.pno = isNaN(config.pno) ? 1 : parseInt(config.pno);
        this.total = isNaN(config.total) ? 1 : parseInt(config.total);
        this.totalRecords = isNaN(config.totalRecords) ? 0 : parseInt(config.totalRecords);
        if (config.pagerid) { this.pagerid = pagerid; }
        if (config.isShowTotalPage != undefined) { this.isShowTotalPage = config.isShowTotalPage; }
        if (config.isShowTotalRecords != undefined) { this.isShowTotalRecords = config.isShowTotalRecords; }
        if (config.isGoPage != undefined) { this.isGoPage = config.isGoPage; }
        this.hrefFormer = config.hrefFormer || '';
        this.hrefLatter = config.hrefLatter || '';
        if (config.getLink && typeof (config.getLink) == 'function') { this.getLink = config.getLink; }
        //验证
        if (this.pno < 1) this.pno = 1;
        this.total = (this.total <= 1) ? 1 : this.total;
        if (this.pno > this.total) this.pno = this.total;
        this.prv = (this.pno <= 2) ? 1 : (this.pno - 1);
        this.next = (this.pno >= this.total - 1) ? this.total : (this.pno + 1);
        this.hasPrv = (this.pno > 1);
        this.hasNext = (this.pno < this.total);

        this.inited = true;
    },
    //生成分页控件Html
    generPageHtml: function () {
        if (!this.inited) {
            return;
        }

        var str_prv = '', str_next = '';
        if (this.hasPrv) {
            str_prv = '<a href="javascript:void(0);"  onclick="kkpager.gopage(this);" title="上一页">上一页</a>';
            //str_prv = '<a href="javascript:void(0);""javascript:void(0);""' + this.getLink(this.prv) + '" title="上一页">上一页</a>';
        } else {
            str_prv = '<span class="disabled">上一页</span>';
        }

        if (this.hasNext) {
            str_next = '<a href="javascript:void(0);" onclick="kkpager.gopage(this);"  title="下一页">下一页</a>';
            //str_next = '<a href="javascript:void(0);""javascript:void(0);""' + this.getLink(this.next) + '" title="下一页">下一页</a>';
        } else {
            str_next = '<span class="disabled">下一页</span>';
        }


        var str = '';
        var dot = '<span>...</span>';
        var total_info = '';
        if (this.isShowTotalPage || this.isShowTotalRecords) {
            total_info = '<span class="normalsize">共';
            if (this.isShowTotalPage) {
                total_info += this.total + '页';
                if (this.isShowTotalRecords) {
                    total_info += '&nbsp;/&nbsp;';
                }
            }
            if (this.isShowTotalRecords) {
                total_info += this.totalRecords + '条数据';
            }

            total_info += '</span>';
        }

        var pageSize = '<select id="select_pagesize" onchange="kkpager.changepageSize(this)">';

        if(this.pagesize == 10)
            pageSize += '<option selected="selected" value="10" >10</option>';
        else
            pageSize += '<option  value="10" >10</option>';
        if(this.pagesize == 20)
             pageSize += '<option selected="selected" value="20" >20</option>';
        else
            pageSize += '<option  value="20" >20</option>';
        if(this.pagesize == 25)
            pageSize += '<option selected="selected" value="25" >25</option>';
        else
            pageSize += '<option  value="25" >25</option>';
        if(this.pagesize == 50)
            pageSize += '<option selected="selected" value="50" >50</option>';
        else
            pageSize += '<option  value="50" >50</option>';
        if(this.pagesize == 75)
            pageSize += '<option selected="selected" value="75" >75</option>';
        else
            pageSize += '<option  value="75" >75</option>';
        if(this.pagesize == 100)
            pageSize += '<option selected="selected" value="100" >100</option>';
        else
            pageSize += '<option  value="100" >100</option>';
        pageSize +='</select><span class="normalsize" >条/页</span>';

        var gopage_info = '';
        if (this.isGoPage) {
            gopage_info = '&nbsp;<span class="normalsize" >转到</span><span id="go_page_wrap" style="display:inline-block;width:44px;height:18px;border:1px solid #DFDFDF;margin:0px 1px;padding:0px;position:relative;left:0px;top:5px;">' +
                '<input type="button" id="btn_go" onclick="kkpager.gopage(this);" style="width:44px;height:20px;line-height:20px;padding:0px;font-family:arial,宋体,sans-serif;text-align:center;border:0px;background-color:#0063DC;color:#FFF;position:absolute;left:0px;top:-1px;display:none;" value="确定" />' +
                '<input type="text" id="btn_go_input" onfocus="kkpager.focus_gopage()" onkeypress="if(event.keyCode<48 || event.keyCode>57)return false;" onblur="kkpager.blur_gopage()" style="width:42px;height:16px;text-align:center;border:0px;position:absolute;left:0px;top:0px;outline:none;" value="' + this.pno + '" /></span><span class="normalsize" >页</span>';
        }

        //分页处理
        if (this.total <= 8) {
            for (var i = 1; i <= this.total; i++) {
                if (this.pno == i) {
                    str += '<span class="curr">' + i + '</span>';
                } else {
                    str += '<a href="javascript:void(0);" onclick="kkpager.gopage(this);" title="第' + i + '页">' + i + '</a>';
                }
            }
        } else {
            if (this.pno <= 5) {
                for (var i = 1; i <= 7; i++) {
                    if (this.pno == i) {
                        str += '<span class="curr">' + i + '</span>';
                    } else {
                        str += '<a href="javascript:void(0);" onclick="kkpager.gopage(this);" title="第' + i + '页">' + i + '</a>';
                    }
                }
                str += dot;
            } else {
                str += '<a href="javascript:void(0);" onclick="kkpager.gopage(this);" title="第1页">1</a>';
                str += '<a href="javascript:void(0);" onclick="kkpager.gopage(this);" title="第2页">2</a>';
                str += dot;

                var begin = this.pno - 2;
                var end = this.pno + 2;
                if (end > this.total) {
                    end = this.total;
                    begin = end - 4;
                    if (this.pno - begin < 2) {
                        begin = begin - 1;
                    }
                } else if (end + 1 == this.total) {
                    end = this.total;
                }
                for (var i = begin; i <= end; i++) {
                    if (this.pno == i) {
                        str += '<span class="curr">' + i + '</span>';
                    } else {
                        str += '<a href="javascript:void(0);" onclick="kkpager.gopage(this);" title="第' + i + '页">' + i + '</a>';
                    }
                }
                if (end != this.total) {
                    str += dot;
                }
            }
        }

        str = "&nbsp;" + pageSize+"&nbsp;"+str_prv + str + str_next + total_info +  gopage_info;
        $("#" + this.pagerid).html(str);
    }
};

function getParameter(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
    var r = window.location.search.substr(1).match(reg);
    if (r != null) return unescape(r[2]); return null;
};
