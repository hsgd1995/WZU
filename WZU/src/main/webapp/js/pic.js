$(function() {
	var index;
	var keyword;
	// 选择图片回显
	echoImg();
	// 上传相片事件
	uploadPic();
	// 请求图片列表数据
	requestPic();
	$.getScript('js/choice.js', function(data, textStatus, jqxhr) {
		/*if (jqxhr.status == '200')
			console.log('导入js/choice.js成功！');
		else
			console.log('导入js/choice.js失败！');*/
	});
	picSeach(index);
	//删除图片
	delPicFunc();
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
			$('fieldset img').attr('src', e.target.result);
		}
	});
}


/**
 * 文件上传
 * 
 * @param url
 */
function uploadFunc() {
	// 创建表单数据对象
	var formData = new FormData();
	// 获得表单上传控件文件名称
	var $name = $("#uploadFile").val();
	// 封装上传文件数据和属性
	formData.append("file", $("#uploadFile")[0].files[0]);
	formData.append("id", $("#picForm #id").val());
	formData.append("picNo", $("#picForm [name= picNo]").val());
	formData.append("picName", $("#picForm [name= picName]").val());
	formData.append("pageview", $("#picForm [name= pageview]").val());
	// ajax异步请求
	$.ajax({
		url : "pic/uploadSave",
		type : 'POST',
		data : formData,
		// 告诉jQuery不要去处理发送的数据
		processData : false,
		// 告诉jQuery不要去设置Content-Type请求头
		contentType : false,
		beforeSend : function() {
			// alert("正在进行，请稍候");
		},
		success : function(data) {
			alert(data.message);
			clearPicFormFunc();
			requestPic();
		},
		error : function(data) {
			alert("服务器异常，请联系管理员!");
		}
	});
}

/**
 * 更新图片事件
 */
function uploadPic() {
	$('#upload').unbind().click(function(e) {
		e.preventDefault();
		// 1.判断是否选取了相片
		var $pid = $('#picForm #id').val();
		//console.log($pid);
		if ($pid != "") {
			updateuploadFunc();
		} else {
			if ($('#uploadFile').val()) {
				uploadFunc($pid);
			} else {
				alert('请先选取相片...');
			}
		}
	});
}

/**
 * 更新图片事件
 * @param id
 */
function updateuploadFunc() {
	var $file = $('#picForm input[name = file]').val();
	if($file == ""){
		updateNoFile();
	} else {
		updateOnFile();
	}
}

function updateNoFile() {
	var formParams = getParams($('#picForm').serializeArray());
	//console.log(formParams);
	$.ajax({
		url : "pic/uploadUpdateNoFile",
		method : 'post',
		data : formParams,
		dataType : 'json',
		success : function(data) {
			if (data) {
				alert('更新成功');
				requestPic();
			}
		},
		error:function(){
			alert('服务器异常，保存失败');
		}
	});
}

function updateOnFile() {
	//console.log("更新图片");
	// 创建表单数据对象
	var formData = new FormData();
	// 封装上传文件数据和属性
	formData.append("file", $("#uploadFile")[0].files[0]);
	formData.append("id", $("#picForm #id").val());
	formData.append("picNo", $("#picForm [name= picNo]").val());
	formData.append("picName", $("#picForm [name= picName]").val());
	formData.append("pageview", $("#picForm [name= pageview]").val());
	
	// ajax异步请求
	$.ajax({
		url : "pic/uploadUpdateOnFile",
		type : 'POST',
		data : formData,
		// 告诉jQuery不要去处理发送的数据
		processData : false,
		// 告诉jQuery不要去设置Content-Type请求头
		contentType : false,
		beforeSend : function() {
			// alert("正在进行，请稍候");
		},
		success : function(data) {
			alert(data.message);
			clearPicFormFunc();
			requestPic();
		},
		error : function(data) {
			alert("服务器异常，请联系管理员!");
		}
	});
}

function requestPic(index,keyword) {
	if(index == null || index == 0){
		index = 1;
	}
	if(keyword == null){
		keyword = null;
	}
	$.ajax({
		url : 'pic/'+ index + '/getPicPageModel',
		method : 'post',
		data : {
			keyword : keyword
		},
		dataType : 'json',
		success : function(data) {
			fillPicList(data);
		}
	});
}


function fillPicList(pageModel) {
	var $data = pageModel.list;
	// 1.清空列表
	$('.fill tbody').html('');
	// 2.填充数据
	$.each($data,function(index,pic){
		$('.fill tbody').append(fillPicData(pic));
	});
	// 清空分页栏
	$('#main #pagination').html('');
	// 加载分页栏
	fillPicPage(pageModel);
	// 分页栏点击事件
	onclickPage(pageModel);
	// 3.清空列表事件
	clearPicFormFunc();
	// 4.编辑图片事件
	editPicForm();
	// 5.复选框事件
	// 5-1.标题复选框事件
	theadChecboxFunc();
	// 5-2.列表复选框事件
	tbodyChecboxFunc();
}

function fillPicPage(pageModel) {
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


function fillPicData(pic) {
	var $tr = $('<tr></tr>');
	var $td1 = $('<td style="text-align: center;"><input type="checkbox" /></td>');
	var $td2 = $('<td>' + pic.picNo +'</td>');
	var $td3 = $('<td>' + pic.picName +'</td>');
	var $td4 = $('<td>' + pic.pageview +'</td>');
	var $td5 = $('<td>' + pic.remark +'</td>');
	var $td6 = $('<td><a href="#">编辑</a></td>');
	var $td7 = $('<td class= "hide">' + pic.id +'</td>');
	return $tr.append($td1).append($td2).append($td3).append($td4).append($td5).append($td6).append($td7);
}
/**
 * 删除图片事件
 */
function delPicFunc() {
	$('#delPic').unbind().click(function () {
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
			$idArr.push($(checkbox).parent().siblings().eq(5).text());
		}
	});
	$.ajax({
		url : "pic/deletePic",
		method : 'post',
		data : {
			ids : $idArr
		},
		dataType : 'json',
		success : function(data) {
			if (data) {
				// 重新加载课程详情列表
				requestPic();
			}
		},
		error : function(){
			alert('服务器异常，请联系管理员');
		}
	});
}

function clearPicFormFunc() {
	$('#picForm input[name = picNo]').val('');
	$('#picForm input[name = picName]').val('');
	$('#picForm input[name = pageview]').val('');
	$('#picForm input[name = file]').val('');
	$('#picForm #id').val('');
}

function editPicForm() {
	$('tbody a').unbind().click(function (e) {
		// 取消默认事件
		e.preventDefault();
		// 把改行数据填充到form表单中
		loadTrPicForm($(this));
	});
}

function loadTrPicForm(that) {
	var $tdArr = that.parent().siblings();
	$('#picForm input[name = picNo]').val($tdArr.eq(1).text());
	$('#picForm input[name = picName]').val($tdArr.eq(2).text());
	$('#picForm input[name = pageview]').val($tdArr.eq(3).text());
	$('#picForm #id').val($tdArr.eq(5).text());
	initRequestPath();
}

function initRequestPath() {
	var $pid = $('#picForm #id').val();
	$('fieldset #img').attr('src',
			'pic/' + $pid + '/get')
}

/**
 * 关键字查询事件
 * @param index
 */
function picSeach(index) {
	$('.btn-primary').unbind().click(function () {
		keyword = $('input[name=keyword]').val();
		requestPic(index,keyword);
	});
}

function pagePicSeach(index) {
	keyword = $('input[name=keyword]').val();
	if(keyword == null){
		keyword = null;
	}
	requestPic(index,keyword);
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
	$('#pagination ul li:eq(0) a').attr("onClick","pagePicSeach(1)");
	$('#pagination ul li:eq(1) a').attr("onClick","pagePicSeach(" + $preIndex+")");
	$('#pagination ul li:eq(3) a').attr("onClick","pagePicSeach(" + $nextIndex+")");
	$('#pagination ul li:eq(4) a').attr("onClick","pagePicSeach(" + $totalSize+")");
}