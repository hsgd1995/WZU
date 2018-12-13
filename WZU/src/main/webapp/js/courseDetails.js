$(function(){
	var index;
	var keyword;
	//回显图片
	echoImg();
	//绑定课程详情事件
	courseDetailsHandler();
	//请求课程详情数据事件
	requestCourseDetails(index,keyword);
	//加载choice.js
	$.getScript('js/choice.js', function(data, textStatus, jqxhr) {
		/*if (jqxhr.status == '200')
			console.log('导入js/choice.js成功！');
		else
			console.log('导入js/choice.js失败！');*/
	});
	//搜索事件
	courseDetailsSeach(index);
	//鼠标移过去显示大图
    $(".bimg").hide();
    $(".box img").mousemove(function(e){
        $(".bimg").css({top:e.pageY-86,left:e.pageX}).html('<img src="' + this.src + '" />').show();
    }).mouseout( function(){
    $(".bimg").hide();
  })
	
	
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

function courseDetailsHandler(){
	//保存或更新课程详情事件
	saveCourseDetailsFunc();
	//删除课程详情事件
	delCourseDetailsFunc();
}

/**
 * 保存课程详情事件
 */
function saveCourseDetailsFunc() {
	$('#saveCourseDetails').unbind().click(function() {
		//创建表单数据对象
		var formData = new FormData();
		formData.append("id", $("#courseDetailsForm [name=id]").val());
		formData.append("coursePeriod", $("#courseDetailsForm [name=coursePeriod]").val());	//学时
		formData.append("expendTime", $("#courseDetailsForm [name=expendTime]").val());	//每周耗时
		formData.append("courseLanguage", $("#courseDetailsForm [name=courseLanguage]").val());	//授课语言    
		formData.append("subtitleType", $("#courseDetailsForm [name=subtitleType]").val());	//字幕
		formData.append("remark", $("#courseDetailsForm [name=remark]").val());	//备注
		formData.append("file", $("#uploadFile")[0].files[0]);	//封面
		if ($('#courseDetailsForm .hide input[name=id]').val()) {
			if(confirm("确认更新吗")){
					updateCourseDetails(formData);
			} 
		} else {
			if(confirm("确认增加吗")){
				saveCourseDetails(formData);
			}
		}
	});
}

/**
 * 提交数据，并请求数据填充到页面
 * 
 * @param formData
 */
function saveCourseDetails(formData) {
	$.ajax({
		url : "courseDetails/saveCourseDetails",
		method : 'post',
		data : formData,
		dataType : 'json',
		// 告诉jQuery不要去处理发送的数据
		processData : false,
		// 告诉jQuery不要去设置Content-Type请求头
		contentType : false,
		dataType: 'json',
		success : function(data) {
			if (data.status) {
				alert('保存成功');
				requestCourseDetails();
			}
		},
		error:function(){
			alert('保存失败，请填写完整信息！');
		}
	});
}

/**
 * 更新
 * @param formData
 */
function updateCourseDetails(formData) {
	var $id = $('#courseDetailsForm .hide input[name=id]').val();
	$.ajax({
		url : "courseDetails/updateCourseDetails",
		method : 'post',
		data : formData,
		// 告诉jQuery不要去处理发送的数据,用来序列化data
		processData : false,
		// 告诉jQuery不要去设置Content-Type请求头
		contentType : false,
		dataType: 'json',
		success : function(data) {
			if (data) {
				alert('更新成功');
				requestCourseDetails();
			}
		},
		error:function(){
			alert('服务器异常，保存失败');
		}
	});
}

/**
 * 请求课程详情数据事件
 */
function requestCourseDetails(index,keyword) {
	if(index == null || index == 0){
		index = 1;
	}
	if(keyword == null){
		keyword = null;
	}
	$.ajax({
		url : 'courseDetails/'+ index +'/getCourseDetailsPageModel',
		method : 'post',
		data : {
			keyword : keyword
		},
		dataType : 'json',
		success : function(data) {
			fillCourseDetailsList(data);
		}
	});
}

/**
 * 填充课程详情列表事件
 * 
 * @param courseDetailsData
 */
function fillCourseDetailsList(pageModel) {
	var $courseDetailsDate = pageModel.list;
	// 1.清空列表
	$('.fill tbody').html('');
	// 2.填充数据
	$.each($courseDetailsDate,function(index,courseDetails){
		$('.fill tbody').append(fillCourseDetailsData(courseDetails));
	});
	// 清空分页栏
	$('#main #pagination').html('');
	// 加载分页栏
	fillPage(pageModel);
	// 分页栏点击事件
	onclickPage(pageModel);
	// 3.清空列表事件
	clearCourseDetailsFormFunc();
	// 4.编辑新闻事件
	editCourseDetailsForm();
	// 5.复选框事件
	// 5-1.标题复选框事件
	theadChecboxFunc();
	// 5-2.列表复选框事件
	tbodyChecboxFunc();
}

/**
 * 列表填充
 * 
 * @param courseDetails
 * @returns
 */
function fillCourseDetailsData(courseDetails) {
	var $tr = $('<tr></tr>');
	var $td1 = $('<td style="text-align: center;"><input type="checkbox" /></td>');
	var $td2 = $('<td>' + courseDetails.coursePeriod + '</td>');
	var $td3 = $('<td>' + courseDetails.expendTime + '</td>');
	var $td4 = $('<td>' + courseDetails.courseLanguage + '</td>');
	var $td5 = $('<td>' + courseDetails.subtitleType + '</td>');
	var $td6 = $('<td class="content"><img height="20" width="20" src="'+ courseDetails.coursePic +'"/></td>');
	var $td7 = $('<td>' + courseDetails.remark + '</td>');
	var $td8 = $('<td><a href="#">编辑</a></td>');
	var $td9 = $('<td class="hide" name = "id">' + courseDetails.id + '</td>');
	return $tr.append($td1).append($td2).append($td3).append($td4).append($td5).append($td6).append($td7).append($td8).append($td9);
}

function delCourseDetailsFunc() {
	$('#delCourseDetails').unbind().click(function () {
		if(isChoosedAny()){
			if (confirm('确认删除吗?')){
				delCourseDetailsHandler();
			}
		} else {
			alert("请选择要删除的课程详情");
		}
	});
}

function delCourseDetailsHandler() {
	var $idArr = [];
	
	$.each($('.fill tbody input[type=checkbox]'),function(index,checkbox){
		if($(checkbox).is(':checked')){
			$idArr.push($(checkbox).parent().siblings().eq(7).text());
		}
	});
	$.ajax({
		url : "courseDetails/deleteCourseDetails",
		method : 'post',
		data : {
			ids : $idArr
		},
		dataType : 'json',
		success : function(data) {
			if (data) {
				// 重新加载课程详情列表
				requestCourseDetails();
			}
		},
		error : function(){
			alert('服务器异常，请联系管理员');
		}
	});
}

/**
 * 清空列表事件
 */
function clearCourseDetailsFormFunc() {
	$('#courseDetailsForm .hide input[name = id]').val('');
	$('#courseDetailsForm input[name = coursePeriod]').val('');	//学时
	$('#courseDetailsForm input[name = expendTime]').val('');	//每周耗时
	$('#courseDetailsForm input[name = courseLanguage]').val('');	//授课语言
	$('#courseDetailsForm input[name = subtitleType]').val('');	//字幕
	$('#courseDetailsForm input[name = remark]').val('');	//备注
	$('#courseDetailsForm input[name = file]').val('');	//上传的文件筐
	$('#img').attr('src',"");	//图片
}


/**
 * 编辑新闻事件
 */
function editCourseDetailsForm() {
	$('.fill tbody a').unbind().click(function (e) {
		// 取消默认事件
		e.preventDefault();
		// 把改行数据填充到form表单中
		loadTrCourseDetailsForm($(this));
	});
}
function loadTrCourseDetailsForm(that) {
	var $tdArr = that.parent().siblings();
	var id = $tdArr.eq(7).text();
	$.ajax({
		url:"findCourseDetailsById",
		data:{id:id},
		datatype:"json",
		success: function(data){
			$('#courseDetailsForm input[name = id]').val(data.id);
			$('#courseDetailsForm input[name = coursePeriod]').val(data.coursePeriod);  //学时
			$('#courseDetailsForm input[name = expendTime]').val(data.expendTime);  //每周耗时
			$('#courseDetailsForm input[name = courseLanguage]').val(data.courseLanguage);  //授课语言
			$('#courseDetailsForm input[name = subtitleType]').val(data.subtitleType);  //字幕
			$('#courseDetailsForm input[name = remark]').val(data.remark);  //备注
			$('#img').attr('src',data.coursePic);		//图片
		}
	});
}

/**
 * 关键字查询事件
 * @param index
 */
function courseDetailsSeach(index) {
	$('.btn-primary').unbind().click(function () {
		keyword = $('input[name=keyword]').val();
		//console.log(keyword);
		requestCourseDetails(index,keyword);
	});
}

function pageSeach(index) {
	keyword = $('input[name=keyword]').val();
	if(keyword == null){
		keyword = null;
	}
	requestCourseDetails(index,keyword);
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