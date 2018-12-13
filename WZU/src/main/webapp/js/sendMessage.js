$(function() {
	var index;
	var keyword;

	// 请求消息与用户数据事件
	requestData(index,keyword);
	//获取消息下拉列表数据
	getMessageList();
	//用户数据
	getUserList();
	
	//超出部分显示省略号 
	omit1();
	omit2();
	
	$.getScript('js/choice.js', function(data, textStatus, jqxhr) {
	});
	
	
	//保存按钮事件
	$('#save').unbind().click(function (e) {
		// 取消默认事件
		e.preventDefault();
		//添加
		addData();
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
	
	//查询按钮事件
	$('#select').unbind().click(function(e){
		// 取消默认事件
		e.preventDefault();
		//用户数据
		getUserList();
	});
	
	$('#message-select').unbind().click(function(e){
		// 取消默认事件
		e.preventDefault();
		// 请求消息与用户数据事件
		requestData(index,keyword);
	});
});

/**
 * 获取消息数据
 */
function getMessageList(){
	var index = 1;
	$.ajax({
		url:'message/list',
		method:'get',
		dataType:'json',
		data:{
			pageIndex : index,
			pageSize : 20,
			message:null
		},
		success : function(data) {
			fillSelect(data);
		}
	});
}

/**
 * 填充消息下拉列表
 */
function fillSelect(data){
	var obj = document.getElementById("messageSelect");
	var list = data.list;
	obj.add(new Option('请选择...', -1));
	for (var index = 0; index < list.length; index++) {
        //添加一个选项
        obj.add(new Option(list[index].title, list[index].id));
    }
}

/**
 * 请求消息与用户数据事件
 */
function requestData(index,keyword) {
	if(index == null || index == 0){
		index = 1;
	}
	if(keyword == null){
		keyword = null;
	}
	
	var title = $('#message-title').val();
	var username = $('#message-username').val();
	var sendTime = null;
	var time = $('#message-createTime').val();
	if(time){
		sendTime = dateFormat(new Date(time),"yyyy-MM-dd HH:mm");
	}
	$.ajax({
		url : 'sendMessage/list',
		method : 'get',
		data : {
			pageIndex : index,
			pageSize : 20,
			title : title,
			username : username,
			sendTime:sendTime
		},
		dataType : 'json',
		success : function(data) {
			fillList(data);
		}
	});
}

/**
 * 填充消息列表事件
 * 
 * @param newsData
 */
function fillList(pageModel) {
	var list = pageModel.list;
	console.log(list);
	// 1.清空列表
	$('.fill tbody').html('');
	// 2.填充数据
	$.each(list,function(index,object){
		if(object.status == 0){
			object.status = "未读";
		}else{
			object.status = "已读";
		}
		$('.fill tbody').append(fillMessageData(object));
	});
	// 清空分页栏
	$('#main #pagination').html('');
	// 加载分页栏
	fillPage(pageModel);
	// 分页栏点击事件
	onclickPage(pageModel);
	
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
function fillMessageData(object) {
	var $tr = $('<tr></tr>');
	var $td1 = $('<td style="text-align: center;"><input value="'+object.id+'" type="checkbox" /></td>');
	var $td2 = $('<td class="content"><span class="tieleOmit" TITLE="'+object.message.title+'">' + object.message.title + '</span></td>');
	var $td3 = $('<td class="contentOmit content">' + object.user.id + '</td>');
	var $td4 = $('<td class="contentOmit content">' + object.user.username + '</td>');
	var $td6 = $('<td class="contentOmit ">' + dateFormat(new Date(object.sendTime),"yyyy-MM-dd") + '</td>');
	var $td5 = $('<td class="contentOmit content">' + object.status + '</td>');
	var $td8 = $('<td class="hide" name = "id">' + object.id + '</td>');
	return $tr.append($td1).append($td2).append($td3).append($td4).append($td6).append($td5).append($td8);
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
 * 分页栏事件
 * @param pageIndex
 */
function pageSeach(pageIndex){
	requestData(pageIndex,null);
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
 * 添加
 */
function addData(){
	var $userIdArr = [];
	var messageIds = [];
	messageIds.push($('#Form select').val());
	var allUser = false;
	$.each($('td img[use=true]'),function(index,img){
			$userIdArr.push($(img).parent().attr('id'));
	});
	
	if(messageIds.length<=0 || messageIds[0]==-1){
		alert('请选择消息...');
		return;
	}
	
	if($userIdArr.length<=0){
		alert('请选择用户...');
		return;
	}
	
	if($('#selectAllUser').is(':checked')){
		allUser = true;
	}
	
	
	var confirmMessage = '';
	if(allUser){
		confirmMessage='确认发送该消息给所有用户吗？';
	}else{
		confirmMessage='确认发送该消息给'+$userIdArr.length+'个用户吗？'
	}
	if(confirm(confirmMessage)){
		add(messageIds,$userIdArr,allUser);
	}
}

/**
 * 提交数据
 * @param formData
 */
function add(messageIds,$userIdArr,allUser){
	$.ajax({
		url:'sendMessage/add',
		method:'post',
		data:{messageIds:messageIds,userIds:$userIdArr,allUser:allUser},
		dataType:'json',
		success : function(data){
			if(data.status){
				alert('发送成功！');
				// 请求消息数据事件
				requestData();
			}else{
				alert('发送失败，请选择要发送的消息和用户！');
			}
		},
		error:function(){
			alert('服务器异常，发送失败！')
		}
	});
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
		url:'sendMessage/batchDel',
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
 * 获取用户列表
 */	
function getUserList(pageIndex){
	var index = 1;
	if(pageIndex){
		index = pageIndex;
	}
	var keyword = $('input[id=username]').val();
	$.ajax({
		url : 'user/' + index + '/getUserPageModel',
		method : 'get',
		data : {
			pageSize:100,
			userName:keyword
		},
		dataType : 'json',
		success : function(data) {
			fillUserList(data);
		}
	});
}

/**
 * 处理用户数据
 */
function fillUserList(data){
	$('#userList').html('');
	//用户数组
	userArray = [];
	table = $('<table style="width:100%;"></table>');
	var tr;
	$.each(data.list,function(index,object){
		if(index==0||(index)%10==0){
			tr = $('<tr></tr>');
			var checkBox = $('<td  style="border:solid 1px #1963AA;text-align:center; padding:10px 5px;"><input name="userCheck"  type="checkbox" value="'+index+'"></td>');
			tr = tr.append(checkBox);
		}
		userArray[object.id] = object;
		tr = tr.append(createUserDom(object));
		if(index==(data.list.length-1)||(index+1)%10==0){
			table = table.append(tr);
		}
	});
	
	$('#userList').append(table);
	
	var pageButton = $('<br><div class="row"><div class="col-sm-12" id="userPagination"></div></div>');
	$('#userList').append(pageButton);
		//绘制分页栏
		fillUserPageButton(data);
		// 分页栏点击事件
		onclickUserPageButton(data);
	
	//上层控制底层可以用两个修改.prop('checked',boolean).trigger('click');
	//底层控制上层只能用一个修改.prop('checked',boolean)
	//用户单元格点击事件
	$('.user').unbind().click(function (e) {
		// 取消默认事件
		e.preventDefault();
		changeBorder($(this));
		$(this).parent().parent().children(':first').children('input').prop('checked',false);
		if(!$(this).is(':checked')){
			$('#selectCurrentPageUser').prop('checked',false);
			$('#selectAllUser').prop('checked',false);
		}
	});
	
	
	//用户多选框点击事件
	$('td input[name=userCheck]').unbind().click(function(e){
			
		var userUnit = $(this).parent().parent().children().children('.user');
		if($(this).is(':checked')){
			userUnit.each(function(){
				$(this).parent().children('div').html('<i style="color:#007500; font-size:18px;" class="icon-ok-circle"></i>');
				$(this).attr('use','true');
			});
		}else{
			userUnit.each(function(){
				$(this).parent().children('div').html('');
				$(this).attr('use','false');
			});
			$('#selectCurrentPageUser').prop('checked',false);
			$('#selectAllUser').prop('checked',false);
		}
		
	});
	
	
	//当前页用户多选框的点击事件
	$('#selectCurrentPageUser').unbind().click(function(e){
		if(!$(this).is(':checked')){
			$('td input[name=userCheck]').prop('checked',true).trigger('click');
		}else{
			$('td input[name=userCheck]').prop('checked',false).trigger('click');
		}
		//$('td input[name=userCheck]').trigger('click');
		//当所有用户多选框被选中时，当前页用户多选框必须被选中
		if($('#selectAllUser').is(':checked')){
			//必须选中当前页用户多选框
			$(this).prop('checked',true).trigger('click');
		}
	});
	
	//所有用户多选框的点击事件
	$('#selectAllUser').unbind().click(function(e){
		if($(this).is(':checked')){
			if(!$('#selectCurrentPageUser').is(':checked')){
				//触发当前页用户多选框点击事件
				$('#selectCurrentPageUser').trigger('click');
			}
		}else{
			$('#selectCurrentPageUser').trigger('click');
		}
		
	});
	
	//每一次加载用户数据时，如果所有用户多选框被选中，则将所有用户单元格选中
	if($('#selectAllUser').is(':checked')){
		$('#selectCurrentPageUser').prop('checked',true).trigger('click');
	}else{
		
		//每次请求数据后把当前页多选框设为不选中
		//但如果所有用户多选框是被选中的，即使在这里把当前页用户多选框设为不选中状态，在每一次请求用户数据时也会被设为选中装态，这样的效果是合理的
		$('#selectCurrentPageUser').prop('checked',false);
	}
}

/**
 * 改变jq目标样式
 * @param target
 */
function changeBorder(target){
	if(target.attr('use')=='false'){
		//修改边框
		//target.attr('style','border:solid 4px #438EB9;text-align:center; padding:10px 10px;');
		target.parent().children('div').html('<i style="color:#007500; font-size:18px;" class="icon-ok-circle"></i>');
		target.attr('use','true');
	}else{
		//修改边框
		//target.attr('style','border:solid 1px #1963AA;text-align:center; padding:10px 10px;');
		target.parent().children('div').html('');
		target.attr('use','false');
	}
}

/**
 * 生成用户标签
 */
function createUserDom(object){
	var td1 = $('<td id="'+object.id+'"  style="border:solid 1px #1963AA;text-align:center; padding:10px 10px; position:relative;"></td>');
	var username = $('<span  style="text-overflow: ellipsis;  -o-text-overflow: ellipsis; white-space:nowrap; width:70px; overflow: hidden ; display:block; margin:0 auto;" TITLE="'+object.username+'">'+'<a  style="z-index: 100;" href="javascript:void(0)" onclick="showUser('+object.id+')">'+object.username+'</a>'+'</span>');
	var img = $('<img class="user" use="false" style="height:70px;width:70px;" src="'+object.userPic+'"><br>');
	var div = $('<div style="height:14px;width:18px; position:absolute; top:1px; right:1px;"></div>');
	
	return td1.append(img).append(username).append(div);
}

/**
 * 显示用户详细信息
 */
function showUser(id){
	console.log(id);
	console.log(userArray[id]);
	$('#table-username').html(userArray[id].username);
	$('#table-username').attr('title',userArray[id].username);
	$('#table-tel').html(userArray[id].phoneNumber);
	$('#table-tel').attr('title',userArray[id].phoneNumber);
	$('#table-email').html(userArray[id].email);
	$('#table-email').attr('title',userArray[id].email);
	$('#table-area').html(userArray[id].area);
	$('#table-area').attr('title',userArray[id].area);
	$('#table-pic').attr('src',userArray[id].userPic);
	
	$('#myModal').modal('show');
}

/**
 * 绘制用户分页栏
 * @param pageModel
 */
function fillUserPageButton(pageModel){
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
	$('#userPagination').append($ul).append($div);
}

/**
 * 添加用户分页栏点击事件
 * @param pageModel
 */
function onclickUserPageButton(pageModel){
	// 当前页
	var $pageIndex = pageModel.pageIndex;
	// 总页数
	var $totalSize = pageModel.totalSize;
	// 上页
	var $preIndex = $pageIndex - 1 > 0 ? ($pageIndex - 1) : 1;
	// 下页
	var $nextIndex = $pageIndex + 1 < $totalSize ? ($pageIndex + 1)
			: $totalSize;
	$('#userPagination ul li:eq(0) a').attr("onClick","userPageSeach(1)");
	$('#userPagination ul li:eq(1) a').attr("onClick","userPageSeach(" + $preIndex+")");
	$('#userPagination ul li:eq(3) a').attr("onClick","userPageSeach(" + $nextIndex+")");
	$('#userPagination ul li:eq(4) a').attr("onClick","userPageSeach(" + $totalSize+")");
}
/**
 * 用户分页栏事件
 * @param pageIndex
 */
function userPageSeach(pageIndex){
	getUserList(pageIndex);
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