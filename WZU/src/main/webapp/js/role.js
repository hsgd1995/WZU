$(function() {
	index = 1;
	// 请求消息数据事件
	requestData(index);
	
	//保存按钮事件
	$('#saveRole').unbind().click(function (e) {
		// 取消默认事件
		e.preventDefault();
		//添加和更新
		addAndUpdate();
	});
	
	//批量停用按钮事件
	$('#batchDisable').unbind().click(function(e){
		// 取消默认事件
		e.preventDefault();
		//停用
		batchDisable();
	});
	
	//批量启用按钮事件
	$('#batchEnable').unbind().click(function(e){
		// 取消默认事件
		e.preventDefault();
		//启用
		batchEnable();
	});
	
	var setting = {
			view: {
				selectedMulti: false
			},
			check: {
				enable: true
			},
			data: {
				simpleData: {
					enable: true
				}
			},
			edit: {
				enable: true
			},
			callback: {
				onCheck: getOnCheck
			}
		};

	var t = new Array();
	$.ajax({
		url:'role/getAuthorityList',
		type:'get',
		dataType:'json',
		success:function(data){
			$.each(data.list,function(i,vo1){
				t.push({id:vo1.id,pId:0,name:vo1.name+"---"+vo1.state,open:false});
				$.each(vo1.list,function(j,vo2){
					t.push({id:vo2.id,pId:vo1.id,name:vo2.name+"---"+vo2.state,open:false});
					$.each(vo2.list,function(k,vo3){
						t.push({id:vo3.id,pId:vo2.id,name:vo3.name+"---"+vo3.state});
					});
				});
			});
			$.fn.zTree.init($("#treeDemo"), setting, t);
		}
	});
});

/**
 * 树形结构复选框点击事件
 * @param event
 * @param treeId
 * @param treeNode
 */
function getOnCheck(event, treeId, treeNode) {
	
}


/**
 * 请求消息数据事件
 */
function requestData(index) {
	if(index == null || index == 0){
		index = 1;
	}
	
	$.ajax({
		url : 'role/list',
		method : 'get',
		data : {
			pageIndex : index,
			pageSize : 8,
		},
		dataType : 'json',
		success : function(data) {
			index = data.pageIndex;
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
	roleArray = [];
	$.each(list,function(index,role){
		roleArray[role.id] = role;
		if(role.type == 0){
			role.type = "已停用";
		}else{
			role.type = "已启用";
		}
		$('.fill tbody').append(fillData(role));
	});
	// 清空分页栏
	$('#main #pagination').html('');
	
	$('#allCheckBox').prop('checked',false);
	
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
}

/**
 * 列表填充
 * 
 * @param role
 * @returns
 */
function fillData(role) {
	var $tr = $('<tr></tr>');
	var $td1 = $('<td style="text-align: center;"><input value="'+role.id+'" type="checkbox" /></td>');
	var $td2 = $('<td class="tieleOmit content">' + role.name + '</td>');
	var $td3 = $('<td class="contentOmit content">' + role.state + '</td>');
	var clazz = 'btn-success';
	if(role.type!='已启用'){
		clazz = 'btn-danger';
	}
	var $td4 = $('<td class=" content"><span class="'+clazz+'">' + role.type + '</span></td>');
	var $td5 = $('<td>'+ role.createTime+ '</td>');
	var $td6 = $('<td class="content">'+ role.createrId+ '</td>');
	var $td7 = $('<td><a href="#" no="'+role.id+'" class="editNews">编辑</a></td>');
	var $td8 = $('<td class="hide" name = "id">' + role.id + '</td>');
	return $tr.append($td1).append($td2).append($td3).append($td4).append($td5).append($td6).append($td7).append($td8);
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
 * 添加和更新
 */
function addAndUpdate(){
	
	
	var id = $('#roleForm .hide input[name = id]').val();
	var name = $('#roleForm input[name = name]').val();
	var state = $('#roleForm input[name = state]').val();
	var type = $('#roleForm select[name = type]').val();
	
	
	if(!name){
		alert('请填写角色名称...');
		return;
	}
	if(!state){
		alert('请填写备注说明...');
		return;
	}
	
	//权限数组
	authority = [];
	
    var treeObj=$.fn.zTree.getZTreeObj("treeDemo"),
    nodes=treeObj.getCheckedNodes(true);
    for(var i=0;i<nodes.length;i++){
    	authority.push(nodes[i].id);
    }
	
	var formData = new FormData();
	formData.append("id", id);
	formData.append("name", name);
	formData.append("state", state);
	formData.append("type", type);
	formData.append("authority[]",authority);
	if(id){
		if(type==0){
			alert('停用后，角色对应的账号将无法登录本系统！');
		}
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
		url:'role/update',
		method:'post',
		data:formData,
		dataType:'json',
		processData:false,
		contentType:false,
		success : function(data){
			if(data.status){
				alert('更新成功！');
				// 请求消息数据事件
				requestData(index);
			}else{
				alert('添加失败，请重新登录后再添加！');
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
		url:'role/add',
		method:'post',
		data:formData,
		dataType:'json',
		processData:false,
		contentType:false,
		success : function(data){
			if(data.status){
				alert('添加成功！');
				// 请求消息数据事件
				requestData(index);
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
 * 停用
 */
function batchDisable(){
	var $idArr = [];
	$.each($('.fill tbody input[type=checkbox]'),function(index,checkbox){
		if($(checkbox).is(':checked')){
			$idArr.push($(checkbox).val());
		}
	});
	if($idArr.length<=0){
		alert('请选择要停用的角色...');
		return;
	}
	
	if(!confirm('确定停用'+$idArr.length+'个角色吗，角色停用后，角色对应的账号将无法登录本系统！')){
		return;
	}
	
	$.ajax({
		url:'role/batchDisable',
		method:'post',
		data:{ids:$idArr},
		dataType:'json',
		success:function(data){
			if(data.status){
				alert('停用成功！');
				// 请求消息数据事件
				requestData(index);
			}else{
				alert('停用失败，请重新登录后再添加！');
			}
		},
		error:function(){
			alert('服务器异常，停用失败！');
		}
	});
	
}

/**
 * 启用
 */
function batchEnable(){
	var $idArr = [];
	$.each($('.fill tbody input[type=checkbox]'),function(index,checkbox){
		if($(checkbox).is(':checked')){
			$idArr.push($(checkbox).val());
		}
	});
	if($idArr.length<=0){
		alert('请选择要启用的角色...');
		return;
	}
	
	if(!confirm('确定启用'+$idArr.length+'个角色吗，角色停启用后，对应的账号即可登录本系统！')){
		return;
	}
	
	$.ajax({
		url:'role/batchEnable',
		method:'post',
		data:{ids:$idArr},
		dataType:'json',
		success:function(data){
			if(data.status){
				alert('启用成功！');
				// 请求消息数据事件
				requestData(index);
			}else{
				alert('启用失败，请重新登录后再添加！');
			}
		},
		error:function(){
			alert('服务器异常，启用失败！');
		}
	});
	
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
 * 分页栏事件
 * @param pageIndex
 */
function pageSeach(pageIndex){
	requestData(pageIndex);
}

/**
 * 编辑按钮事件
 */
function editForm() {
	$('.fill tbody a').unbind().click(function (e) {
		// 取消默认事件
		e.preventDefault();
		// 把改行数据填充到form表单中
		showRole(roleArray[$(this).attr('no')]);
	});
}

/**
 * 填充编辑框
 */
function showRole(data){
	$('#roleForm input[name = id]').val(data.id);
	$('#roleForm input[name = name]').val(data.name);
	$('#roleForm input[name = state]').val(data.state);
	$('#roleForm select[name = type]').val(data.type=='已启用'?1:0);
	$.ajax({
		url:'role/getRoleAuthority',
		data:{roleId:data.id},
		dataType:'json',
		success:function(data){
			var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
			$.each(data,function(i,object){
				var node = treeObj.getNodeByParam("id", object.authority.id);
				treeObj.checkNode(node,true,false);
			});
		}
	});
	
}

/**
 * 清空编辑框
 */
function clearFormFunc(){
	$('#roleForm input[name = id]').val('');
	$('#roleForm input[name = name]').val('');
	$('#roleForm input[name = state]').val('');
	$('#roleForm select[name = type]').val(1);
	var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
	if(treeObj!=null){
		treeObj.checkAllNodes(false);
	}
	
}