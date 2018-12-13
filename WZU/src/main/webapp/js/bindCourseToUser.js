$(function(){
	var index;
	var keyword;
	//绑定事件
	Handler();
	
	//导入js
	$.getScript('js/choice.js', function(data, textStatus, jqxhr) {
		if (jqxhr.status == '200')
			console.log('导入js/choice.js成功！');
		else
			console.log('导入js/choice.js失败！');
	});
	//请求列表数据
	request(index,keyword);
	
	$('#screen-select').unbind().click(function(e){
		// 取消默认事件
		e.preventDefault();
		// 请求消息与用户数据事件
		request(index,keyword);
	});
});

function Handler(){
	//保存
	saveFunc();
	//删除
	deleteFunc();
}

/**
 * 加载课程下拉列表
 */
function loadCourseList() {
	$.ajax({
		url : 'course/getCourse',
		success : function(data) {
			// 先清空select下拉列表
			$('#courseSelect').html('');
			if (data) {
				$a = $('<option selected value = "0">请选择课程</option>')
				$('#courseSelect').append($a).append(fillCourseSelect(data));
			}
		}
	});
}
function fillCourseSelect(data) {
	var $optionArr = new Array();
	// 遍历数组
	$.each(data,function(index, course) {
		var $option;
			$option = $('<option value=' + course.id + '>' + course.courseName
					+ '</option>')
		$optionArr.push($option);
	});
	return $optionArr;
}

//保存关系
function saveFunc(){
	$("#save").unbind().click(function(){
		var formData = new FormData();
		formData.append("id",$("#Form [name=id]").val());
		//先将编号作为ID
		formData.append("courseId", $("#Form [name=courseNo]").val());
		formData.append("userId", $("#Form [name=userId]").val());
		if($("#Form .hide input[name=id]").val()){
			if(confirm("确定更新吗")){
				update(formData);
			}
		}else{
			if(confirm("确定增加吗")){
				save(formData);
			}
		}
	});
}

/**
 * 提交数据，并请求数据填充到页面
 * 
 * @param formData
 */
function save(formData){
	$.ajax({
		url:"courseAndUser/saveCourseAndUser",
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
				clearFormFunc();
				request();
			}else{
				alert('保存失败');
			}
		},
		error:function(){
			alert('保存失败，请填写完整信息！');
		}
		
		
	});
}

/**
 * 请求列表数据
 */
function request(index,keyword) {
	if(index == null || index == 0){
		index = 1;
	}
	if(keyword == null){
		keyword = null;
	}
	var courseNo = $('#screen-courseNo').val();
	var userId = $('#screen-userId').val();
	console.log('courseNo',courseNo ,'。','userId',userId);
	$.ajax({
		url : 'courseAndUser/'+ index +'/getUserNamePageModel',
		method : 'post',
		data : {
			"courseNo" : courseNo,
			"userId" : userId,
		},
		dataType : 'json',
		success : function(data) {
			if(data.recordCount == 0 || data.recordCount == undefined){
				alert('没有此课程，请检查课程编号是否正确!!!');
			}else{
				fillList(data);
			}
		}
	});
}

/**
 * 更新
 */
function update(formData) {
	var $id = $('#Form .hide input[name=id]').val();
	$.ajax({
		url : "courseVideo/updateParentName",
		method : 'post',
		data : formData,
		// 告诉jQuery不要去处理发送的数据,用来序列化data
		processData : false,
		// 告诉jQuery不要去设置Content-Type请求头
		contentType : false,
		success : function(data) {
			if (data) {
				alert('更新成功');
				clearFormFunc();
				request();
			}
		},
		error:function(){
			alert('服务器异常，保存失败');
		}
	});
}


function deleteFunc(){
	$("#delete").unbind().click(function(){
		if(isChoosedAny()){
			if(confirm('确认删除吗?')){
				deleteHandler();
			}
		}else{
			alert("请至少选择一行");
		}
		
	});
}

function deleteHandler() {
	var $idArr = [];
	
	$.each($('.fill tbody input[type=checkbox]'),function(index,checkbox){
		if($(checkbox).is(':checked')){
			$idArr.push($(checkbox).parent().siblings().eq(3).text());
		}
	});
	//console.log($idArr);
	$.ajax({
		url : "courseAndUser/deleteCourseAndUser",
		method : 'post',
		data : {
			ids : $idArr
		},
		dataType : 'json',
		success : function(data) {
			if (data) {
				alert('删除成功');
				// 重新加载列表
				clearFormFunc();
				request();
			}
		},
		error : function(){
			alert('服务器异常，请联系管理员');
		}
	});
}

/**
 * 填充列表事件
 * 
 * @param pageModel
 */
function fillList(pageModel) {
	var $Data = pageModel.list;
	// 1.清空列表
	$('.fill tbody').html('');
	// 2.填充数据
	$.each($Data,function(index,courseAndUserVO){
		$('.fill tbody').append(fillData(courseAndUserVO));
	});
	// 清空分页栏
	$('#main #pagination').html('');
	// 加载分页栏
	fillPage(pageModel);
	// 分页栏点击事件
	onclickPage(pageModel);
	// 4.编辑事件
	editForm();
	// 5.复选框事件
	// 5-1.标题复选框事件
	theadChecboxFunc();
	// 5-2.列表复选框事件
	tbodyChecboxFunc();
}

/**
 * 列表填充
 * 
 * @param courseVideoVO
 * @returns
 */
function fillData(courseAndUserVO) {
	var $tr = $('<tr></tr>');
	var $td1 = $('<td style="text-align: center;"><input type="checkbox" /></td>');
	var $td2 = $('<td>' + courseAndUserVO.course + '</td>');
	var $td3 = $('<td>' + courseAndUserVO.userId + '</td>');
	var $td4 = $('<td>' + courseAndUserVO.user + '</td>')
//	var $td4 = $('<td><a href="#" class="editNews">编辑</a></td>');
	var $td5 = $('<td class="hide" name = "id">' + courseAndUserVO.id + '</td>');
	return $tr.append($td1).append($td2).append($td3).append($td4).append($td5);
}

/**
 * 清空列表事件
 */
function clearFormFunc() {
	$('#Form .hide input[name = id]').val('');
	$('#Form input[name = name]').val('');
	$('#courseSelect').val('0');
}

/**
 * 编辑课程事件
 */
function editForm() {
	$('.fill tbody a').unbind().click(function (e) {
		// 取消默认事件
		e.preventDefault();
		// 把改行数据填充到form表单中
		loadTrForm($(this));
	});
}
function loadTrForm(that) {
	var $tdArr = that.parent().siblings();
	//console.log($tdArr.eq(3).text()); //id
	var id = $tdArr.eq(3).text();
	$.ajax({
		url:"findCourseVideoById",
		data:{id:id},
		datatype:"json",
		success: function(data){
			$('#Form input[name = id]').val(data.id);
			$('#Form input[name = name]').val(data.name);
			$("#courseSelect").val(data.courseId);
		}
	});
}

/**
 * 关键字查询事件
 * @param index
 */
function newsSeach(index) {
	$('.btn-primary').unbind().click(function () {
		courseId = $("#courseSelect option:selected").val();
		keyword = $('input[name=keyword]').val();
		//console.log(keyword);
		request(index,keyword,courseId);
	});
}

function pageSeach(index) {
	keyword = $('input[name=keyword]').val();
	courseId = $("#courseSelect option:selected").val();
	if(keyword == null){
		keyword = null;
	}
	request(index,keyword,courseId);
}

function onclickPage(data) {
	// 当前页
	var $pageIndex = data.pageIndex;
	// 总页数
	var $totalSize = data.totalSize;
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