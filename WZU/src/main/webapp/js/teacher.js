$(function() {
	var index;
	var keyword;
	echoImg();
	// 绑定教师事件
	teacherHandler();
	// 请求教师数据事件
	requestTeacher();
	$.getScript('js/choice.js', function(data, textStatus, jqxhr) {
		/*if (jqxhr.status == '200')
			console.log('导入js/choice.js成功！');
		else
			console.log('导入js/choice.js失败！');*/
	});
	
	// 搜索事件
	$('#screen-select').unbind().click(function(e){
		// 取消默认事件
		e.preventDefault();
		// 请求消息与用户数据事件
		requestTeacher(index);
	});
	//查询教师时间
	
	//鼠标移动过去显示大图
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

function teacherHandler() {
	// 保存教师事件
	saveTeacherFunc();
	// 更新教师事件
	delTeacherFunc();
	//查询教师事件
	queryTeacherFunc();
}

/**
 * 保存新闻事件
 */
function saveTeacherFunc() {
	$('#saveTeacher').unbind().click(function() {
/*		var $formParams = getParams($('#teacherForm').serializeArray());*/
		var formData = new FormData();
		formData.append("id", $("#teacherForm [name=id]").val());
		formData.append("teacherName", $("#teacherForm [name=teacherName]").val());
		formData.append("position",$("#teacherForm [name=position]").val());
		formData.append("introduce", $("#teacherForm [name=introduce]").val());
		formData.append("teacherNo",$("#teacherForm [name=teacherNo]").val());
		formData.append("file", $("#uploadFile")[0].files[0]);
		formData.append("content", editor.txt.html());
		
		if ($('#teacherForm .hide input[name=id]').val()) {
			if(confirm("确认更新吗")){
				updateTeacher(formData);
			} 
		} else {
			if(confirm("确认增加吗")){
				saveTeacher(formData);
			}
		}
	});
}

function queryTeacherFunc(){
	$('#queryTeacher').unbind().click(function(){
		var formData = new FormData();
		formData.append("teacherName",$("#teaName").val());
		if($("#teaName").val() == '' || $("#teaName").val() == undefined){
			alert('请输入查询关键字');
			requestTeacher();
		}else{
			queryTeacher(formData);
		}
	});
}


function queryTeacher(formData){
	//console.log(formData.teacherName);
	$.ajax({
		url : "teacher/queryTeacher",
		method : 'post',
		data : formData,
		dataType : 'json',
		// 告诉jQuery不要去处理发送的数据
		processData : false,
		// 告诉jQuery不要去设置Content-Type请求头
		contentType : false,
		success : function(data) {
			if(data.recordCount == 0){
				alert('没有查询到信息!');
				$("#teaName").val('');
			}else{
				fillTeacherList(data);
			}
		}
	});
}

/**
 * 提交数据，并请求数据填充到页面
 * 
 * @param formParams
 */
function saveTeacher(formData) {
	$.ajax({
		url : "teacher/saveTeacher",
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
				requestTeacher();
			}
		},
		error:function(){
			alert('保存失败，请填写完整信息！');
		}
	});
}

/**
 * 请求新闻数据事件
 */
function requestTeacher(index) {
	if(index == null || index == 0){
		index = 1;
	}
	var teacherName = $('#screen-teacherName').val();
	var teacherNo = $('#screen-teacherNo').val();
	$.ajax({
		url : 'teacher/' + index + '/getTeacherPageModel',
		method : 'post',
		data : {
			"teacherName" : teacherName,
			"teacherNo" : teacherNo
		},
		dataType : 'json',
		success : function(data) {
			fillTeacherList(data);
		}
	});
}

function updateTeacher(formData) {
	var $TeacherId = $('#teacherForm .hide input[name=id]').val();
	$.ajax({
		url : "teacher/updateTeacher",
		method : 'post',
		data : formData,
		// 告诉jQuery不要去处理发送的数据,用来序列化data
		processData : false,
		// 告诉jQuery不要去设置Content-Type请求头
		contentType : false,
		success : function(data) {
			if (data) {
				alert('更新成功');
				requestTeacher();
			}
		},
		error:function(){
			alert('服务器异常，保存失败');
		}
	});
}

function delTeacherFunc() {
	$('#delTeacher').unbind().click(function () {
		if(isChoosedAny()){
			if (confirm('确认删除吗?')){
				delTeacherHandler();
			}
		} else {
			alert("请选择要删除的教师信息！！！");
		}
	});
}

function delTeacherHandler() {
	var $idArr = [];
	
	$.each($('.fill tbody input[type=checkbox]'),function(index,checkbox){
		if($(checkbox).is(':checked')){
			$idArr.push($(checkbox).parent().siblings().eq(7).text());
		}
	});
	//console.log($idArr);
	$.ajax({
		url : "teacher/deleteTeacher",
		method : 'post',
		data : {
			ids : $idArr
		},
		dataType : 'json',
		success : function(data) {
			if (data) {
				alert('删除成功');
				// 重新加载新闻列表
				requestTeacher();
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
 * @param TeacherData
 */
function fillTeacherList(pageModel) {
	var $teacherData = pageModel.list;
	// 1.清空列表
	$('.fill tbody').html('');
	// 2.填充数据
	$.each($teacherData,function(index,teacher){
		$('.fill tbody').append(fillTeacherData(teacher));
	});
	// 清空分页栏
	$('#main #pagination').html('');
	// 加载分页栏
	fillPage(pageModel);
	// 分页栏点击事件
	onclickPage(pageModel);
	// 3.清空列表事件
	clearTeacherFormFunc();
	// 4.编辑新闻事件
	editTeacherForm();
	// 5.复选框事件
	// 5-1.标题复选框事件
	theadChecboxFunc();
	// 5-2.列表复选框事件
	tbodyChecboxFunc();
	//详细介绍显示省略
	omit1();
}

function editTeacherForm() {
	$('.fill tbody a').unbind().click(function (e) {
		// 取消默认事件
		e.preventDefault();
		// 把改行数据填充到form表单中
		loadTrTeacherForm($(this));
	});
}

function loadTrTeacherForm(that) {
	var $tdArr = that.parent().siblings();
	//console.log($tdArr.eq(7).text()); //id
	var id = $tdArr.eq(7).text();
	$.ajax({
		url:"findTeacherById",
		data:{id:id},
		dataType:"json",
		success: function(data){
			$('#teacherForm input[name = id]').val(data.id);
			$('#teacherForm input[name = teacherName]').val(data.teacherName);
			$('#teacherForm input[name = position]').val(data.position);
			$('#teacherForm input[name = introduce]').val(data.introduce);
			$('#teacherForm input[name = teacherNo]').val(data.teacherNo);
			$('#img').attr('src',data.teacherPic);
			editor.txt.html(data.content);
		}
	});
}

/**
 * 列表填充
 * 
 * @param Teacher
 * @returns
 */
function fillTeacherData(teacher) {
	var $tr = $('<tr></tr>');
	var $td1 = $('<td style="text-align: center;"><input type="checkbox" /></td>');
	var $td2 = $('<td>' + teacher.teacherName + '</td>');
	var $td3 = $('<td>' + teacher.position + '</td>');
	var $td4 = $('<td>' + teacher.introduce + '</td>');
	var $td5 = $('<td>' + teacher.teacherNo + '</td>');
	var $td6 = $('<td class="teacherContentOmit">' + teacher.content + '</td>');
	var $td7 = $('<td><img height="20" width="20" src="'+ teacher.teacherPic +'"/></td>');
	var $td8 = $('<td><a href="#">编辑</a></td>');
	var $td9 = $('<td class="hide" name = "id">' + teacher.id + '</td>');
	return $tr.append($td1).append($td2).append($td3).append($td4).append($td5).append($td6).append($td7).append($td8).append($td9);
}

/**
 * 清空表单
 */
function clearTeacherFormFunc() {
	$('#teacherForm input[name = teacherName]').val('');
	$('#teacherForm input[name = position]').val('');
	$('#teacherForm input[name = introduce]').val('');
	$('#teacherForm input[name = teacherNo]').val('');
	$('#teacherForm input[name = id]').val('');
	$('#teacherForm input[name = file]').val('');
	$('#img').attr('src',"");
	editor.txt.html("");
}

function pageSeach(index) {
	requestTeacher(index);
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

function omit1(){
	//超出部分显示省略号 
	   $(".teacherContentOmit").each(function() {
			var maxwidth = 10;
			if($(this).text().length > maxwidth) {
				$(this).text($(this).text().substring(0, maxwidth));
				$(this).html($(this).html() +'...');
			}
		});
}
