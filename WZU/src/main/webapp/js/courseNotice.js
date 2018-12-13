$(function(){
	var index;
	var keyword;
	var courseId;
	//绑定课程公告事件
	CourseNoticeHandler();
	
	$("#courseSelect").change(function(){
		courseId = $("#courseSelect option:selected").val();
		//console.log(courseId);
		requestCourseNotice(index,keyword,courseId);
	});
	
	$.getScript('js/choice.js', function(data, textStatus, jqxhr) {
		/*if (jqxhr.status == '200')
			console.log('导入js/choice.js成功！');
		else
			console.log('导入js/choice.js失败！');*/
	});
	
	// 加载课程下拉列表
	loadCourseList();
	
	//超出部分显示省略号 
	omit1();
	
	//请求列表数据
	requestCourseNotice(index,keyword);
	
	$('#screen-select').unbind().click(function(e){
		// 取消默认事件
		e.preventDefault();
		// 请求消息与用户数据事件
		requestCourseNotice(index,keyword);
	});

});

function CourseNoticeHandler(){
	//保存
	saveCourseNoticeFunc();
	//删除
	delCourseNoticeFunc();
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

//保存课程公告
function saveCourseNoticeFunc(){
	$('#saveCourseNotice').unbind().click(function(){
		courseDetail();
		if(!$('#courseNoticeForm .hide input[name=courseId]').val()){
			return;
		}
		var formData = new FormData();
		formData.append("id", $("#courseNoticeForm input[name=id]").val());
		formData.append("title", $("#courseNoticeForm input[name=title]").val());
		formData.append("content", $("#courseNoticeForm textarea[name=content]").val());
		formData.append("courseId", $('#courseNoticeForm input[name = courseId]').val());
		if ($('#courseNoticeForm .hide input[name=id]').val()) {
			if(confirm("确认更新吗")){
				update(formData);
			} 
		} else {
			if(confirm("确认增加吗")){
				save(formData);
			}
		}
	})
	
}

/**
 * 提交数据，并请求数据填充到页面
 * 
 * @param formData
 */
function save(formData) {
	$.ajax({
		url : "notice/saveNotice",
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
				clearCourseNoticeFormFunc();
				requestCourseNotice();
			}
		},
		error:function(){
			alert('保存失败，请填写完整信息！');
		}
	});
}

function update(formData) {
	var $id = $('#courseNoticeForm .hide input[name=id]').val();
	$.ajax({
		url : "notice/updateNotice",
		method : 'post',
		data : formData,
		// 告诉jQuery不要去处理发送的数据,用来序列化data
		processData : false,
		// 告诉jQuery不要去设置Content-Type请求头
		contentType : false,
		success : function(data) {
			if (data) {
				alert('更新成功');
				clearCourseNoticeFormFunc();
				requestCourseNotice();
			}
		},
		error:function(){
			alert('服务器异常，保存失败');
		}
	});
}

function delCourseNoticeFunc() {
	$('#delCourseNotice').unbind().click(function () {
		if(isChoosedAny()){
			if (confirm('确认删除吗?')){
				delCourseNoticeHandler();
			}
		} else {
			alert("请选择要删除的公告");
		}
	});
}

function delCourseNoticeHandler() {
	var $idArr = [];
	
	$.each($('.fill tbody input[type=checkbox]'),function(index,checkbox){
		if($(checkbox).is(':checked')){
			$idArr.push($(checkbox).parent().siblings().eq(5).text());
		}
	});
	//console.log($idArr);
	$.ajax({
		url : "notice/deleteNotice",
		method : 'post',
		data : {
			ids : $idArr
		},
		dataType : 'json',
		success : function(data) {
			if (data) {
				// 重新加载列表
				requestCourseNotice();
			}
		},
		error : function(){
			alert('服务器异常，请联系管理员');
		}
	});
}

/**
 * 请求列表数据
 */
function requestCourseNotice(index,keyword) {
	if(index == null || index == 0){
		index = 1;
	}
	if(keyword == null){
		keyword = null;
	}
	var courseName = $('#screen-courseName').val();
	var title = $('#screen-title').val();
	var content = $('#screen-content').val();
	console.log("courseName: ",courseName);
	$.ajax({
		url : 'notice/'+ index +'/getNoticePageModel',
		method : 'post',
		data : {
			"courseName" : courseName,
			"title" : title,
			"content" : content
		},
		dataType : 'json',
		success : function(data) {
			fillCourseNoticeList(data);
		}
	});
}

function courseDetail(){
	var courseNo = $('input[name=courseNo]').val();
	if(courseNo == null || courseNo == "" ){
		alert('请输入课程编号!!!');
		return;
	}
	$.ajax({
		url:"findCourseDetailByNo",
		data:{courseNo:courseNo},
		async: false,
		datatype:"json",
		success: function(data){
			$('#courseNoticeForm input[name = courseId]').val(data.id);
			document.getElementById('courseNo').innerHTML=data.courseNo;
			document.getElementById('courseName').innerHTML=data.courseName;
			document.getElementById('courseIntroduce').innerHTML=data.introduce;
			document.getElementById('courseOutline').innerHTML=data.outline;
			document.getElementById('remark').innerHTML=data.remark;
		}
	});
}


/**
 * 填充列表事件
 * 
 * @param data
 */
function fillCourseNoticeList(data) {
	var $Data = data.list;
	// 1.清空列表
	$('.fill tbody').html('');
	// 2.填充数据
	$.each($Data,function(index,noticeVO){
		$('.fill tbody').append(fillCourseNoticeData(noticeVO));
	});
	// 清空分页栏
	$('#main #pagination').html('');
	// 加载分页栏
	fillPage(data);
	// 分页栏点击事件
	onclickPage(data);
/*	// 3.清空列表事件
	clearCourseNoticeFormFunc();*/
	// 4.编辑事件
	editCourseNoticeForm();
	// 5.复选框事件
	// 5-1.标题复选框事件
	theadChecboxFunc();
	// 5-2.列表复选框事件
	tbodyChecboxFunc();
	
	//超出部分显示省略号 
	omit1();
	
}

/**
 * 列表填充
 * 
 * @param news
 * @returns
 */
function fillCourseNoticeData(noticeVO) {
	var $tr = $('<tr></tr>');
	var $td1 = $('<td style="text-align: center;"><input type="checkbox" /></td>');
	var $td2 = $('<td>' + noticeVO.courseName + '</td>');
	var $td3 = $('<td>' + noticeVO.title + '</td>');
	var $td4 = $('<td class="contentOmit">' + noticeVO.content + '</td>');
	var $td5 = $('<td>' + dateFormat(new Date(noticeVO.releaseTime), 'yyyy-MM-dd') + '</td>');
	var $td6 = $('<td><a href="#" class="editNews">编辑</a></td>');
	var $td7 = $('<td class="hide" name = "id">' + noticeVO.id + '</td>');
	var $td8 = $('<td class="hide" name = "no">' + noticeVO.courseNo + '</td>');
	return $tr.append($td1).append($td2).append($td3).append($td4).append($td5).append($td6).append($td7).append($td8);
}

function omit1(){
	//超出部分显示省略号 
	   $(".contentOmit").each(function() {
			var maxwidth = 10;
			if($(this).html().length > maxwidth) {
				$(this).html($(this).text().substring(0, maxwidth));
				$(this).text($(this).html() +'...');
			}
		});
}


/**
 * 清空列表事件
 */
function clearCourseNoticeFormFunc() {
	$('input[name = courseNo]').val('');
	$('input[name = id]').val('');
	$('input[name = courseId]').val('');
	$('#courseNoticeForm input[name = title]').val('');
	$('#courseNoticeForm textarea[name = content]').val('');
}

/**
 * 编辑课程事件
 */
function editCourseNoticeForm() {
	$('.fill tbody a').unbind().click(function (e) {
		// 取消默认事件
		e.preventDefault();
		// 把改行数据填充到form表单中
		loadTrCourseNoticeForm($(this));
	});
}
function loadTrCourseNoticeForm(that) {
	var $tdArr = that.parent().siblings();
	//console.log($tdArr.eq(5).text()); //id
	var id = $tdArr.eq(5).text();
	$.ajax({
		url:"findNoticeById",
		data:{id:id},
		datatype:"json",
		success: function(data){
			console.log(data);
			$('#courseNoticeForm input[name = id]').val(data.id);
			$('#courseNoticeForm input[name = courseId]').val(data.courseId);
			$('#courseNoticeForm input[name = courseNo]').val($tdArr.eq(6).text());
			$('#courseNoticeForm input[name = title]').val(data.title);
			$('#courseNoticeForm textarea[name = content]').val(data.content);
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
		requestCourseNotice(index,keyword,courseId);
	});
}

function pageSeach(index) {
	keyword = $('input[name=keyword]').val();
	courseId = $("#courseSelect option:selected").val();
	if(keyword == null){
		keyword = null;
	}
	requestCourseNotice(index,keyword,courseId);
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