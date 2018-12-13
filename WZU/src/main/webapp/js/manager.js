$(function() {
	var index;
	var keyword;
	// 绑定管理员事件
	managerHandler();
	// 请求管理员数据事件
	requestManager();
	$.getScript('js/choice.js', function(data, textStatus, jqxhr) {
		/*if (jqxhr.status == '200')
			console.log('导入js/choice.js成功！');
		else
			console.log('导入js/choice.js失败！');*/
	});
	
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
		var $formParams = getParams($('#managerForm').serializeArray());
		if ($('#managerForm .hide input[name=id]').val()) {
			if(confirm("确认更新吗")){
				updateManager($formParams);
			} 
		} else {
			if(confirm("确认增加吗")){
				saveManager($formParams);
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
		url : "manager/saveManager",
		method : 'post',
		data : formParams,
		dataType : 'json',
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
		url : 'manager/' + index + '/getManagerPageModel',
		method : 'post',
		data : {
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
		url : "manager/updateManager",
		method : 'post',
		data : formData,
/*		// 告诉jQuery不要去处理发送的数据,用来序列化data
		processData : false,
		// 告诉jQuery不要去设置Content-Type请求头
		contentType : false,*/
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
			if (confirm('确认??')){
				delManagerHandler();
			}
		} else {
			alert("请选择要删除的管理员信息");
		}
	});
}

function delManagerHandler() {
	var $idArr = [];
	
	$.each($('.fill tbody input[type=checkbox]'),function(index,checkbox){
		if($(checkbox).is(':checked')){
			$idArr.push($(checkbox).parent().siblings().eq(3).text());
		}
	});
	//console.log($idArr);
	$.ajax({
		url : "manager/deleteManager",
		method : 'post',
		data : {
			ids : $idArr
		},
		dataType : 'json',
		success : function(data) {
			if (data) {
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
	//详细介绍显示省略
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
	var $tdArr = that.parent().siblings();
	//console.log($tdArr.eq(3).text()); //id
	var id = $tdArr.eq(3).text();
	$.ajax({
		url:"findManagerById",
		data:{id:id},
		dataType:"json",
		success: function(data){
			$('#managerForm input[name = id]').val(data.id);
			$('#managerForm input[name = username]').val(data.username);
			$('#managerForm input[name = password]').val(data.password);
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
	var $td1 = $('<td style="text-align: center;"><input type="checkbox" /></td>');
	var $td2 = $('<td>' + manager.username + '</td>');
	var $td3 = $('<td>' + manager.password + '</td>');
	var $td4 = $('<td><a href="#">编辑</a></td>');
	var $td5 = $('<td class="hide" name = "id">' + manager.id + '</td>');
	return $tr.append($td1).append($td2).append($td3).append($td4).append($td5);
}

/**
 * 清空表单
 */
function clearManagerFormFunc() {
	$('#managerForm input[name = id]').val('');
	$('#managerForm input[name = username]').val('');
	$('#managerForm input[name = password]').val('');
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
