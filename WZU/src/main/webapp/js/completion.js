$(function() {
	//首次进入页面时进行处理
	var index;
	var keyword;

	$.getScript('js/choice.js', function(data, textStatus, jqxhr) {
		if (jqxhr.status == '200')
			console.log('导入js/choice.js成功！');
		else
			console.log('导入js/choice.js失败！');
	});
	// 新增按钮绑定事件
	questionInfo();
	// 导入Excel
	importQuestion();
	// 关闭按钮绑定事件
	close();
	// 选择题事件
	questionHandler();
	// 请求选择题
	requestQuestion(index, keyword);
	// 加载课程下拉列表
	loadCourseList();
//	loadCourseVideoList();
	
	//课程下拉列表事件
	$("#courseSelect").change(function(){
		courseId = $("#courseSelect option:selected").val();
		//加载主标题下拉列表
		loadCourseVideoList(courseId);
	});
	//搜索按钮事件
	$('#screen-select').unbind().click(function(e){
		// 取消默认事件
		e.preventDefault();
		// 请求消息与用户数据事件
		requestQuestion(index,keyword);
	});
});

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

/**
 * 对课程下拉框添加option
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

/**
 * 加载课程下拉列表
 */
function loadCourseVideoList(courseId) {
	$.ajax({
		url : 'courseVideo/'+ courseId +'/getParentName',
		success : function(data) {
			// 先清空select下拉列表
			$('#courseVideoSelect').html('');
			if (data) {
				$a = $('<option selected value = "0">请选择课程视频主标题</option>')
				$('#courseVideoSelect').append($a).append(
						fillCourseVideoSelect(data));
			}
		}
	});
}

/**
 * 添加课程视频主标题下拉列表option标签
 * 
 * @param data
 * @returns {Array}
 */
function fillCourseVideoSelect(data) {
	var $optionArr = new Array();
	// 遍历数组
	$.each(data, function(index, courseVideo) {
		var $option;
		$option = $('<option value=' + courseVideo.id + '>' + courseVideo.name
				+ '</option>')
		$optionArr.push($option);
	});
	return $optionArr;
}

/**
 * 新增按钮绑定事件
 */
function questionInfo() {
	$('#saveQuestion').unbind().click(function(e) {
		e.preventDefault();
		$('.zz').show();
		$('.editInfo .form-group .btn-primary').val("保存");// 设置确定按钮的文本
		$('.editInfo').show();// 打开选择题详细信息窗口
		//清空选择题详细信息窗口中的数据
		clearQuestionFormFunc();
	});
}

/**
 * 导入Excel
 */
function importQuestion(){
	$('#submitExcel').unbind().click(function(e){
		//console.log('提交表单');
		e.preventDefault();
		//提交表单
		var formData = new FormData();
		var file = $("#file")[0].files[0];
		if(!file){
			alert('请选择文件！');
			return;
		}
		//进度条
		uploadProgress();
		//模态框关闭事件
		formData.append("file", file);
		$.ajax({
			url : "completion/importExcel",
			method : 'post',
			data : formData,
			dataType : 'json',
			// 告诉jQuery不要去处理发送的数据
			processData : false,
			// 告诉jQuery不要去设置Content-Type请求头
			contentType : false,
			success : function(data) {
				//console.log('data:',data);
				var noExist = '';
				//是否存在错误记录或者不合格记录
				if (!data.status) {
					//是
					var errorList = data.errorList;//错误记录集合
					var errorListSize = data.errorListSize;//错误记录集合总数
					//是否存在错误记录
					if(errorList.length){
						noExist = noExist + '部分记录导入失败，原因：课程视频主标题不存在！'+'<br>';
				
						for(var i=0;i<errorList.length;i++){
						//console.log('id->',errorList[i].id);
						//console.log('name->',errorList[i].name);
						noExist = noExist + '  序号：'+ errorList[i].id+',&emsp;   主标题：'+errorList[i].name+' <br>  ';
						}
					}
					//是否存在不合格记录
					if(data.failIndex){
						noExist = noExist + '<p style="color:red"><br>导入停止，存在不合格的记录，第'+data.failIndex+'条行记录存在空的单元格，该行记录之前的记录已导入</p>';
					}
					$('#message').html(noExist);
					//停止定时器
					clearInterval(t);
					//重新加载列表
					requestQuestion();
					//重新加载列表
					requestQuestion();
				
					return;
				}
				$('#message').text('全部导入成功！');
				
			},
			error : function() {
				alert('服务器异常，保存失败');
			}
		});
	});
}

/**
 * 保存视频进度条
 */
function uploadProgress() {
	//console.log('进度条');
	$('#pro').width(0+"%");
	t = setInterval(function() {
		$.get('cq/getStatus', function(data) {
			
			$('#pro').width(data.percent+"%");
			if(data.percent>=100){
				//停止这个定时器
				clearInterval(t);
				//console.log('定时器停止');
				
			}
		});
	}, 500)
}
/**
 * 模态框关闭的处理事件
 */
function closeModal(){
		//重新加载列表
		requestQuestion();
		$('#pro').width(0+"%");
		$('#file').val('');
		//console.log('清除已选择的文件');
}

/**
 * 关闭按钮绑定事件
 */
function close() {
	$('#close').unbind().click(function() {
		$('.zz').hide();
		$('.editInfo').hide();// 关闭选择题详细信息窗口
		clearQuestionFormFunc();
	});
}

/**
 * 新增，修改，删除
 */
function questionHandler() {
	// 保存
	saveQuestion();
	// 删除
	delQuestion();
}

/**
 * 新增或更新选择题
 */
function saveQuestion() {
	$('.editInfo .form-group .btn-primary').unbind()
			.click(function() {
						/*
						 * var formParams =
						 * getParams($('#questionForm').serializeArray());
						 */
						//校验数据
						var title = $("#questionForm [name=title]").val();
						var jieshi = $("#questionForm [name=jieshi]").val();
						var answer = $("#questionForm [name=answer]").val();
						var courseVideoId = $("#courseVideoSelect option:selected").val();
						//console.log('courseVideoId:',courseVideoId);
						if(courseVideoId==0){
							alert('请选择课程视频主标题！');
							return false;
						}
						if(!title){
							alert('请输入题目！');
							return false;
						}
						if(!jieshi){
							alert('请输入解释！');
							return false;
						}
						
						
						var formData = new FormData();
						formData.append("id", $("#questionForm [name=id]").val());
						formData.append("title",title);
						formData.append("answer",answer);
						formData.append("jieshi", jieshi);
						formData.append("courseVideoId", courseVideoId);

						if ($('#questionForm .hide input[name=id]').val()) {
							update(formData);
						} else {
							save(formData);
						}
					});
}


/**
 * 更新
 * @param formData
 */
function update(formData) {
	//console.log('执行更新');
	$.ajax({
		url : "completion/updateQuestion",
		method : 'post',
		data : formData,
		dataType : 'json',
		// 告诉jQuery不要去处理发送的数据,用来序列化data
		processData : false,
		// 告诉jQuery不要去设置Content-Type请求头
		contentType : false,
		success : function(data) {
			if (data) {
				alert('更新成功');
				$('.zz').hide();
				$('.editInfo').hide();
				clearQuestionFormFunc();
				requestQuestion();
				//清空搜索框
				$('input[name=keyword]').val('');
			}
		},
		error : function() {
			alert('服务器异常，保存失败');
		}
	});
}

/**
 * 新增
 * @param formData
 */
function save(formData) {
	//console.log('执行新增');
	$.ajax({
		url : "completion/saveQuestion",
		method : 'post',
		data : formData,
		dataType : 'json',
		// 告诉jQuery不要去处理发送的数据
		processData : false,
		// 告诉jQuery不要去设置Content-Type请求头
		contentType : false,
		success : function(data) {
			if (data) {
				alert('保存成功');
				$('.zz').hide();
				$('.editInfo').hide();
				clearQuestionFormFunc();
				requestQuestion();
				//清空搜索框
				$('input[name=keyword]').val('');
			}
		},
		error : function() {
			alert('服务器异常，保存失败');
		}
	});
}

/**
 * 重置按钮：清空选择题详细信息窗口里的所有数据
 */
function clearQuestionFormFunc() {
	$('#questionForm input[name=id]').val('');
	$('#questionForm textarea[name=id]').val('');
	$('#questionForm textarea[name=title]').val('');
	$('#questionForm textarea[name=answer]').val('');
	$('#questionForm textarea[name=jieshi]').val('');
	$('#courseVideoSelect').val('0');
	//选择题详细信息窗口中的保存按钮
	$('.editInfo .form-group .btn-primary').val("保存");
}

/**
 * 删除按钮绑定事件
 */
function delQuestion() {
	$('#delQuestion').unbind().click(function() {
		if (isChoosedAny()) {
			if (confirm('确认删除吗?')) {
				delQuestionHandler();
			}
		} else {
			alert("请选择要删除的选择题！！！");
		}
	});
}

/**
 * 删除记录
 */
function delQuestionHandler() {
	var $idArr = [];
	$.each($('.fill tbody input[type=checkbox]'), function(index, checkbox) {
		if ($(checkbox).is(':checked')) {
			$idArr.push($(checkbox).parent().siblings().eq(0).text());
		}
	});
	$.ajax({
		url : "completion/delQuestion",
		method : 'post',
		data : {
			ids : $idArr
		},
		dataType : 'json',
		success : function(data) {
			if (data) {
				alert('删除成功！');
				// 重新加载新闻列表
				requestQuestion();
			}
		},
		error : function() {
			alert('服务器异常，请联系管理员');
		}
	});
}
//重新加载新闻列表
function requestQuestion(index, keyword) {
	if (index == null || index == 0) {
		index = 1;
	}
	if (keyword == null) {
		keyword = null;
	}
	var subject = $('#screen-subject').val();
	var title = $('#screen-title').val();
	$.ajax({
		url : 'completion/' + index + '/getQuestionPageModel',
		method : 'post',
		data : {
			'subject' : subject,
			'title' : title
		},
		dataType : 'json',
		success : function(data) {
			fillQuestionData(data);
		}
	});
	
}

/**
 * 刷新选择题列表：对从后台获取的数据进行处理
 * @param pageModel
 */
function fillQuestionData(pageModel) {
	var $questionDate = pageModel.list;
	$('.fill tbody').html('');
	$.each($questionDate,function(index,question){
		$('.fill tbody').append(fillQuestion(question));
	});
	// 2.填充数据
	// 清空分页栏
	$('#main #pagination').html('');
	// 填充分页栏
	fillQuestionPage(pageModel);
	// 分页栏点击事件
	onclickPage(pageModel);
	// 清空列表事件
	clearQuestionFormFunc();
	// 修改选择题事件
	editQuestion();
	// 标题复选框事件
	theadChecboxFunc();
	// 列表复选框事件
	tbodyChecboxFunc();
	// 文本隐藏
	omit();
	//取消选中全选框
	$('#selectAll').prop('checked',false); 
}

/**
 * 构造列表的行
 * @param question
 * @returns
 */
function fillQuestion(question) {
	var $tr = $('<tr></tr>');
	var $td1 = $('<td style="text-align: center;"><input type="checkbox" /></td>');
	var $td2 = $('<td>' + question.id + '</td>');
	var $td3 = $('<td class="omit">' + question.title + '</td>');
	var $td4 = $('<td>'+question.answer+'</td>');
	var $td5 = $('<td>' + question.courseName + '</td>');
	var $td6 = $('<td><button class="btn btn-xs btn-success">修改</button></td>');
	return $tr.append($td1).append($td2).append($td3).append($td4).append($td5).append($td6);
}

/**
 * 对列表中修改按钮添加事件
 */
function editQuestion() {
	$('.fill tbody button').unbind().click(function(e) {
		// 取消默认事件
		e.preventDefault();
		$('.zz').show();
		$('.editInfo .form-group .btn-primary').val("修改");
		$('.editInfo').show();
		// 把改行数据填充到form表单中
		var $tdArr = $(this).parent().siblings();
		var id = $tdArr.eq(1).text();
		$.ajax({
			url : "completion/" + id + "/getQuestion",
			method : 'post',
			dataType : 'json',
			success : function(data) {
				if (data) {
					// 对选择题详细信息窗口填充数据
					loadEditInfo(data);
				}
			}
		});
	});
}

/**
 * 对选择题详细信息窗口填充数据
 * @param data
 */
function loadEditInfo(data) {
	$('#questionForm input[name=id]').val(data.id);
	$('#questionForm textarea[name=title]').val(data.title);
	$('#questionForm textarea[name=answer]').val(data.answer);
	$('#questionForm textarea[name=jieshi]').val(data.jieshi);
	$("#courseVideoSelect").val(data.courseVideoId);
}

/**
 * 对选择列表中选择题题目进行处理，若过该字段数据长度超过20，则前面20个字符正常显示，超过20的字符用...表示
 */
function omit() {
	$(".omit").each(function() {
		var maxwidth = 20;
		if ($(this).html().length > maxwidth) {
			$(this).html($(this).text().substring(0, maxwidth));
			$(this).html($(this).html() + '...');
		}
	});
}

/**
 * 填充分页栏
 * @param pageModel
 */
function fillQuestionPage(pageModel) {
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
	var $li3 = $('<li><a>第&nbsp;' + $pageIndex + '&nbsp;页</a></li>');
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

/**
 * 分页栏按钮的绑定事件
 * @param index
 */
function pageQuestionSeach(index) {
	keyword = $('input[name=keyword]').val();
	//console.log('keyword:',keyword);
	if (keyword == null) {
		keyword = null;
	}
	requestQuestion(index, keyword);
}

/**
 * 分页栏按钮添加事件
 * @param pageModel
 */
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
	$('#pagination ul li:eq(0) a').attr("onClick", "pageQuestionSeach(1)");
	$('#pagination ul li:eq(1) a').attr("onClick","pageQuestionSeach(" + $preIndex + ")");
	$('#pagination ul li:eq(3) a').attr("onClick","pageQuestionSeach(" + $nextIndex + ")");
	$('#pagination ul li:eq(4) a').attr("onClick","pageQuestionSeach(" + $totalSize + ")");
}