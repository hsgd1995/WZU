$(function(){
	var index;
	var keyword;
	var courseId;
	var parentNameId;
	//绑定课程公告事件
	CourseVideoHandler();
//	
	//主标题下拉事件
	$("#parentNameSelect").change(function(){
		parentNameId = $("#parentNameSelect option:selected").val();
		//console.log("..:"+courseId);
		//console.log("..:"+parentNameId);
		requestCourseVideo(index,keyword,courseId,parentNameId);
	});
	
	requestCourseVideo(index,keyword,null,-1);
	
	$.getScript('js/choice.js', function(data, textStatus, jqxhr) {
		/*if (jqxhr.status == '200')
			console.log('导入js/choice.js成功！');
		else
			console.log('导入js/choice.js失败！');*/
	});

});

function CourseVideoHandler(){
	//保存
	saveCourseVideoFunc();
	//删除
	delCourseVideoFunc();
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

function courseVideoDetail(){
	var courseNo = $('input[name=courseNo]').val();
	if(courseNo == null || courseNo == ""){
		alert('请输入课程编号！');
		$('#courseVideoForm input[name = courseId]').val('');
		return false;
	}
	$.ajax({
		url:"courseVideo/getParentName",
		data:{courseNo:courseNo},
		datatype:"json",
		async: false,
		success: function(data){
			// 先清空select下拉列表
			$('#parentNameSelect').html('');
			if (data) {
				$('#courseVideoForm input[name = courseId]').val(data[0].courseId);
				$a = $('<option value = "0">请选择主标题</option>')
				$('#parentNameSelect').append($a).append(fillParentNameSelect(data));
			}
		}
	});
}

///**
// * 加载主标题下拉列表
// */
//function loadParentNameList(courseId) {
//	$.ajax({
//		url : 'courseVideo/'+ courseId +'/getParentName',
//		success : function(data) {
//			// 先清空select下拉列表
//			$('#parentNameSelect').html('');
//			if (data) {
//				$a = $('<option selected value = "0">请选择主标题</option>')
//				$('#parentNameSelect').append($a).append(fillParentNameSelect(data));
//			}
//		}
//	});
//}

/**
 * 对主标题下拉列表添加option
 * @param data
 * @returns {Array}
 */
function fillParentNameSelect(data) {
	var $optionArr = new Array();
	// 遍历数组
	$.each(data,function(index, courseVideo) {
		var $option;
			$option = $('<option value=' + courseVideo.id + '>' + courseVideo.name
					+ '</option>')
		$optionArr.push($option);
	});
	return $optionArr;
}

//保存课程视频
function saveCourseVideoFunc(){
	$('#saveCourseVideo').unbind().click(function(){
		
		//js校验
		var id = $("#courseVideoForm [name=id]").val();
		var courseId = $('#courseVideoForm input[name = courseId]').val();
		if(courseId==0){
			alert('请选择课程！');
			return false;
		}
		var parent = $("#parentNameSelect option:selected").val();
		if(parent == 0){
			alert('请选择主标题！');
			return false;
		}
		var name = $("#courseVideoForm [name=name]").val();
		if(!name){
			alert('请输入子标题！');
			return false;
		}
		var file = $("#uploadFile")[0].files[0];
		//处于新增状态才必须选择文件
		if(!file && !id){
			alert('请选择文件！');
			return false;
		}
		var captionFile = document.getElementById("captionFile").files;
		//console.log('字幕文件：',captionFile);
		var formData = new FormData();
		formData.append("id", id);
		formData.append("courseId", courseId);
		formData.append("parent", parent);
		formData.append("name", name);
		formData.append("file", file);
		for(var i = 0;i<captionFile.length;i++){
			formData.append("captionFile", captionFile[i]);
		}
		
		if ($('#courseVideoForm .hide input[name=id]').val()) {
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
	//进度条
	uploadProgress();
	
	
	$.ajax({
		url : "courseVideo/saveCourseVideo",
		method : 'post',
		data : formData,
		dataType : 'json',
		// 告诉jQuery不要去处理发送的数据
		processData : false,
		// 告诉jQuery不要去设置Content-Type请求头
		contentType : false,
		success : function(data) {
			if (data.status) {
				//console.log('进度条长度:',$("#pro").width());
				$("#pro").width(100+"%");
				setTimeout(function(){
					alert('保存成功');
				}, 1000);
				requestCourseVideo();
				//清空编辑框
				clearCourseVideoFormFunc();
				//进度条归零
				$("#pro").width(0+"%");
				
			}else if(data.status == false){
				alert('保存失败！');
			}
		},
		error:function(){
			alert('保存失败，请填写完整信息！');
		}
	});
}
/**
 * 保存视频进度条
 */
function uploadProgress() {
	//console.log('进度条');
	$('#pro').width(0+"%");
	var t = setInterval(function() {
		$.get('courseVideo/getStatus', function(data) {
			
			$('#pro').width(data.percent+"%");
			if(data.percent>=100){
				//停止这个定时器
				clearInterval(t);
				//console.log('定时器停止');
				
			}
		});
	}, 1000)
}
/**
 * 更新
 * @param formData
 */
function update(formData) {
	
	var file = $("#uploadFile")[0].files[0];
	//选择了文件才触发进度条
	if(file ){
		uploadProgress();
	}
	//进度条
	
	
	var $id = $('#courseVideoForm .hide input[name=id]').val();
	$.ajax({
		url : "courseVideo/updateCourseVideo",
		method : 'post',
		data : formData,
		// 告诉jQuery不要去处理发送的数据,用来序列化data
		processData : false,
		// 告诉jQuery不要去设置Content-Type请求头
		contentType : false,
		success : function(data) {
			if (data) {
				alert('更新成功');
				requestCourseVideo();
				//清空编辑框
				clearCourseVideoFormFunc();
				//进度条置0
				$('#pro').width(0+"%");
			}
		},
		error:function(){
			alert('服务器异常，保存失败');
		}
	});
}

/**
 * 删除确认
 */
function delCourseVideoFunc() {
	$('#delCourseVideo').unbind().click(function () {
		if(isChoosedAny()){
			if (confirm('确认删除吗?')){
				delCourseVideoHandler();
			}
		} else {
			alert("请选择要删除的行");
		}
	});
}

/**
 * 删除
 */
function delCourseVideoHandler() {
	var $idArr = [];
	
	$.each($('.fill tbody input[type=checkbox]'),function(index,checkbox){
		if($(checkbox).is(':checked')){
			$idArr.push($(checkbox).parent().siblings().eq(5).text());
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
				// 重新加载列表
				requestCourseVideo();
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
function requestCourseVideo(index,keyword,courseId,parentNameId) {
	//console.log("courseId111："+courseId);
	//console.log("parentNameId222:"+parentNameId);
	if(index == null || index == 0){
		index = 1;
	}
	if(keyword == null){
		keyword = null;
	}
	
	if(parentNameId ==0 || parentNameId == undefined){
		parentNameId = -1;
	}
	//console.log('parentNameId --> ',parentNameId);
	$.ajax({
		url : 'courseVideo/'+ index +'/getCourseVideoPageModel',
		method : 'post',
		data : {
			"keyword" : keyword,
			"courseId" : courseId,
			"parentNameId" : parentNameId,
		},
		dataType : 'json',
		success : function(data) {
			fillCourseVideoVOList(data);
		}
	});
}

/**
 * 填充列表事件
 * 
 * @param data
 */
function fillCourseVideoVOList(data) {
	var $Data = data.list;
	// 1.清空列表
	$('.fill tbody').html('');
	// 2.填充数据
	$.each($Data,function(index,courseVideoVO){
		$('.fill tbody').append(fillCourseVideoVOData(courseVideoVO));
	});
	// 清空分页栏
	$('#main #pagination').html('');
	// 加载分页栏
	fillPage(data);
	// 分页栏点击事件
	onclickPage(data);
/*	// 3.清空列表事件
	clearCourseVideoFormFunc();*/
	// 4.编辑事件
	editCourseVideoForm();
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
function fillCourseVideoVOData(courseVideoVO) {
	var $tr = $('<tr></tr>');
	var $td1 = $('<td style="text-align: center;"><input type="checkbox" /></td>');
	var $td2 = $('<td>' + courseVideoVO.courseName + '</td>');
	var $td3 = $('<td>' + courseVideoVO.parentName + '</td>');
	var $td4 = $('<td class="contentOmit">' + courseVideoVO.name + '</td>');
	var $td5 = $('<td class="contentOmit">' + courseVideoVO.url + '</td>');
	var $td6 = $('<td><a href="#" class="editCourseVideo">编辑</a></td>');
	var $td7 = $('<td class="hide" name = "id">' + courseVideoVO.id + '</td>');
	return $tr.append($td1).append($td2).append($td3).append($td4).append($td5).append($td6).append($td7);
}

/**
 * 超出部分显示省略号 
 */
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
function clearCourseVideoFormFunc() {
	$('#courseVideoForm .hide input[name = id]').val('');
	$('#courseVideoForm input[name = courseId]').val('');
	$('input[name=courseNo]').val('');
	$('#courseVideoForm input[name = name]').val('');
	$('#courseVideoForm input[name = file]').val('');
	$('#courseSelect').val('0');
	$('#parentNameSelect').val('0');
}

/**
 * 编辑课程事件
 */
function editCourseVideoForm() {
	$('.fill tbody a').unbind().click(function (e) {
		// 取消默认事件
		e.preventDefault();
		// 把改行数据填充到form表单中
		loadTrCourseVideoForm($(this));
	});
}
function loadTrCourseVideoForm(that) {
	var $tdArr = that.parent().siblings();
	//console.log($tdArr.eq(5).text()); //id
	var id = $tdArr.eq(5).text();
	$.ajax({
		url:"findCourseVideoById",
		data:{id:id},
		datatype:"json",
		success: function(data){
			console.log(data);
			$('#courseVideoForm input[name = id]').val(data.id);
			$('#courseVideoForm input[name = courseNo]').val(data.courseNo);
			$('#courseVideoForm input[name = name]').val(data.courseName);
			$('#courseVideoForm textarea[name = url]').val(data.url);
			//$("#courseSelect").change();
			courseVideoDetail();
			console.log(data.parent);
			console.log($('select[name = parentNameSelect]')[0]);
			$('#parentNameSelect').val(data.parent);
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
		requestCourseVideo(index,keyword,courseId,-1);
	});
}

function pageSeach(index) {
	keyword = $('input[name=keyword]').val();
	courseId = $("#courseSelect option:selected").val();
	parentNameId = $("#parentNameSelect option:selected").val();
	if(keyword == null){
		keyword = null;
	}
	requestCourseVideo(index,keyword,courseId,parentNameId);
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