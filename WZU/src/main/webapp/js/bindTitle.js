$(function(){
	var index;
	var keyword;
	var courseId;
	//绑定事件
	Handler();
	
	//导入js
	$.getScript('js/choice.js', function(data, textStatus, jqxhr) {
		if (jqxhr.status == '200')
			console.log('导入js/choice.js成功！');
		else
			console.log('导入js/choice.js失败！');
	});
	
	//加载课程下拉列表
	loadCourseList();
	
	//请求列表数据
	request(index,keyword);
	
	$('#screen-select').unbind().click(function(e){
		// 取消默认事件
		e.preventDefault();
		// 请求消息与用户数据事件
		request(index,keyword);
	});
});

/**
 * 新增，修改，删除
 */
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

function courseDetail(){
	var courseNo = $('input[name=courseNo]').val();
	$.ajax({
		url:"findCourseDetailByNo",
		data:{courseNo:courseNo},
		datatype:"json",
		success: function(data){
			document.getElementById('courseNo').innerHTML=data.courseNo;
			document.getElementById('courseName').innerHTML=data.courseName;
			document.getElementById('courseIntroduce').innerHTML=data.introduce;
			document.getElementById('courseOutline').innerHTML=data.outline;
			document.getElementById('remark').innerHTML=data.remark;
		}
	});
}

/**
 * 填充select标签的option
 * @param data
 * @returns {Array}
 */
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

//保存主标题
function saveFunc(){
	$("#save").unbind().click(function(){
		var id = $("#Form [name=id]").val();
		var courseNo = $("#Form [name=courseNo]").val();
		var name = $("#Form [name=name]").val();
		if(!courseNo){
			alert('请输入课程编号!');
			return false;
		}
		if(!name){
			alert('请输入主标题');
			return false;
		}
		
		var formData = new FormData();
		formData.append("id",id);
		//将课程编号作为课程ID
		formData.append("courseId", courseNo);
		formData.append("name", name);
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
		url:"courseVideo/saveParentName",
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
				request();
				//清空编辑框
				clearFormFunc();
			}else{
				alert('保存失败');
			}
		},
		error:function(){
			alert('新增失败！服务器发生异常，请联系管理员！');
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
	var courseName = $('#screen-courseName').val();
	var title = $('#screen-title').val();
	$.ajax({
		url : 'courseVideo/'+ index +'/getParentNamePageModel',
		method : 'post',
		data : {
			"keyword" : keyword,
			"courseName" : courseName,
			"title" : title
		},
		dataType : 'json',
		success : function(data) {
			fillList(data);
		}
	});
}

/**
 * 修改
 * @param formData
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
				request();
				//清空编辑框
				clearFormFunc();
			}
		},
		error:function(){
			alert('服务器异常，更新失败');
		}
	});
}
/**
 * 删除确认
 */
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

/**
 * 删除
 */
function deleteHandler() {
	var $idArr = [];
	
	$.each($('.fill tbody input[type=checkbox]'),function(index,checkbox){
		if($(checkbox).is(':checked')){
			$idArr.push($(checkbox).parent().siblings().eq(3).text());
		}
	});
	//console.log($idArr);
	$.ajax({
		url : "courseVideo/deleteCourseVideo",
		method : 'post',
		data : {
			ids : $idArr
		},
		dataType : 'json',
		success : function(data) {
			if (data) {
				alert('删除成功！');
				// 重新加载列表
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
	$.each($Data,function(index,courseVideoVO){
		$('.fill tbody').append(fillData(courseVideoVO));
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
	// 每次刷新列表时取消全选框选中
	$('#selectAll').prop('checked',false); 
}

/**
 * 列表填充
 * 
 * @param courseVideoVO
 * @returns
 */
function fillData(courseVideoVO) {
	var $tr = $('<tr></tr>');
	var $td1 = $('<td style="text-align: center;"><input type="checkbox" /></td>');
	var $td2 = $('<td>' + courseVideoVO.courseName + '</td>');
	var $td3 = $('<td>' + courseVideoVO.parentName + '</td>');
	var $td4 = $('<td><a href="#" class="editNews">编辑</a></td>');
	var $td5 = $('<td class="hide" name = "id">' + courseVideoVO.id + '</td>');
	return $tr.append($td1).append($td2).append($td3).append($td4).append($td5);
}

/**
 * 清空列表事件
 */
function clearFormFunc() {
	$('#Form input[name = courseNo]').val('');
	$('#Form input[name = name]').val('');
	$('#courseSelect').val('0');
	// 重新加载列表
	request();
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

/**
 * 将信息填充到编辑框
 * @param that
 */
function loadTrForm(that) {
	var $tdArr = that.parent().siblings();
	//console.log($tdArr.eq(3).text()); //id
	var id = $tdArr.eq(3).text();
	$.ajax({
		url:"findCourseVideoById",
		data:{id:id},
		datatype:"json",
		success: function(data){
			console.log(data);
			$('#Form input[name = courseNo]').val(data.courseNo);
			$('#Form input[name = name]').val(data.courseName);
			$("#Form input[name = id]").val(data.id);
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