$(function() {
	var index;
	var keyword;
	echoImg();
	// 绑定新闻事件
	newsHandler();
	// 请求新闻数据事件
	requestNews(index,keyword);
	
	//超出部分显示省略号 
	omit1();
	omit2();
	
	$.getScript('js/choice.js', function(data, textStatus, jqxhr) {
		/*if (jqxhr.status == '200')
			console.log('导入js/choice.js成功！');
		else
			console.log('导入js/choice.js失败！');*/
	});
	
	// 搜索事件
	newsSeach(index);
	
	//鼠标移动过去显示大图
    $(".bimg").hide();
    $(".box img").mousemove(function(e){
        $(".bimg").css({top:e.pageY-86,left:e.pageX}).html('<img src="' + this.src + '" />').show();
    }).mouseout( function(){
    $(".bimg").hide();
  })
  
  $('#screen-select').unbind().click(function(e){
		// 取消默认事件
		e.preventDefault();
		// 请求消息与用户数据事件
		requestNews(index,keyword);
	});
    
});

/**
 * 选择图片回显
 */
function echoImg() {
	$('#uploadFile').unbind().change(function(e) {
		// 创建FileReader对象
		var freader = new FileReader();
		// 设置回显图片文件
		freader.readAsDataURL(e.target.files.item(0));
		// 回显重载
		freader.onload = function(e) {
			$('.box img').attr('src', e.target.result);
		}
	});
}

function newsHandler() {
	// 保存或更新新闻事件
	saveNewsFunc();
	// 删除新闻事件
	delNewsFunc();
}

/**
 * 保存新闻事件
 */
function saveNewsFunc() {
	$('#saveNews').unbind().click(function() {
/*		var $formParams = getParams($('#newsForm').serializeArray());*/
		//创建表单数据对象
		var formData = new FormData();
		formData.append("id", $("#newsForm [name=id]").val());
		formData.append("newsName", $("#newsForm [name=newsName]").val());
		formData.append("newsType", $("select option:selected").val());
		formData.append("file", $("#uploadFile")[0].files[0]);
		formData.append("content", editor.txt.html());
		if ($('#newsForm .hide input[name=id]').val()) {
			if(confirm("确认更新吗")){
					updateNews(formData);
			} 
		} else {
			if(confirm("确认增加吗")){
				saveNews(formData);
			}
		}
	});
}

/**
 * 提交数据，并请求数据填充到页面
 * 
 * @param formData
 */
function saveNews(formData) {
	$.ajax({
		url : "news/saveNews",
		method : 'post',
		data : formData,
		dataType : 'json',
		// 告诉jQuery不要去处理发送的数据
		processData : false,
		// 告诉jQuery不要去设置Content-Type请求头
		contentType : false,
		success : function(data) {
			if (data.status) {
				alert('保存成功');
				requestNews();
			}
		},
		error:function(){
			alert('保存失败，请填写完整信息！');
		}
	});
}

/**
 * 请求新闻数据事件
 */
function requestNews(index,keyword) {
	if(index == null || index == 0){
		index = 1;
	}
	if(keyword == null){
		keyword = null;
	}
	var newsName = $('#screen-newsName').val();
	$.ajax({
		url : 'news/'+ index +'/getNewsPageModel',
		method : 'post',
		data : {
			keyword : keyword,
			"newsName":newsName
		},
		dataType : 'json',
		success : function(data) {
			fillNewsList(data);
		}
	});
}

/**
 * 更新新闻
 * @param formData
 */
function updateNews(formData) {
	var $id = $('#newsForm .hide input[name=id]').val();
	//console.log("214345435"+"id:"+$("#newsForm [name=id]").val()+"--newsName:"+$("#newsForm [name=newsName]").val()+"--newsType:"+$("select option:selected").val()+"--content:"+editor.txt.html());
	$.ajax({
		url : "news/updateNews",
		method : 'post',
		data : formData,
		// 告诉jQuery不要去处理发送的数据,用来序列化data
		processData : false,
		// 告诉jQuery不要去设置Content-Type请求头
		contentType : false,
		success : function(data) {
			if (data) {
				alert('更新成功');
				requestNews();
			}
		},
		error:function(){
			alert('服务器异常，保存失败');
		}
	});
}

function delNewsFunc() {
	$('#delNews').unbind().click(function () {
		if(isChoosedAny()){
			if (confirm('确认删除吗?')){
				delNewsHandler();
			}
		} else {
			alert("请选择要删除的新闻！！！");
		}
	});
}

function delNewsHandler() {
	var $idArr = [];
	
	$.each($('.fill tbody input[type=checkbox]'),function(index,checkbox){
		if($(checkbox).is(':checked')){
			$idArr.push($(checkbox).parent().siblings().eq(6).text());
		}
	});
	//console.log($idArr);
	$.ajax({
		url : "news/deleteNews",
		method : 'post',
		data : {
			ids : $idArr
		},
		dataType : 'json',
		success : function(data) {
			if (data) {
				// 重新加载新闻列表
				alert('删除成功!');
				requestNews();
			}
		},
		error : function(){
			alert('服务器异常，请联系管理员');
		}
	});
}

/**
 * 填充新闻列表事件
 * 
 * @param newsData
 */
function fillNewsList(pageModel) {
	var $newsDate = pageModel.list;
	// 1.清空列表
	$('.fill tbody').html('');
	// 2.填充数据
	$.each($newsDate,function(index,news){
		if(news.newsType == 0){
			news.newsType = "新闻动态";
		}else{
			news.newsType = "媒体报道";
		}
		$('.fill tbody').append(fillNewsData(news));
	});
	// 清空分页栏
	$('#main #pagination').html('');
	// 加载分页栏
	fillPage(pageModel);
	// 分页栏点击事件
	onclickPage(pageModel);
	// 3.清空列表事件
	clearNewsFormFunc();
	// 4.编辑新闻事件
	editNewsForm();
	// 5.复选框事件
	// 5-1.标题复选框事件
	theadChecboxFunc();
	// 5-2.列表复选框事件
	tbodyChecboxFunc();
	//超出部分显示省略号 
	omit1();
	omit2();
}

/**
 * 列表填充
 * 
 * @param news
 * @returns
 */
function fillNewsData(news) {
	var $tr = $('<tr></tr>');
	var $td1 = $('<td style="text-align: center;"><input type="checkbox" /></td>');
	var $td2 = $('<td class="newsNameOmit content">' + news.newsName + '</td>');
	var $td3 = $('<td class="contentOmit content">' + news.content + '</td>');
	var $td4 = $('<td>'
			+ dateFormat(new Date(news.createDate), 'yyyy-MM-dd')
			+ '</td>');
	var $td5 = $('<td class="content">&nbsp;&nbsp;&nbsp;&nbsp;' + news.newsType + '&nbsp;</td>');
	var $td6 = $('<td class="content"><img height="20" width="20" src="'+ news.newsPic +'"/></td>');
	var $td7 = $('<td><a href="#" class="editNews">编辑</a></td>');
	var $td8 = $('<td class="hide" name = "id">' + news.id + '</td>');
	return $tr.append($td1).append($td2).append($td3).append($td4).append($td5).append($td6).append($td7).append($td8);
}

/**
 * 编辑列表事件
 */
function editNews(){
	
	
}

/**
 * 清空列表事件
 */
function clearNewsFormFunc() {
	$('#newsForm .hide input[name = id]').val('');
	$('#newsForm input[name = newsName]').val('');
	$('#newsForm select[name = newsSelect]').val(0);
	$('#newsForm input[name = file]').val('');
	$('#img').attr('src',"");
	editor.txt.html("");
}

/**
 * 编辑新闻事件
 */
function editNewsForm() {
	$('.fill tbody a').unbind().click(function (e) {
		// 取消默认事件
		e.preventDefault();
		// 把改行数据填充到form表单中
		loadTrNewsForm($(this));
	});
}
function loadTrNewsForm(that) {
	var $tdArr = that.parent().siblings();
	//console.log($tdArr.eq(6).text()); //id
	var id = $tdArr.eq(6).text();
	$.ajax({
		url:"findNewsById",
		data:{id:id},
		datatype:"json",
		success: function(data){
			$('#newsForm input[name = newsName]').val(data.newsName);
			$('#newsForm input[name = id]').val(data.id);
			$('#newsForm select[name = newsSelect]').val(data.newsType);
			$('#img').attr('src',data.newsPic);
			editor.txt.html(data.content);
		}
	});
}

/**
 * 关键字查询事件
 * @param index
 */
function newsSeach(index) {
	$('.btn-primary').unbind().click(function () {
		keyword = $('input[name=keyword]').val();
		requestNews(index,keyword);
	});
}

function pageSeach(index) {
	keyword = $('input[name=keyword]').val();
	if(keyword == null){
		keyword = null;
	}
	requestNews(index,keyword);
}

function onclickPage(pageModel) {
	// 当前页
	var $pageIndex = pageModel.pageIndex;
	// 总页数
	var $totalSize = pageModel.totalSize;
	// 上页
	var $preIndex = $pageIndex - 1 > 0 ? ($pageIndex - 1) : 1;
	// 下页
	var $nextIndex = $pageIndex + 1 < $totalSize ? ($pageIndex + 1)
			: $totalSize;
	$('#pagination ul li:eq(0) a').attr("onClick","pageSeach(1)");
	$('#pagination ul li:eq(1) a').attr("onClick","pageSeach(" + $preIndex+")");
	$('#pagination ul li:eq(3) a').attr("onClick","pageSeach(" + $nextIndex+")");
	$('#pagination ul li:eq(4) a').attr("onClick","pageSeach(" + $totalSize+")");
}

function omit1(){
	//超出部分显示省略号 
	   $(".newsNameOmit").each(function() {
			var maxwidth = 10;
			if($(this).html().length > maxwidth) {
				$(this).html($(this).text().substring(0, maxwidth));
				$(this).html($(this).html() +'...');
			}
		});
}
function omit2(){
	//超出部分显示省略号 
	   $(".contentOmit").each(function() {
			var maxwidth = 20;
			if($(this).html().length > maxwidth) {
				$(this).html($(this).text().substring(0, maxwidth));
				$(this).html($(this).html() +'...');
			}
		});
}