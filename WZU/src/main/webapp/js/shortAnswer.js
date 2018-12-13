/**
 * 简答题
 */
$(function() {
	var index;
	var keyword;

	// 请求消息数据事件
	requestData(index,keyword);
	
	//超出部分显示省略号 
	omit1();
	omit2();
	
	$.getScript('js/choice.js', function(data, textStatus, jqxhr) {
	});

	
	//保存按钮事件
	$('#save').unbind().click(function (e) {
		// 取消默认事件
		e.preventDefault();
		//添加和更新
		addAndUpdate();
	});
	//删除按钮事件
	$('#del').unbind().click(function(e){
		// 取消默认事件
		e.preventDefault();
		//删除
		if(confirm('确认删除吗？')){
			del();
		}
	});
	
	//查询按钮事件
	$('#screen-select').unbind().click(function(e){
		// 取消默认事件
		e.preventDefault();
		// 请求消息数据事件
		requestData(index,keyword);
	});
});

/**
 * 请求消息数据事件
 */
function requestData(index,keyword) {
	if(index == null || index == 0){
		index = 1;
	}
	var name = $('#screen-name').val();
	$.ajax({
		url : 'shortAnswer/list',
		method : 'get',
		data : {
			pageIndex : index,
			pageSize : 8,
			name : name,
		},
		dataType : 'json',
		success : function(data) {
			fillList(data);
		}
	});
}

/**
 * 填充消息列表事件
 * 
 * @param newsData
 */
function fillList(pageModel) {
	var list = pageModel.list;
	// 1.清空列表
	$('.fill tbody').html('');
	// 2.填充数据
	$.each(list,function(index,sa){
		$('.fill tbody').append(fillData(sa));
	});
	// 清空分页栏
	$('#main #pagination').html('');
	// 加载分页栏
	fillPage(pageModel);
	// 分页栏点击事件
	onclickPage(pageModel);
	// 3.清空编辑框
	clearFormFunc();
	// 4.编辑消息事件
	editForm();
	// 5.复选框事件
	// 5-1.标题复选框事件
	theadChecboxFunc();
	// 5-2.列表复选框事件
	tbodyChecboxFunc();
	//超出部分显示省略号 
	omit1();
	omit2();
	
	$('#thCheckbox').prop('checked',false);
}

/**
 * 列表填充
 * 
 * @param sa
 * @returns
 */
function fillData(sa) {
	var $tr = $('<tr></tr>');
	var $td1 = $('<td style="text-align: center;"><input value="'+sa.id+'" type="checkbox" /></td>');
	var $td2 = $('<td class="content"><span title="'+sa.name+'" class="tieleOmit">' + sa.name + '</span></td>');
	var $td3 = $('<td class="content"><span  class="contentOmit">' + sa.answer + '</span></td>');
	var $td7 = $('<td><a href="#" no="'+sa.id+'" class="editNews">编辑</a></td>');
	var $td8 = $('<td class="hide" name = "id">' + sa.id + '</td>');
	return $tr.append($td1).append($td2).append($td3).append($td7).append($td8);
}

/**
 * 分页栏点击事件
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
	$('#pagination ul li:eq(0) a').attr("onClick","pageSeach(1)");
	$('#pagination ul li:eq(1) a').attr("onClick","pageSeach(" + $preIndex+")");
	$('#pagination ul li:eq(3) a').attr("onClick","pageSeach(" + $nextIndex+")");
	$('#pagination ul li:eq(4) a').attr("onClick","pageSeach(" + $totalSize+")");
}

/**
 * 消息标题字数超出部分处理
 */
function omit1(){
	//超出部分显示省略号 
	   $(".tieleOmit").each(function() {
			var maxwidth = 10;
			if($(this).html().length > maxwidth) {
				$(this).html($(this).text().substring(0, maxwidth));
				$(this).html($(this).html() +'...');
			}
		});
}

/**
 * 消息内容字数超出部分处理
 */
function omit2(){
	//超出部分显示省略号 
	   $(".contentOmit").each(function() {
			var maxwidth = 20;
			if($(this).html().length > maxwidth) {
				$(this).html($(this).text().substring(0, maxwidth));
				$(this).html($(this).html() +'...');
			}
		});
}

/**
 * 分页栏事件
 * @param pageIndex
 */
function pageSeach(pageIndex){
	requestData(pageIndex,null);
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

/**
 * 编辑按钮事件
 */
function editForm() {
	$('.fill tbody a').unbind().click(function (e) {
		// 取消默认事件
		e.preventDefault();
		// 把改行数据填充到form表单中
		loadTrNewsForm($(this));
	});
}
/**
 * 填充编辑框
 * @param that
 */
function loadTrNewsForm(that) {
	var id = that.attr('no');
	
	$.ajax({
		url:"shortAnswer/findById",
		data:{id:id},
		datatype:"json",
		success: function(data){
			$('#shortAnswerForm input[name = name]').val(data.name);
			$('#shortAnswerForm input[name = id]').val(data.id);
			editor.txt.html(data.answer);
		}
	});
}

/**
 * 清空编辑框
 */
function clearFormFunc() {
	$('#shortAnswerForm .hide input[name = id]').val('');
	$('#shortAnswerForm input[name = name]').val('');
	editor.txt.html("");
}

/**
 * 添加和更新
 */
function addAndUpdate(){
	var id = $('#shortAnswerForm .hide input[name = id]').val();
	var name = $('#shortAnswerForm input[name = name]').val();
	var answer = editor.txt.html();
	
	if(!name){
		alert('请填写题目...');
		return;
	}
	if(!answer){
		alert('请填写答案...');
		return;
	}
	
	var formData = new FormData();
	formData.append("id", id);
	formData.append("name", name);
	formData.append("answer", answer);
	if(id){
		if(confirm('确认更新吗？')){
			update(formData);
		}
	}else{
		if(confirm('确认添加吗？')){
			add(formData);
		}
	}
}

/**
 * 更新
 * @param formData
 */
function update(formData){
	$.ajax({
		url:'shortAnswer/update',
		method:'post',
		data:formData,
		dataType:'json',
		processData:false,
		contentType:false,
		success : function(data){
			if(data.status){
				alert('更新成功！');
				// 请求消息数据事件
				requestData();
			}
		},
		error:function(){
			alert('服务器异常，更新失败！')
		}
	});
}

/**
 * 添加
 * @param formData
 */
function add(formData){
	$.ajax({
		url:'shortAnswer/add',
		method:'post',
		data:formData,
		dataType:'json',
		processData:false,
		contentType:false,
		success : function(data){
			if(data.status){
				alert('添加成功！');
				// 请求消息数据事件
				requestData();
			}else{
				alert('添加失败，请重新登录后再添加！');
			}
		},
		error:function(){
			alert('服务器异常，添加失败！')
		}
	});
}

/**
 * 删除
 */
function del(){
	var $idArr = [];
	$.each($('.fill tbody input[type=checkbox]'),function(index,checkbox){
		if($(checkbox).is(':checked')){
			$idArr.push($(checkbox).val());
		}
	});
	if($idArr.length<=0){
		alert('请选择要删除的记录...');
		return;
	}
	$.ajax({
		url:'shortAnswer/batchDel',
		method:'post',
		data:{ids:$idArr},
		dataType:'json',
		success:function(data){
			if(data.status){
				alert('删除成功！');
				// 请求消息数据事件
				requestData();
			}
		},
		error:function(){
			alert('服务器异常，删除失败！');
		}
	});
	
}

