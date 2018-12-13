$(function() {
	var index;
	var keyword;
	// 绑定用户事件
	userHandler();
	// 请求用户数据事件
	requestUser();
//	$.getScript('js/choice.js', function(data, textStatus, jqxhr) {
//		/*if (jqxhr.status == '200')
//			console.log('导入js/choice.js成功！');
//		else
//			console.log('导入js/choice.js失败！');*/
//	});
	// 搜索按钮事件
	$('#screen-select').unbind().click(function(e){
		// 取消默认事件
		e.preventDefault();
		// 请求消息与用户数据事件
		requestUser(index);
	});
	
	
});

function userHandler() {
	// 保存用户事件
	saveUserFunc();
	// 更新用户事件
	delUserFunc();
}


/**
 * 验证表单数据
 */
function fromVerification(){
	if($("#userForm [name=userId]").val() ==""){
		return "用户ID不能为空!!!";
	}
	if($("#userForm [name=address]").val() ==""){
		return "通讯地址不能为空!!!";
	}
	if($("#userForm [name=birthday]").val() ==""){
		return "出生年月不能为空!!!";
	}
	if($("#education option:selected").val() ==""){
		return "请选择学历!!!";
	}
	if($("#sex option:selected").val() == ""){
		return "请选择性别!!!";
	}
	if($("#privacy option:selected").val() == ""){
		return "请选择隐私!!!";
	}
	if($("#atSchool option:selected").val() == ""){
		return "请选择是否在校!!!";
	}
	return "";
}
/**
 * 保存用户事件
 */
function saveUserFunc() {
	$('#saveUser').unbind().click(function() {
/*		var $formParams = getParams($('#userForm').serializeArray());*/
		if(fromVerification() != ""){
			return alert(fromVerification());
		}
		var formData = new FormData();
		formData.append("userId", $("#userForm [name=userId]").val());
		formData.append("privacy", $("#privacy option:selected").val());
		formData.append("atSchool",$("#atSchool option:selected").val());
		formData.append("sex",$("#sex option:selected").val());
		formData.append("education",$("#education option:selected").val());
		formData.append("school",$("#userForm [name=school]").val());
		formData.append("major",$("#userForm [name=major]").val());
		formData.append("grade",$("#userForm [name=grade]").val());
		formData.append("address",$("#userForm [name=address]").val());
		formData.append("entrance",$("#userForm [name=entrance]").val());
		formData.append("birthday",$("#userForm [name=birthday]").val());
		formData.append("personalProfile",$("#userForm [name=personalProfile]").val());
		
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
		url : "userInfo/saveUserInfo",
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
			}else{
				alert('用户不存在!');
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
	var userId = $('#screen-userId').val();
	$.ajax({
		url : 'userInfo/' + index + '/getUserInfoPageModel',
		method : 'post',
		data : {
			"userId" : userId
		},
		dataType : 'json',
		success : function(data) {
			fillUserInfoList(data);
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
		url : "userInfo/updateUserInfo",
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
			$idArr.push($(checkbox).parent().siblings().eq(10).text());
		}
	});
//	console.log($idArr);
	$.ajax({
		url : "userInfo/deleteUserInfo",
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
function fillUserInfoList(pageModel) {
	var $userInfoData = pageModel.list;
	// 1.清空列表
	$('.fill tbody').html('');
	// 2.填充数据
	$.each($userInfoData,function(index,userInfo){
		$('.fill tbody').append(fillUserInfoData(userInfo));
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
	omit3();
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
	var id = $tdArr.eq(10).text();
	$.ajax({
		url:"findUserInfoById",
		data:{id:id},
		dataType:"json",
		success: function(data){
			var entranceTime = dateFormat(new Date(data.entrance), 'yyyy-MM-dd');
			var birthdayTime = dateFormat(new Date(data.birthday), 'yyyy-MM-dd');
			$('#userForm input[name=id]').val(data.id);
			$('#userForm [name=userId]').val(data.userId);
			$('#userForm [name=userId]').attr("readOnly","true");
			$('#userForm select[name=privacy]').val(data.privacy);
			$('#userForm select[name=atSchool]').val(data.atSchool);
			$('#userForm select[name=sex]').val(data.sex);
			$('#userForm select[name=education]').val(data.education);
			$('#userForm [name=school]').val(data.school);
			$('#userForm [name=major]').val(data.major);
			$('#userForm [name=grade]').val(data.grade);
			$('#userForm input[name = entrance]').val(entranceTime);
			$('#userForm input[name = birthday]').val(birthdayTime);
			$('#userForm [name = address]').val(data.address);
			$('#userForm [name = personalProfile]').val(data.personalProfile);
		}
	});
}

/**
 * 列表填充
 * 
 * @param User
 * @returns
 */
function fillUserInfoData(userInfo) {
	var $tr = $('<tr></tr>');
	var $td1 = $('<td style="text-align: center;"><input type="checkbox" /></td>');
	var $td2 = $('<td>' + userInfo.userId + '</td>');
	var $td3 = $('<td class="userInfoContentOmit">' + userInfo.school + '</td>');
	var $td4 = $('<td class="userInfoContentOmit">' + userInfo.major + '</td>');
	var $td5 = $('<td>' + userInfo.grade + '</td>');
	var $td6 = $('<td class="userInfoContentOmit">' + userInfo.address + '</td>');
	var $td7 = $('<td>' + userInfo.atSchool + '</td>');
	var $td8 = $('<td>' + userInfo.education + '</td>');
	var $td9 = $('<td>' + userInfo.sex +'</td>');
	var $td10 = $('<td>' + userInfo.privacy + '</td>');
	var $td11 = $('<td><a href="#">编辑</a></td>');
	var $td12 = $('<td class="hide" name = "id">' + userInfo.id + '</td>');
	return $tr.append($td1).append($td2).append($td3).append($td4).append($td5).append($td6).append($td7).append($td8).append($td9).append($td10).append($td11).append($td12);
}

/**
 * 清空表单
 */
function clearUserFormFunc() {
	$('#userForm input[name=id]').val('');
	$('#userForm [name=userId]').val('');
	$('#userForm [name=userId]').removeAttr("readonly");
	$('#privacy option:selected').val('');
	$('#atSchool option:selected').val('');
	$('#sex option:selected').val('');
	$('#education option:selected').val();
	$('#userForm [name=school]').val('');
	$('#userForm [name=major]').val('');
	$('#userForm [name=grade]').val('');
	$('#userForm input[name = entrance]').val('');
	$('#userForm input[name = birthday]').val('');
	$('#userForm [name = address]').val('');
	$('#userForm [name = personalProfile]').val('');
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

function omit3(){
	//超出部分显示省略号 
	   $(".userInfoContentOmit").each(function() {
			var maxwidth = 10;
			if($(this).text().length > maxwidth) {
				$(this).text($(this).text().substring(0, maxwidth));
				$(this).html($(this).html() +'...');
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

//验证字符串是否是数字
function checkNumber(theObj) {
  var reg = /^[0-9]+.?[0-9]*$/;
  if (reg.test(theObj)) {
    return true;
  }
  return false;
}
