/**
 * 基地进驻入口
 */

$(function() {
	var index;
	var keyword;
	// 绑定管理员事件
	managerHandler();
	// 请求管理员数据事件
	requestManager();
	
	// 搜索事件
	managerSeach(index);
});


function managerHandler() {
	// 保存管理员事件
	saveManagerFunc();
	// 更新管理员事件
	delManagerFunc();
}

/**
 * 保存管理员事件
 */
function saveManagerFunc() {
	$('#saveManager').unbind().click(function() {
		var formData = new FormData();
		debugger;
		formData.append("id", $("#managerForm [name=id]").val());
		formData.append("name", $("#managerForm [name=name]").val());
		formData.append("state",$("#managerForm [name=state]").val());
		formData.append("startTime",$("#managerForm [name=startTime]").val());
		formData.append("endTime",$("#managerForm [name=endTime]").val());
		if ($('#managerForm .hide input[name=id]').val()) {
			if(confirm("确认更新吗")){
				updateManager(formData);
			} 
		} else {
			if(confirm("确认增加吗")){
				saveManager(formData);
			}
		}
	});
}

/**
 * 提交数据，并请求数据填充到页面
 * 
 * @param formParams
 */
function saveManager(formParams) {
	$.ajax({
		url : "baseInto/add",
		method : 'post',
		data : formParams,
		dataType : 'json',
		// 告诉jQuery不要去处理发送的数据
		processData : false,
		// 告诉jQuery不要去设置Content-Type请求头
		contentType : false,
		success : function(data) {
			if (data.status) {
				alert('保存成功');
				requestManager();
			}
		},
		error:function(){
			alert('保存失败，请填写完整信息！');
		}
	});
}

/**
 * 请求管理员数据事件
 */
function requestManager(index,keyword) {
	if(index == null || index == 0){
		index = 1;
	}
	if(keyword == null){
		keyword = null;
	}
	$.ajax({
		url : 'baseInto/list',
		method : 'get',
		data : {
			pageSize:8,
			pageIndex:index,
			keyword : keyword
		},
		dataType : 'json',
		success : function(data) {
			fillManagerList(data);
		}
	});
}

function updateManager(formData) {
	var $UserId = $('#managerForm .hide input[name=id]').val();
	$.ajax({
		url : "baseInto/update",
		method : 'post',
		data : formData,
		// 告诉jQuery不要去处理发送的数据,用来序列化data
		processData : false,
		// 告诉jQuery不要去设置Content-Type请求头
		contentType : false,
		success : function(data) {
			if (data) {
				alert('更新成功');
				requestManager();
			}
		},
		error:function(){
			alert('服务器异常，保存失败');
		}
	});
}

function delManagerFunc() {
	$('#delManager').unbind().click(function () {
		if(isChoosedAny()){
			if (confirm('确认删除吗?')){
				delManagerHandler();
			}
		} else {
			alert("请选择要删除的记录");
		}
	});
}

function delManagerHandler() {
	var $idArr = [];
	
	$.each($('.fill tbody input[type=checkbox]'),function(index,checkbox){
		if($(checkbox).is(':checked')){
			$idArr.push($(checkbox).attr('no'));
		}
	});
	//console.log($idArr);
	$.ajax({
		url : "baseInto/del",
		method : 'post',
		data : {
			ids : $idArr
		},
		dataType : 'json',
		success : function(data) {
			if (data) {
				alert('删除成功!');
				// 重新加载新闻列表
				requestManager();
			}
		},
		error : function(){
			alert('服务器异常，请联系管理员');
		}
	});
}

/**
 * 填充管理员列表事件
 * 
 * @param ManagerData
 */
function fillManagerList(pageModel) {
	var $managerData = pageModel.list;
	// 1.清空列表
	$('.fill tbody').html('');
	// 2.填充数据
	$.each($managerData,function(index,manager){
		$('.fill tbody').append(fillManagerData(manager));
	});
	// 清空分页栏
	$('#main #pagination').html('');
	// 加载分页栏
	fillPage(pageModel);
	// 分页栏点击事件
	onclickPage(pageModel);
	// 3.清空列表事件
	clearManagerFormFunc();
	// 4.编辑管理员事件
	editManagerForm();
	// 5.复选框事件
	// 5-1.标题复选框事件
	theadChecboxFunc();
	// 5-2.列表复选框事件
	tbodyChecboxFunc();
	//将全选按钮设为非选中状态
	cleanSelectAll();
}

/**
 * 将全选按钮设为非选中状态
 */
function cleanSelectAll(){
	$('#allSelect').prop('checked',false);
}

function editManagerForm() {
	$('.fill tbody a').unbind().click(function (e) {
		// 取消默认事件
		e.preventDefault();
		// 把改行数据填充到form表单中
		loadTrManagerForm($(this));
	});
}

function loadTrManagerForm(that) {
	var id = that.attr('thisId');
	$.ajax({
		url:"baseInto/findById",
		data:{id:id},
		method : 'get',
		dataType:"json",
		success: function(data){
			$('#managerForm [name = id]').val(data.id);
			$('#managerForm [name = name]').val(data.name);
			$('#managerForm [name = state]').val(data.state);
			$('#managerForm [name = startTime]').val(dateFormat(new Date(data.startTime), 'yyyy-MM-dd'));
			$('#managerForm [name = endTime]').val(dateFormat(new Date(data.endTime), 'yyyy-MM-dd'));
		}
	});
}

/**
 * 列表填充
 * 
 * @param Manager
 * @returns
 */
function fillManagerData(manager) {
	
	var $tr = $('<tr></tr>');
	var $td1 = $('<td style="text-align: center;"><input no="'+manager.id+'" type="checkbox" /></td>');
	var $td2 = $('<td>' + manager.id + '</td>');
	var $td3 = $('<td>' + manager.name + '</td>');
	var $td7 = $('<td>' + manager.state + '</td>');
	var $td8 = $('<td>' + dateFormat(new Date(manager.startTime), 'yyyy-MM-dd') + '</td>');
	var $td6 = $('<td>' + dateFormat(new Date(manager.endTime), 'yyyy-MM-dd') + '</td>');
	var $td4 = $('<td><a thisId="'+manager.id+'" href="#">编辑</a></td>');
	var $td5 = $('<td class="hide" name = "id">' + manager.id + '</td>');
	return $tr.append($td1).append($td3).append($td7).append($td8).append($td6).append($td4).append($td5);
}

/**
 * 清空表单
 */
function clearManagerFormFunc() {
	$('#managerForm [name = id]').val('');
	$('#managerForm [name = name]').val('');
	$('#managerForm [name = state]').val('');
	$('#managerForm [name = startTime]').val('');
	$('#managerForm [name = endTime]').val('');
}

/**
 * 关键字查询事件
 * @param index
 */
function managerSeach(index) {
	$('.btn-primary').unbind().click(function () {
		keyword = $('input[name=keyword]').val();
		requestManager(index,keyword);
	});
}

function pageSeach(index) {
	keyword = $('input[name=keyword]').val();
	if(keyword == null){
		keyword = null;
	}
	requestManager(index,keyword);
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
		+ $dateStr + " " + date.getHours() + ":" + date.getMinutes();
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