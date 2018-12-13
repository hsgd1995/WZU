$(function() {
	var index;
	var keyword;
	
	//超出部分显示省略号 
	omit();
	
	// 加载课程类型下拉列表
	loadCourseTypeList();
	// 加载课程详情下拉列表
//	loadCourseDetailsList();
	
	// 绑定课程事件
	courseHandler();
	// 请求课程数据事件
	requestCourse();
	echoImg();
	$.getScript('js/choice.js', function(data, textStatus, jqxhr) {
		if (jqxhr.status == '200')
			console.log('导入js/choice.js成功！');
		else
			console.log('导入js/choice.js失败！');
	});
	// 搜索事件
	courseSeach(index);
	
	//鼠标移动过去显示大图
    $(".bimg").hide();
    $(".box img").mousemove(function(e){
        $(".bimg").css({top:e.pageY-86,left:e.pageX}).html('<img src="' + this.src + '" />').show();
    }).mouseout( function(){
    $(".bimg").hide();
  })
	
	$('#close').unbind().click(function() {
		$('.zz').hide();
		$('.editInfo').hide();
	});
    
    $('#screen-select').unbind().click(function(e){
		// 取消默认事件
		e.preventDefault();
		// 请求消息与用户数据事件
		requestCourse();
	});
	
});

/**
 * 加载课程类型列表
 */
function loadCourseTypeList() {
	$.ajax({
		url : 'courseType/getCourseType',
		success : function(data) {
			// 先清空select下拉列表
			$('#courseTypeSelect').html('');
			if (data) {
				$a = $('<option selected value = "0">请选择课程类型</option>')
				$('#courseTypeSelect').append($a).append(fillCourseTypeSelect(data));
			}
		}
	});
}
function fillCourseTypeSelect(data) {
	var $optionArr = new Array();
	// 遍历数组
	$.each(data,function(index, courseType) {
		var $option;
			$option = $('<option value=' + courseType.id + '>' + courseType.typeName
					+ '</option>')
		$optionArr.push($option);
	});
	return $optionArr;
}


///**
// * 加载课程详情列表
// */
//function loadCourseDetailsList() {
//	$.ajax({
//		url : 'courseDetails/getCourseDetails',
//		success : function(data) {
//			// 先清空select下拉列表
//			$('#courseDetailsSelect').html('');
//			if (data) {
//				$a = $('<option selected value = "0">请选择课程详情</option>')
//				$('#courseDetailsSelect').append($a).append(fillCourseDetailsSelect(data));
//			}
//		}
//	});
//}
function fillCourseDetailsSelect(data) {
	var $optionArr = new Array();
	// 遍历数组
	$.each(data,function(index, courseDetails) {
		var $option;
			$option = $('<option value=' + courseDetails.id + '>' + 
					'学时:'+ courseDetails.coursePeriod +
					'周&nbsp;&nbsp;&nbsp;每周消耗:'+ courseDetails.expendTime +
					'小时/周&nbsp;&nbsp;&nbsp;授课语言:'+ courseDetails.courseLanguage + 
					'&nbsp;&nbsp;&nbsp;字幕类型:'+ courseDetails.subtitleType + 
					'&nbsp;&nbsp;&nbsp;图片:<img src="'+courseDetails.coursePic +
					'">&nbsp;&nbsp;&nbsp;备注:'+ courseDetails.remark
					+ '</option>')
		$optionArr.push($option);
	});
	return $optionArr;
}

function courseHandler() {
	// 保存新闻事件
	saveCourseFunc();
	// 更新新闻事件
	delCourseFunc();
}

/**
 * 保存课程事件
 */
function saveCourseFunc() {
	$('#saveCourse').unbind().click(function() {
		/*var $formParams = getParams($('#courseForm').serializeArray());
		var upload = document.getElementById('uploadFile').value;
		var arr = JSON.stringify(upload).split("\\");
		var arr1 = arr[arr.length-1].split("\"");
		$formParams.coursePic = arr1[0];*/
		var formData = new FormData();
		formData.append("id", $("#courseForm [name=id]").val());
		formData.append("courseNo", $("#courseForm [name=courseNo]").val());
		formData.append("courseName", $("#courseForm [name=courseName]").val());
		formData.append("courseTypeId", $("#courseForm [name=courseTypeId]").val());
		formData.append("introduce", $("#courseForm [name=introduce]").val());
		formData.append("outline", $("#courseForm [name=outline]").val());
		formData.append("examine", $("#courseForm [name=examine]").val());
		formData.append("teachingMateril", $("#courseForm [name=teachingMateril]").val());
		formData.append("coursePeriod", $("#courseForm [name=coursePeriod]").val());
		formData.append("expendTime", $("#courseForm [name=expendTime]").val());
		formData.append("courseLanguage", $("#courseForm [name=courseLanguage]").val());
		formData.append("subtitleType", $("#courseForm [name=subtitleType]").val());
		formData.append("file", $("#uploadFile")[0].files[0]);
		formData.append("studyStartTime", $("#courseForm [name=studyStartTime]").val());
		formData.append("studyEndTime", $("#courseForm [name=studyEndTime]").val());
		formData.append("examEndTime", $("#courseForm [name=examEndTime]").val());
		formData.append("orfree", $("#courseForm [name=orfree]").val());
		formData.append("remark", $("#courseForm [name=remark]").val());

		var ss = $("#courseForm [name=studyStartTime]").val();
		if ($('#courseForm .hide input[name=id]').val()) {
			if(confirm("确认更新吗")){
				updateCourse(formData);
			} 
		} else {
			if(confirm("确认增加吗")){
				saveCourse(formData);
			}
		}
	});
}

/**
 * 提交数据，并请求数据填充到页面
 * 
 * @param formParams
 */
function saveCourse(formParams) {
	$.ajax({
		url : "course/saveCourse",
		method : 'post',
		data : formParams,
		dataType : 'json',
		// 告诉jQuery不要去处理发送的数据
		processData : false,
		// 告诉jQuery不要去设置Content-Type请求头
		contentType : false,
		success : function(data) {
			if (data) {
				alert('保存成功');
				requestCourse();
			}
		},
		error:function(){
			alert('保存失败，请填写完整必填信息！');
		}
	});
}

/**
 * 请求数据事件
 */
function requestCourse(index,keyword) {
	if(index == null || index == 0){
		index = 1;
	}
	if(keyword == null){
		keyword = null;
	}
	var courseNo = $('#screen-courseNo').val();
	var courseName = $('#screen-courseName').val();
	$.ajax({
		url : 'course/'+ index +'/getCoursePageModelBackstage',
		method : 'post',
		data : {
			keyword : keyword,
			"courseNo":courseNo,
			"courseName":courseName
			
		},
		dataType : 'json',
		success : function(pageModel) {
			fillCourseList(pageModel);
		}
	});
}

function requestCourseByPage(pageIndex) {
	$.get('course/' + pageIndex + '/getCoursePageModel',function(pageModel){
		fillCourseList(pageModel);
	});
}

function updateCourse(formParams) {
	var $courseId = $('#courseForm .hide input[name=id]').val();
	$.ajax({
		url : "course/updateCourse",
		method : 'post',
		data : formParams,
		processData : false,
		contentType : false,
		success : function(data) {
			if (data) {
				alert('保存成功');
				requestCourse();
			}
		},
		error:function(){
			alert('保存失败，请填写完整必填信息！');
		}
	});
}

function delCourseFunc() {
	$('#delCourse').unbind().click(function () {
		if(isChoosedAny()){
			if (confirm('确认删除吗?')){
				delCourseHandler();
			}
		} else {
			alert("请选择要删除的课程！！！");
		}
	});
}

function delCourseHandler() {
	var $idArr = [];
	$.each($('.fill tbody input[type=checkbox]'),function(index,checkbox){
		if($(checkbox).is(':checked')){
			$idArr.push($(checkbox).parent().siblings().eq(11).text());
		}
	});
	//console.log($idArr);
	$.ajax({
		url : "course/deleteCourse",
		method : 'post',
		data : {
			ids : $idArr
		},
		dataType : 'json',
		success : function(data) {
			if (data) {
				// 重新加载新闻列表
				alert('删除成功！');
				requestCourse();
			}
		},
		error : function(){
			alert('服务器异常，请联系管理员');
		}
	});
}

/**
 * 填充新闻列表事件
 * 
 * @param courseData
 */
function fillCourseList(pageModel) {
	var $courseData = pageModel.list;
	// 1.清空列表
	$('.fill tbody').html('');
	// 2.填充数据
	$.each($courseData,function(index,course){
		var orfree;
		if(course.orfree == 0){
			orfree = '开放课程';
		}if(course.orfree == 1){
			orfree = '收费课程';
		}
		$('.fill tbody').append(fillCourseData(course,orfree));
	});
	// 清空分页栏
	$('#main #pagination').html('');
	// 加载分页栏
	fillPage(pageModel);
	// 分页栏点击事件
	onclickPage(pageModel);
	// 3.清空列表事件
	clearCourseFormFunc();
	// 4.编辑新闻事件
	editCourseForm();
	// 5.复选框事件
	// 5-1.标题复选框事件
	theadChecboxFunc();
	// 5-2.列表复选框事件
	tbodyChecboxFunc();
	//超出部分显示省略号 
	omit();
	//课程详情
	//courseDetail('0');
}

function courseDetail(id){
	$.ajax({
		url:"findCourseDetailById",
		data:{id:id},
		datatype:"json",
		success: function(data){
			document.getElementById('coursePeriod').innerHTML=data.coursePeriod;
			document.getElementById('expendTime').innerHTML=data.expendTime;
			document.getElementById('courseLanguage').innerHTML=data.courseLanguage;
			document.getElementById('subtitleType').innerHTML=data.subtitleType;
			document.getElementById('studyStartTime').innerHTML=dateFormat(new Date(data.studyStartTime), 'yyyy-MM-dd');;
			document.getElementById('studyEndTime').innerHTML=dateFormat(new Date(data.studyEndTime), 'yyyy-MM-dd');
			document.getElementById('examStartTime').innerHTML=dateFormat(new Date(data.examStartTime), 'yyyy-MM-dd');
			document.getElementById('examEndTime').innerHTML=dateFormat(new Date(data.examEndTime), 'yyyy-MM-dd');
			document.getElementById('coursePic').src=data.coursePic;
		}
	});
}

function editCourseForm() {
	$('.edit').unbind().click(function (e) {
		// 取消默认事件
		e.preventDefault();
		// 把改行数据填充到form表单中
		loadTrCourseForm($(this));
	});
}

/**
 * 填充表单事件
 * @param that
 */
function loadTrCourseForm(that) {
	var $tdArr = that.parent().siblings();
	var id = $tdArr.eq(11).text();
	$.ajax({
		url:"findCourseById",
		data:{id:id},
		dataType:"json",
		success: function(data){
			//console.log(data);
			var studyStartTime = dateFormat(new Date(data.studyStartTime), 'yyyy-MM-dd');
			var studyEndTime = dateFormat(new Date(data.studyEndTime), 'yyyy-MM-dd');
			var examStartTime = dateFormat(new Date(data.examStartTime), 'yyyy-MM-dd');
			var examEndTime = dateFormat(new Date(data.examEndTime), 'yyyy-MM-dd');
			$('#courseForm .hide input[name = id]').val(data.id);
			$('#courseForm input[name = courseNo]').val(data.courseNo);
			$('#courseForm input[name = courseName]').val(data.courseName);
			$('#courseForm textarea[name = introduce]').val(data.introduce);
			$('#courseForm textarea[name = outline]').val(data.outline);
			$('#courseForm textarea[name = examine]').val(data.examine);
			$('#courseForm textarea[name = teachingMateril]').val(data.teachingMateril);
			$('#courseForm input[name = coursePeriod]').val(data.coursePeriod);
			$('#courseForm input[name = expendTime]').val(data.expendTime);
			$('#courseForm input[name = courseLanguage]').val(data.courseLanguage);
			$('#courseForm input[name = subtitleType]').val(data.subtitleType);
			$('#img').attr('src',data.coursePic);		//图片
			$('#courseForm input[name = studyStartTime]').val(studyStartTime);
			$('#courseForm input[name = studyEndTime]').val(studyEndTime);
			$('#courseForm input[name = examStartTime]').val(examStartTime);
			$('#courseForm input[name = examEndTime]').val(examEndTime);
			$("#orfree").val(data.orfree);
			$("#courseTypeSelect").val(data.courseType.id);
			//$("#courseDetailsSelect").val(data.courseDetails.id);
			$('#courseForm input[name = remark]').val(data.remark==null?"":data.remark);
		}
	});
/*	$('#courseForm input[name = courseNo]').val($tdArr.eq(1).text());
	$('#courseForm input[name = courseName]').val($tdArr.eq(2).text());
	$('#courseForm textarea[name = introduce]').val($tdArr.eq(3).text());
	$('#courseForm textarea[name = outline]').val($tdArr.eq(4).text());
	$('#courseForm textarea[name = examine]').val($tdArr.eq(5).text());
	$('#courseForm textarea[name = teachingMateril]').val($tdArr.eq(6).text());
	$('#courseForm input[name = studyStartTime]').val($tdArr.eq(7).text());
	$('#courseForm input[name = studyEndTime]').val($tdArr.eq(8).text());
	$('#courseForm input[name = examStartTime]').val($tdArr.eq(9).text());
	$('#courseForm input[name = examEndTime]').val($tdArr.eq(10).text());
	$('#courseForm input[name = remark]').val($tdArr.eq(11).text());
	$('#courseForm .hide input[name = id]').val($tdArr.eq(12).text());*/
}

/**
 * 列表填充
 * 
 * @param course
 * @returns
 */
function fillCourseData(course,orfree) {
	var $tr = $('<tr></tr>');
	var $td1 = $('<td style="text-align: center;"><input type="checkbox" /></td>');
	var $td2 = $('<td>' + course.courseNo + '</td>');
	var $td3 = $('<td>' + course.courseName + '</td>');
	var $td4 = $('<td class="courseOmit">' + course.introduce + '</td>');
	var $td5 = $('<td class="courseOmit">' + course.outline + '</td>');
	var $td6 = $('<td class="courseOmit">' + course.examine + '</td>');
	var $td7 = $('<td class="courseOmit">' + course.teachingMateril + '</td>');
	/*var $td8 = $('<td>'
			+ dateFormat(new Date(course.studyStartTime), 'yyyy-MM-dd')
			+ '</td>');
	var $td9 = $('<td>'
			+ dateFormat(new Date(course.studyEndTime), 'yyyy-MM-dd')
			+ '</td>');
	var $td10 = $('<td>'
			+ dateFormat(new Date(course.examStartTime), 'yyyy-MM-dd')
			+ '</td>');
	var $td11 = $('<td>'
			+ dateFormat(new Date(course.examEndTime), 'yyyy-MM-dd')
			+ '</td>');*/
	var $td12 = $('<td>' + course.courseType.typeName + '</td>');
	//var $td13 = $('<td><a onclick="test()" href="javascript:void(0)" class="details">id:' + course.courseDetails.id + '(点击)</a></td>');
	var $td13 = $('<td align="center"><input type="button" id="aaa" data-toggle="modal" data-target="#myModal" value="点击查看" onclick="courseDetail('+course.id+')"></td>') ;
	var $td14 = $('<td>' + orfree + '</td>');
	var $td15 = $('<td>' + course.remark + '</td>');
	var $td16 = $('<td><a href="#" class="edit">编辑</a></td>');
	var $td17 = $('<td class="hide" name = "id">' + course.id + '</td>');
	return $tr.append($td1).append($td2).append($td3).append($td4).append($td5).append($td6)
	.append($td7).append($td12).append($td13).append($td14).append($td15).append($td16).append($td17);
}


function clearCourseFormFunc() {
	$('#courseForm input[name = courseNo]').val('');
	$('#courseForm input[name = courseName]').val('');
	$('#courseForm textarea[name = introduce]').val('');
	$('#courseForm textarea[name = outline]').val('');
	$('#courseForm textarea[name = examine]').val('');
	$('#courseForm textarea[name = teachingMateril]').val('');
	$('#courseForm input[name = studyStartTime]').val('');
	$('#courseForm input[name = studyEndTime]').val('');
	$('#courseForm input[name = examStartTime]').val('');
	$('#courseForm input[name = examEndTime]').val('');
	$('#courseForm input[name = remark]').val('');
	$('#courseForm input[name = coursePeriod]').val('');
	$('#courseForm input[name = courseLanguage]').val('');
	$('#courseForm input[name = subtitleType]').val('');
	$('#courseForm input[name = expendTime]').val('');
	$("#courseTypeSelect").val('0');
	$('#courseForm input[name = file]').val('');
	$('#img').attr('src',"");
	$("#courseDetailsSelect").val('0');
	$('#courseForm select[name = orfree]').val('0');
	$('#courseForm .hide input[name = id]').val('');
}
/**
 * 关键字查询事件
 * @param index
 */
function courseSeach(index) {
	$('.btn-primary').unbind().click(function () {
		keyword = $('input[name=keyword]').val();
		//console.log(keyword);
		requestCourse(index,keyword);
	});
}

function pageSeach(index) {
	keyword = $('input[name=keyword]').val();
	if(keyword == null){
		keyword = null;
	}
	requestCourse(index,keyword);
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

function omit(){
	//超出部分显示省略号 
	   $(".courseOmit").each(function() {
			var maxwidth = 5;
			if($(this).html().length > maxwidth) {
				$(this).html($(this).text().substring(0, maxwidth));
				$(this).text($(this).html() +'...');
			}
		});
}

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