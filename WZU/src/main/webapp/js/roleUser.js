$(function(){
	var index;
	var keyword;

	// 请求角色与用户数据事件
	requestData(index,keyword);
	//获取角色下拉列表数据
	getRoleList();
	
	
	//保存按钮事件
	$('#save').unbind().click(function (e) {
		// 取消默认事件
		e.preventDefault();
		//添加
		addData();
	});
	//删除按钮事件
	$('#delete').unbind().click(function(e){
		// 取消默认事件
		e.preventDefault();
		//删除
		if(confirm('确认删除吗？')){
			del();
		}
	});
	
	$('#role-select').unbind().click(function(e){
		// 取消默认事件
		e.preventDefault();
		// 请求角色与用户数据事件
		requestData(index,keyword);
	});
});

/**
 * 获取角色数据
 */
function getRoleList(){
	var index = 1;
	$.ajax({
		url:'role/list',
		method:'get',
		dataType:'json',
		data:{
			pageIndex : index,
			pageSize : 20,
			role:null
		},
		success : function(data) {
			fillSelect(data);
		}
	});
}

/**
 * 填充角色下拉列表
 */
function fillSelect(data){
	var obj = document.getElementById("roleSelect");
	var list = data.list;
	obj.add(new Option('请选择...', -1));
	for (var index = 0; index < list.length; index++) {
        //添加一个选项
        obj.add(new Option(list[index].name+"---"+list[index].state, list[index].id));
    }
}

/**
* 请求角色与用户数据事件
*/
function requestData(index,keyword) {
	if(index == null || index == 0){
		index = 1;
	}
	var name = $('#role-name').val();
	var username = $('#user-username').val();
	
	$.ajax({
		url : 'roleUser/list',
		method : 'get',
		data : {
			pageIndex : index,
			pageSize : 20,
			name : name,
			username : username,
		},
		dataType : 'json',
		success : function(data) {
			fillList(data);
		}
	});
}

/**
 * 填充角色列表事件
 * 
 * @param newsData
 */
function fillList(pageModel) {
	var list = pageModel.list;
	// 1.清空列表
	$('.fill tbody').html('');
	// 2.填充数据
	$.each(list,function(index,object){
		$('.fill tbody').append(fillData(object));
	});
	// 清空分页栏
	$('#main #pagination').html('');
	// 加载分页栏
	fillPage(pageModel);
	// 分页栏点击事件
	onclickPage(pageModel);
	
	// 5.复选框事件
	// 5-1.标题复选框事件
	theadChecboxFunc();
	// 5-2.列表复选框事件
	tbodyChecboxFunc();
	//超出部分显示省略号 
	omit1();
	omit2();
	
	$('#roleUserCheckbox').prop('checked',false);
}

/**
 * 列表填充
 * 
 * @param object
 * @returns
 */
function fillData(object) {
	var $tr = $('<tr></tr>');
	var $td1 = $('<td style="text-align: center;"><input value="'+object.id+'" type="checkbox" /></td>');
	var $td2 = $('<td class="content"><span class="tieleOmit" TITLE="'+object.role.name+'">' + object.role.name + '</span></td>');
	var $td3 = $('<td><span class="contentOmit content" TITLE="'+object.role.state+'">' + object.role.state + '</td>');
	var $td4 = $('<td class="contentOmit content">' + object.user.id + '</td>');
	var $td5 = $('<td><span class="contentOmit content" TITLE="'+object.user.username+'">' + object.user.username + '</span></td>');
	var $td6 = $('<td class="hide" name = "id">' + object.id + '</td>');
	return $tr.append($td1).append($td2).append($td3).append($td4).append($td5).append($td6);
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
 * 角色-用户分页栏事件
 * @param pageIndex
 */
function pageSeach(pageIndex){
	requestData(pageIndex,null);
}

/**
 * 角色标题字数超出部分处理
 */
function omit1(){
	//超出部分显示省略号 
	   $(".tieleOmit").each(function() {
			var maxwidth = 15;
			if($(this).html().length > maxwidth) {
				$(this).html($(this).text().substring(0, maxwidth));
				$(this).html($(this).html() +'...');
			}
		});
}

/**
 * 角色内容字数超出部分处理
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
 * 添加
 */
function addData(){
	var $userIdArr = [];
	var roleIds = [];
	roleIds.push($('#Form select').val());
	var allUser = false;
	$.each($('td img[use=true]'),function(index,img){
			$userIdArr.push($(img).parent().attr('id'));
	});
	
	if(roleIds.length<=0 || roleIds[0]==-1){
		alert('请选择角色...');
		return;
	}
	
	if($userIdArr.length<=0){
		alert('请选择用户...');
		return;
	}
	
	if($('#selectAllUser').is(':checked')){
		allUser = true;
	}
	
	
	var confirmMessage = '';
	if(allUser){
		confirmMessage='确认分配该角色给所有用户吗？';
	}else{
		confirmMessage='确认分配该角色给'+$userIdArr.length+'个用户吗？'
	}
	if(confirm(confirmMessage)){
		add(roleIds,$userIdArr,allUser);
	}
}

/**
 * 提交数据
 * @param formData
 */
function add(roleIds,$userIdArr,allUser){
	$.ajax({
		url:'roleUser/add',
		method:'post',
		data:{roleIds:roleIds,userIds:$userIdArr,allUser:allUser},
		dataType:'json',
		success : function(data){
			if(data.status){
				alert('发送成功！');
				// 请求消息数据事件
				requestData();
			}else{
				alert('发送失败，请选择要分配的角色和用户！');
			}
		},
		error:function(){
			alert('服务器异常，发送失败！')
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
		alert('请选择要解除的记录...');
		return;
	}
	$.ajax({
		url:'roleUser/batchDel',
		method:'post',
		data:{ids:$idArr},
		dataType:'json',
		success:function(data){
			if(data.status){
				alert('解除成功！');
				// 请求消息数据事件
				requestData();
			}
		},
		error:function(){
			alert('服务器异常，删除失败！');
		}
	});
}

