$(function() {
	// 加载课程计划下拉列表
	loadCourseList();
	// 选择课程
	selectCourse();
});

/**
 * 绑定课程计划事件
 */
function planHandler($courseId) {
	// 保存课程计划事件
	savePlanHandler($courseId);
	// 删除课程计划事件
	delPlanHandler();
}

/**
 * 请求课程计划列表
 */
function requestCousePlan(courseId) {
	$.get('coursePlan/'+ courseId +'/getPlanList',function(data){
		fillPlanList(data);
	});
}

/**
 * 选择课程
 */
function selectCourse() {
	$('#planSelect').unbind().change(function() {
		var $courseId = $(this).val();
		requestCousePlan($courseId);
		// 课程计划事件绑定
		planHandler($courseId);
	});
}

function getCourseId(courseId) {
	//alert(courseId);
	return courseId;
}

/**
 * 加载课程计划下拉列表
 */
function loadCourseList() {
	// 请求计划列表
	$.ajax({
		url : 'course/getCourse',
		success : function(data) {
			// 先清空select下拉列表
			$('#planSelect').html('');
			if (data) {
				// 如果有课程
				$('#planSelect').append(fillPlanSelect(data));
				requestCousePlan($('#planSelect option').val());
			} else {
				// 如果没有课程
				$('#planSelect').append(//
				$('<option selected>--- 请选择课程 ---</option>'));
			}
		}
	});
}

function fillPlanSelect(data) {
	var $optionArr = new Array();
	// 遍历计划数组
	$.each(data,//
	function(index, plan) {
		var $option;
		if (index == 0) {
			$option = $('<option selected value=' + plan.courseId + '>' + plan.courseName
					+ '</option>')
		} else {
			$option = $('<option value=' + plan.courseId + '>' + plan.courseName
					+ '</option>')
					
		}
		$optionArr.push($option);
	});
	return $optionArr;
}

/**
 * 保存课程计划事件
 */
function savePlanHandler($courseId) {
	$('#savePlan').unbind().click(function () {
		var $formParams = getParams($('#coursePlanForm').serializeArray());
		var unit = $('#coursePlanForm select[name = unit]').val();
		if ($('#coursePlanForm .hide input[name=id]').val()) {
			if(confirm("确认更新吗")){
				updatePlan($formParams);
			} 
		} else {
			if(confirm("确认增加吗")){
				savePlan($formParams,$courseId);
			}
		}
	});
}

/**
 * 删除课程计划事件
 */
function delPlanHandler() {
	
}

/**
 * 更新计划
 */
function updatePlan() {
	
}

/**
 * 保存计划
 */
function savePlan(formParams,courseId) {
//	var courseId = $("#planSelect option").val();
	//console.log(courseId);
	$.ajax({
		url : "coursePlan/" + courseId +"/saveCoursePlan",
		method : 'post',
		data : formParams,
		dataType : 'json',
		success : function(data) {
			if (data) {
				alert('保存成功');
				requestCousePlan(courseId);
			}
		},
		error:function(){
			alert('服务器异常，保存失败');
		}
	});
}

function fillPlanList(data) {
	// 1.清空列表
	$('#coursePlanTbody').html('');
	// 2.填充数据
	$.each(data,function(index,plan){
		$('#coursePlanTbody').append(fillPlanData(plan));
	});
	// 3.清空列表事件
	clearPlanFormFunc();
	// 4.编辑新闻事件
	editPlanForm();
	// 5.复选框事件
	// 5-1.标题复选框事件
	planTheadChecboxFunc();
	// 5-2.列表复选框事件
	planTbodyChecboxFunc();
}

function fillPlanData(plan) {
	var $tr = $('<tr></tr>');
	var $td1 = $('<td style="text-align: center;"><input type="checkbox" /></td>');
	var $td2 = $('<td>' + plan.unit + '</td>');
	var $td3 = $('<td>' + plan.content + '</td>');
	var $td4 = $('<td><a href="#">编辑</a></td>');
	var $td5 = $('<td class="hide" name = "id">' + plan.id + '</td>');
	return $tr.append($td1).append($td2).append($td3).append($td4).append($td5);
}

function clearPlanFormFunc() {
	$('#coursePlanForm select[name = unit]').val('');
	$('#coursePlanForm input[name = content]').val('');
	$('#coursePlanForm .hide input[name = id]').val('');
}

function editPlanForm() {
	$('#coursePlanForm a').unbind().click(function (e) {
		// 取消默认事件
		e.preventDefault();
		// 把改行数据填充到form表单中
		loadTrPlanForm($(this));
	});
}

function loadTrPlanForm(that) {
	var $tdArr = that.parent().siblings();
	$('#coursePlanForm select[name = unit]').val($tdArr.eq(1).text());
	$('#coursePlanForm input[name = content]').val($tdArr.eq(2).text());
	$('#coursePlanForm input[name = id]').val($tdArr.eq(3).text());
}

function planTheadChecboxFunc() {
	$('#coursePlanThead input[type=checkbox]').eq(0).unbind().change(function() {
		if($(this).is(':checked')){
			// 标题复选框选中，数据行复选框全部选中
			$('#coursePlanTbody input[type=checkbox]').prop('checked',true);
		} else {
			// 标题复选框选中，数据行复选框全部取消选中
			$('#coursePlanTbody input[type=checkbox]').prop('checked',false);
		}
	});
}

/**
 * 数据行复选框事件
 */
function planTbodyChecboxFunc() {
	$('#coursePlanTbody input[type=checkbox]').unbind().change(function() {
		if(isChoosedAll()){
			// 全选，标题复选框选中
			$('#coursePlanThead input[type=checkbox]').eq(0).prop('checked',true);
		} else {
			// 未全选，标题复选框未选中
			$('#coursePlanThead input[type=checkbox]').eq(0).prop('checked',false);
		}
	});
}

function isChoosedAll() {
	// 获得tbody中的行数
	var $rows = $('#coursePlanTbody tr').length;
	// 当前选中的行数
	var $count = 0;
	$.each($('#coursePlanTbody input[type=checkbox]'),function(index,checkbox){
		if($(checkbox).is(':checked')){
			$count++;
		}
	});
	if($rows == $count ){
		return true;
	}
	return false;
}