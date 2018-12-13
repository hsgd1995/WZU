$(function() {
	var index;
	var keyword;
	echoImg();
	// 绑定用户事件
	userHandler();
	// 请求用户数据事件
	requestUser();
	$.getScript('js/choice.js', function(data, textStatus, jqxhr) {
		/*if (jqxhr.status == '200')
			console.log('导入js/choice.js成功！');
		else
			console.log('导入js/choice.js失败！');*/
	});
	// 搜索按钮事件
	questionSeach();
	// 搜索事件
	//搜索按钮事件
	$('#screen-select').unbind().click(function(e){
		// 取消默认事件
		e.preventDefault();
		// 请求消息与用户数据事件
		requestUser(index);
	});
	
	//鼠标移动过去显示大图
    $(".bimg").hide();
    $(".box img").mousemove(function(e){
        $(".bimg").css({top:e.pageY-86,left:e.pageX}).html('<img src="' + this.src + '" />').show();
    }).mouseout( function(){
    $(".bimg").hide();
  });
  
  omit1()
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

function userHandler() {
	// 保存用户事件
	saveUserFunc();
	// 更新用户事件
	delUserFunc();
}

/**
 * 关键字查询事件
 * 
 * @param index 第几页
 */
function questionSeach(index) {
	$('#query').unbind().click(function() {
		requestUser(index);
	});
}

/**
 * 保存用户事件
 */
function saveUserFunc() {
	$('#saveUser').unbind().click(function() {
/*		var $formParams = getParams($('#userForm').serializeArray());*/
		var formData = new FormData();
		formData.append("id", $("#userForm [name=id]").val());
		formData.append("loginName", $("#userForm [name=loginName]").val());
		formData.append("username",$("#userForm [name=username]").val());
		formData.append("password", $("#userForm [name=password]").val());
		formData.append("phoneNumber",$("#userForm [name=phoneNumber]").val());
		formData.append("email",$("#userForm [name=email]").val());
		formData.append("cardId",$("#userForm [name=cardId]").val());
		formData.append("area",$("#userForm [name=area]").val());
		formData.append("remark", $("select option:selected").val());
		formData.append("file", $("#uploadFile")[0].files[0]);
		
		if ($('#userForm .hide input[name=id]').val()) {
			if(confirm("确认更新吗")){
				updateUser(formData);
			} 
		} else {
			if(confirm("确认增加吗")){
				saveUser(formData);
			}
		}
	});
}

/**
 * 增加用户
 * 提交数据，并请求数据填充到页面
 * 
 * @param formParams
 */
function saveUser(formData) {
	$.ajax({
		url : "user/saveUser",
		method : 'post',
		data : formData,
		dataType : 'json',
		// 告诉jQuery不要去处理发送的数据
		processData : false,
		// 告诉jQuery不要去设置Content-Type请求头
		contentType : false,
		success : function(data) {
			if (data.status) {
				alert('添加用户成功!');
				requestUser();
			}
		},
		error:function(){
			alert('保存失败，请填写完整/正确信息！');
		}
	});
}

/**
 * 请求用户数据事件
 */
function requestUser(index) {
	if(index == null || index == 0){
		index = 1;
	}
	var userName = $('#screen-userName').val();
	var userIdcar = $('#screen-userIdcar').val();
	$.ajax({
		url : 'user/' + index + '/getUserPageModel',
		method : 'post',
		data : {
			"userName" : userName,
			"userIdcar" : userIdcar
		},
		dataType : 'json',
		success : function(data) {
			fillUserList(data);
		}
	});
}
/**
 * 更新用户信息
 * @param formData
 */
function updateUser(formData) {
	var $UserId = $('#userForm .hide input[name=id]').val();
	$.ajax({
		url : "user/updateUser",
		method : 'post',
		data : formData,
		// 告诉jQuery不要去处理发送的数据,用来序列化data
		processData : false,
		// 告诉jQuery不要去设置Content-Type请求头
		contentType : false,
		success : function(data) {
			if (data) {
				alert('更新成功!');
				requestUser();
			}
		},
		error:function(){
			alert('服务器异常，保存失败');
		}
	});
}

function delUserFunc() {
	$('#delUser').unbind().click(function () {
		if(isChoosedAny()){
			if (confirm('是否删除用户?')){
				delUserHandler();
			}
		} else {
			alert("请选择要删除的用户信息");
		}
	});
}

/**
 * 删除用户
 */
function delUserHandler() {
	var $idArr = [];
	
	$.each($('.fill tbody input[type=checkbox]'),function(index,checkbox){
		if($(checkbox).is(':checked')){
			$idArr.push($(checkbox).parent().siblings().eq(11).text());
		}
	});
	//console.log($idArr);
	$.ajax({
		url : "user/deleteUser",
		method : 'post',
		data : {
			ids : $idArr
		},
		dataType : 'json',
		success : function(data) {
			if (data) {
				alert('删除成功！');
				// 重新加载新闻列表
				requestUser();
			}
		},
		error : function(){
			alert('服务器异常，请联系管理员');
		}
	});
}

/**
 * 填充用户列表事件
 * 
 * @param UserData
 */
function fillUserList(pageModel) {
	var $userData = pageModel.list;
	// 1.清空列表
	$('.fill tbody').html('');
	// 2.填充数据
	$.each($userData,function(index,user){
		if(user.remark == 0){
			user.remark = "-";
		}else{
			user.remark = "已预约";
		}
		$('.fill tbody').append(fillUserData(user));
	});
	// 清空分页栏
	$('#main #pagination').html('');
	// 加载分页栏
	fillPage(pageModel);
	// 分页栏点击事件
	onclickPage(pageModel);
	// 3.清空列表事件
	clearUserFormFunc();
	// 4.编辑用户事件
	editUserForm();
	// 5.复选框事件
	// 5-1.标题复选框事件
	theadChecboxFunc();
	// 5-2.列表复选框事件
	tbodyChecboxFunc();
	//详细介绍显示省略
	omit1();
}

function editUserForm() {
	$('.fill tbody a').unbind().click(function (e) {
		// 取消默认事件
		e.preventDefault();
		// 把改行数据填充到form表单中
		loadTrUserForm($(this));
	});
}

function loadTrUserForm(that) {
	var $tdArr = that.parent().siblings();
	var id = $tdArr.eq(11).text();
	$.ajax({
		url:"findUserById",
		data:{id:id},
		dataType:"json",
		success: function(data){
			$('#userForm input[name = id]').val(data.id);
			$('#userForm input[name = loginName]').val(data.loginName);
			$('#userForm input[name = username]').val(data.username);
			$('#userForm input[name = password]').val(data.password);
			$('#userForm input[name = phoneNumber]').val(data.phoneNumber);
			$('#userForm input[name = email]').val(data.email);
			$('#userForm input[name = cardId]').val(data.cardId);
			$('#userForm input[name = area]').val(data.area);
			$('#userForm select[name = remark]').val(data.remark);
			$('#img').attr('src',data.userPic);
		}
	});
}

/**
 * 列表填充
 * 
 * @param User
 * @returns
 */
function fillUserData(user) {
	var $tr = $('<tr></tr>');
	var $td1 = $('<td style="text-align: center;"><input type="checkbox" /></td>');
	var $td_userId = $('<td>' + user.id + '</td>');
	var $td2 = $('<td class="userOmit">' + user.loginName + '</td>');
	var $td3 = $('<td class="userOmit">' + user.username + '</td>');
	var $td4 = $('<td class="userOmit">' + user.password + '</td>');
	var $td5 = $('<td>' + user.phoneNumber + '</td>');
	var $td6 = $('<td>' + user.email + '</td>');
	var $td7 = $('<td>' + user.cardId + '</td>');
	var $td8 = $('<td>' + user.area + '</td>');
	var $td9 = $('<td><img height="20" width="20" src="'+ user.userPic +'"/></td>');
	var $td10 = $('<td>' + user.remark + '</td>');
	var $td11 = $('<td><a href="#">编辑</a></td>');
	var $td12 = $('<td class="hide" name = "id">' + user.id + '</td>');
	return $tr.append($td1).append($td_userId).append($td2).append($td3).append($td4).append($td5).append($td6).append($td7).append($td8).append($td9).append($td10).append($td11).append($td12);
}

/**
 * 清空表单
 */
function clearUserFormFunc() {
	$('#userForm input[name = id]').val('');
	$('#userForm input[name = loginName]').val('');
	$('#userForm input[name = username]').val('');
	$('#userForm input[name = password]').val('');
	$('#userForm input[name = phoneNumber]').val('');
	$('#userForm input[name = email]').val('');
	$('#userForm input[name = cardId]').val('');
	$('#userForm input[name = area]').val('');
	$('#userForm select[name = remark]').val(0);
	$('#userForm input[name = file]').val('');
	$('#img').attr('src',"");
}

function pageSeach(index) {
	keyword = $('input[name=keyword]').val();
	if(keyword == null){
		keyword = null;
	}
	requestUser(index,keyword);
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
	   $(".userOmit").each(function() {
			var maxwidth = 5;
			if($(this).text().length > maxwidth) {
				$(this).text($(this).text().substring(0, maxwidth));
				$(this).html($(this).html() +'...');
			}
		});
}

