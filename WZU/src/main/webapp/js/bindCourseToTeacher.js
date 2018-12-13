$(function() {
	var index;
	var keyword;
	var teacherId;
	var courseId;
	
	$.getScript('js/choice.js', function(data, textStatus, jqxhr) {
		if (jqxhr.status == '200')
			console.log('导入js/choice.js成功！');
		else
			console.log('导入js/choice.js失败！');
	});
	
	//请求列表数据
	requestCourseAndTeacher(index,keyword);
	
	//保存
	saveCourseToTeacher();
	//删除
	delCourseToTeacher();
	
	$('#screen-select').unbind().click(function(e){
		// 取消默认事件
		e.preventDefault();
		// 请求消息与用户数据事件
		requestCourseAndTeacher(index,keyword);
	});
});

//
//function fillCourseSelect(data) {
//	var $optionArr = new Array();
//	// 遍历数组
//	$.each(data,function(index, course) {
//		var $option;
//			$option = $('<option value=' + course.id + '>' + course.courseName
//					+ '</option>')
//		$optionArr.push($option);
//	});
//	return $optionArr;
//}
//
//function fillTeacherSelect(data) {
//	var $optionArr = new Array();
//	// 遍历数组
//	$.each(data,function(index, teacher) {
//		var $option;
//			$option = $('<option value=' + teacher.id + '>' + teacher.teacherName
//					+ '</option>')
//		$optionArr.push($option);
//	});
//	return $optionArr;
//}

function saveCourseToTeacher(){
	$('#saveCourseToTeacher').unbind().click(function(){
		var teacherNo = $("input[name = teacherNo]").val();
		var courseNo = $("input[name = courseNo]").val();
		$.ajax({
			url:"saveCourseToTeacher",
			data: {"courseNo":courseNo,"teacherNo":teacherNo},
			success : function(data){
				if(data.status){
					alert("添加成功");
					clearCourseToTeacherFormFunc();
					requestCourseAndTeacher();
				}else{
					alert("已添加过");
				}
			}
		});
	});
}
/**
 * 请求列表数据
 */
function requestCourseAndTeacher(index,keyword) {
	if(index == null || index == 0){
		index = 1;
	}
	if(keyword == null){
		keyword = null;
	}
	var teacherNo = $('#screen-teacherNo').val();
	var teacherName = $('#screen-teacherName').val();
	var courseName = $('#screen-courseName').val();
	$.ajax({
		url : 'CourseAndTeacher/'+ index +'/getCourseAndTeacherPageModel',
		method : 'post',
		data : {
			"teacherNo" : teacherNo,
			"teacherName" : teacherName,
			"courseName" : courseName,
		},
		dataType : 'json',
		success : function(data) {
			fillCourseAndTeacherList(data);
		}
	});
}

/**
 * 填充列表事件
 * 
 * @param courseAndTeacherData
 */
function fillCourseAndTeacherList(data) {
	var $Data = data.list;
	// 1.清空列表
	$('.fill tbody').html('');
	// 2.填充数据
	$.each($Data,function(index,courseAndTeacher){
		$('.fill tbody').append(fillCourseAndTeacherData(courseAndTeacher));
	});
	// 清空分页栏
	$('#main #pagination').html('');
	// 加载分页栏
	fillPage(data);
	// 分页栏点击事件
	onclickPage(data);
/*	// 3.清空列表事件
	clearCourseToTeacherFormFunc();*/
	// 4.编辑事件
/*	editCourseAndTeacherForm();*/
	// 5.复选框事件
	// 5-1.标题复选框事件
	theadChecboxFunc();
	// 5-2.列表复选框事件
	tbodyChecboxFunc();
}

/**
 * 列表填充
 * 
 * @param news
 * @returns
 */
function fillCourseAndTeacherData(courseAndTeacher) {
	var $tr = $('<tr></tr>');
	var $td1 = $('<td style="text-align: center;"><input type="checkbox" value="'+courseAndTeacher.id+'"/></td>');
	var $td2 = $('<td class="content">' + courseAndTeacher.teacher.teacherName + '</td>');
	var $td3 = $('<td class="content">' + courseAndTeacher.teacher.teacherNo + '</td>');
	var $td4 = $('<td class="content">' + courseAndTeacher.course.courseName + '</td>');
/*	var $td7 = $('<td><a href="#" class="editNews">编辑</a></td>');*/
/*	var $td8 = $('<td class="hide" name = "id">' + courseAndTeacherVO.id + '</td>');*/
	return $tr.append($td1).append($td2).append($td3).append($td4);
}


function pageSeach(index) {
	teacherId = $("#teacherSelect option:selected").val();
	courseId = $("#courseSelect option:selected").val();
	keyword = $('input[name=keyword]').val();
	//console.log(teacherId+"----"+courseId+"-------"+index+"----------123");
	if(keyword == null){
		keyword = null;
	}
	requestCourseAndTeacher(index,keyword,teacherId,courseId);
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

/**
 * 清空列表事件
 */
function clearCourseToTeacherFormFunc() {
	$("input[name=teacherNo]").val('');
	$("input[name=courseNo]").val('');
}


function delCourseToTeacher() {
	$('#delCourseToTeacher').unbind().click(function () {
		if(isChoosedAny()){
			if (confirm('确认删除吗?')){
				delCourseToTeacherHandler();
			}
		} else {
			alert("请至少选择一项删除");
		}
	});
}

function delCourseToTeacherHandler() {
	var $idArr = [];
	
	$.each($('.fill tbody input[type=checkbox]'),function(index,checkbox){
		if($(checkbox).is(':checked')){
			$idArr.push($(checkbox).val());
		}
	});
	$.ajax({
		url : "CourseAndTeacher/deleteCourseToTeacher",
		method : 'post',
		data : {
			ids : $idArr
		},
		dataType : 'json',
		success : function(data) {
			if (data) {
				alert("删除成功");
				// 重新加载列表
				clearCourseToTeacherFormFunc();
				requestCourseAndTeacher();
			}
		},
		error : function(){
			alert('服务器异常，请联系管理员');
		}
	});
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