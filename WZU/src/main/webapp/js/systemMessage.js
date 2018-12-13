$(function() {
	
	var index;
	
	// 请求数据
	requestData(index);
	//超出部分显示省略号 
	omit1();
	omit2();
	$.getScript('js/choice.js', function(data, textStatus, jqxhr) {
	});
	//删除按钮事件
	$('#delete').unbind().click(function(e){
		// 取消默认事件
		e.preventDefault();
		//删除
		if(confirm('确认删除吗？')){
			del();
		}
	});
	
	$('#reset').unbind().click(function(e){
		// 取消默认事件
		e.preventDefault();
		clearInput();
	});
	
	$('#message-select').unbind().click(function(e){
		// 取消默认事件
		e.preventDefault();
		// 请求消息与用户数据事件
		requestData(index);
	});
	
});

/**
 * 请求数据
 */
function requestData(index) {
	if(index == null || index == 0){
		index = 1;
	}
	
	
	var title = $('#message-title').val();
	var username = $('#message-username').val();
	var sendTime = null;
	var time = $('#message-createTime').val();
	if(time){
		sendTime = dateFormat(new Date(time),"yyyy-MM-dd HH:mm");
	}
	console.log("参数：",title,username,sendTime);
	var content = editor.txt.html();
	$.ajax({
		url : 'sysMessage/list',
		method : 'get',
		data : {
			pageIndex : index,
			pageSize : 8,
			title:title,
			username:username,
			sendTime:sendTime,
		},
		dataType : 'json',
		success : function(data) {
			fillList(data);
		}
	});
}

3
/**
 * 处理数据
 * 
 * @param newsData
 */
function fillList(pageModel) {
	var list = pageModel.list;
	// 1.清空列表
	$('.fill tbody').html('');
	
	datalist = [];
	
	// 2.填充数据
	$.each(list,function(index,object){
		if(object.status == 0){
			object.status = "未读";
		}else{
			object.status = "已读";
		}
		datalist[object.id] = object;
		$('.fill tbody').append(fillData(object));
	});
	// 清空分页栏
	$('#main #pagination').html('');
	// 加载分页栏
	fillPage(pageModel);
	// 分页栏点击事件
	onclickPage(pageModel);
	// 4.编辑消息事件
	editMessageForm();
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
 * @param object
 * @returns
 */
function fillData(object) {
	var $tr = $('<tr></tr>');
	var $td1 = $('<td style="text-align: center;"><input value="'+object.id+'" type="checkbox" /></td>');
	var $td2 = $('<td class="tieleOmit content">' + object.title + '</td>');
	var $td6 = $('<td class="tieleOmit content" TITLE="'+ object.content +'">' + object.content + '</td>');
	var $td3 = $('<td class="contentOmit content">' + object.user.id + '</td>');
	var $td4 = $('<td class="contentOmit content">' + object.user.username + '</td>');
	var $td5 = $('<td class="contentOmit content">' + dateFormat(new Date(object.sendTime),"yyyy-MM-dd") + '</td>');
	var $td7 = $('<td class="contentOmit content">' + object.status + '</td>');
	var $td8 = $('<td class="hide" name = "id">' + object.id + '</td>');
	var $td9 = $('<td><a href="#" id="lookMessage" no="'+ object.id+'" class="editNews">查看</a></td>');
	return $tr.append($td1).append($td2).append($td6).append($td3).append($td4).append($td5).append($td7).append($td8).append($td9);
}
/**
 * 绘制分页栏
 * @param pageModel
 */
function fillPage(pageModel) {
	// 分页按钮栏
	var $ul = $('<ul class="pagination pagination-md col-sm-6" style="margin: 0;"></ul>');
	// 当前页
	var $pageIndex = pageModel.pageIndex;
	// 总页数
	var $totalSize = pageModel.totalSize;
	// 上页
	var $preIndex = $pageIndex - 1 > 0 ? ($pageIndex - 1) : 1;
	// 下页
	var $nextIndex = $pageIndex + 1 < $totalSize ? ($pageIndex + 1)
			: $totalSize;
	// li元素
	var $li1 = $('<li><a style="cursor:pointer;">首页</a></li>');
	var $li2 = $('<li><a style="cursor:pointer;">上一页</a></li>');
	var $li3 = $('<li><a>第&nbsp;' +$pageIndex+'&nbsp;页</a></li>');
	var $li4 = $('<li><a style="cursor:pointer;">下一页</a></li>');
	var $li5 = $('<li><a style="cursor:pointer;">尾页</a></li>');
	$ul.append($li1).append($li2).append($li3).append($li4).append($li5);
	// 分页信息栏
	var $div = $('<div class="col-sm-6 pull-right" style="height:32px"></div>');
	var $toRecord = pageModel.pageSize * $pageIndex;
	var $desc = '每页显示 '
			+ pageModel.pageSize
			+ ' 条记录, Page '
			+ $pageIndex
			+ ' of '
			+ $totalSize
			+ ', Displaying '
			+ (pageModel.firstLimitParam + 1)
			+ ' to '
			+ ($toRecord >= pageModel.recordCount ? pageModel.recordCount
					: $toRecord) + ' of ' + pageModel.recordCount + ' items.';
	$div.text($desc);
	// 整合分页栏
	$('#pagination').append($ul).append($div);
}

/**
 * 分页栏点击事件
 * @param pageModel
 */
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

/**
 * 编辑按钮事件
 */
function editMessageForm() {
	$('.fill tbody a').unbind().click(function (e) {
		// 取消默认事件
		e.preventDefault();
		// 把改行数据填充到form表单中
		showMessage(datalist[$(this).attr('no')]);
	});
}
/**
 * 消息标题字数超出部分处理
 */
function omit1(){
	//超出部分显示省略号 
	   $(".tieleOmit").each(function() {
			var maxwidth = 15;
			if($(this).html().length > maxwidth) {
				$(this).html($(this).text().substring(0, maxwidth));
				$(this).html($(this).html() +'...');
			}
		});
}

/**
 * 消息内容字数超出部分处理
 */
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

/**
 * 将消息显示在编辑框上
 */
function showMessage(object){
	$('input[name=title]').val(object.title);
	$('input[name=id]').val(object.id);
	$('input[name=username]').val(object.user.username);
	$('input[name=sendTime]').val(dateFormat(new Date(object.sendTime),"yyyy-MM-dd"));
	editor.txt.html(object.content);
}

/**
 * 情况编辑框
 */
function clearInput(){
	$('input[name=title]').val('');
	$('input[name=id]').val('');
	$('input[name=username]').val('');
	$('input[name=sendTime]').val('');
	editor.txt.html('');
}

/**
 * 删除
 */
function del(){
	var $idArr = [];
	$.each($('.fill tbody input[type=checkbox]'),function(index,checkbox){
		if($(checkbox).is(':checked')){
			$idArr.push($(checkbox).val());
		}
	});
	if($idArr.length<=0){
		alert('请选择要删除的记录...');
		return;
	}
	$.ajax({
		url:'sysMessage/batchDel',
		method:'post',
		data:{ids:$idArr},
		dataType:'json',
		success:function(data){
			if(data.status){
				alert('删除成功！');
				// 请求消息数据事件
				requestData();
			}
		},
		error:function(){
			alert('服务器异常，删除失败！');
		}
	});
}


/**
 * 日期格式化
 * 
 * @param date
 * @param pattern
 * @returns {String}
 */
function dateFormat(date, pattern) {
	var $dateString;
	var $monthStr;
	if (date.getMonth() + 1 < 10) {
		$monthStr = '0' + (date.getMonth() + 1);
	} else {
		$monthStr = date.getMonth() + 1;
	}
	var $dateStr;
	if (date.getDate() < 10) {
		$dateStr = '0' + date.getDate();
	} else {
		$dateStr = date.getDate();
	}
	var $hour;
	if(pattern == "yyyy-MM-dd HH:mm"){
		$dateString = '' + date.getFullYear() + '-' + $monthStr + '-'
		+ $dateStr + " " + date.getHours() + ":" + date.getSeconds();
	}
	
	if (pattern == "yyyy-MM-dd") {
		$dateString = '' + date.getFullYear() + '-' + $monthStr + '-'
				+ $dateStr;
	} else if (pattern == "yyyyMMdd") {
		$dateString = '' + date.getFullYear() + $monthStr + $dateStr;
	} 
	if (pattern == "yyyy") {
		$dateString = '' + date.getFullYear();
	} else if (pattern == "yyyy") {
		$dateString = '' + date.getFullYear();
	}
	if (pattern == "MM") {
		$dateString = '' + $monthStr;
	} else if (pattern == "MM") {
		$dateString = '' + $monthStr;
	}
	return $dateString;
}