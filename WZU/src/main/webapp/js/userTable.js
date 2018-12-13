/**
 * 用户表格工具
 */
$(function (){
	object = {};
	//初始化用户搜索框
	initUsersearch();
	//初始化模态框
	initEditModal();
	//用户数据
	getUserList();
	
	//查询按钮事件
	$('#uesrTable-select').unbind().click(function(e){
		// 取消默认事件
		e.preventDefault();
		//用户数据
		getUserList();
	});
});

/**
 * 初始化参数<br>
 * url:请求用户数据的url<br>
 * pageSize:每页记录数，默认为100<br>
 * pageIndex:页码，默认为1
 */
function InitParam(params){
	
	if(params){
		object = params;
	}
}



/**
 * 初始化用户搜索框
 */
function initUsersearch(){
	var $div = $('<div style="text-align: center;"></div>');
	var $checkboxCurrent = $('<span>选择当前页：</span><input type="checkbox" id="selectCurrentPageUser"> ');
	var $checkboxAll = $('<span style="margin-left:20px;">选择所有用户：</span><input type="checkbox" id="selectAllUser">');
	var $input = $('       <span style="margin-left:20px;">用户名：</span><input id="username" value="" placeholder="请输入用户名..."style="width: 150px; display: inline; padding: 3px 4px;">');
	var $button = $('<button style="margin-bottom: 5px"class="btn btn-xs btn-primary" id="uesrTable-select">查询</button>');
	$('#userTable-search').append($div.append($checkboxCurrent).append($checkboxAll).append($input).append($button));
}


/**
 * 初始化模态框
 */
function initEditModal(){
	var $div1 = $('<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true"></div>');
	var $div2 = $('<div class="modal-dialog"></div>');
	var $div3 = $('<div class="modal-content"></div>');
	var $divHeader = $('<div class="modal-header"></div>');
	var $headerContent = $('<h2>用户信息</h2>');
	var $divBody = $('<div style="margin-left:10px;" class="modal-body"></div>');
	var $bodyTable = $('<table></table>');
	var $tableTr0 = $('<tr><td style="text-align:right;">用户id：</td><td style="width:165px;"><span style="width:165px; text-overflow: ellipsis;  -o-text-overflow: ellipsis; white-space:nowrap; overflow: hidden ; display:block; margin:0 auto;" title="" id="table-id"></span></td><td rowspan="5" ><img style="height:190px;width:290px; margin-right:0px" id="table-pic" src=""></img></td></tr>');
	var $tableTr1 = $('<tr><td style="text-align:right;">用户名：</td><td style="width:165px;"><span style="width:165px; text-overflow: ellipsis;  -o-text-overflow: ellipsis; white-space:nowrap; overflow: hidden ; display:block; margin:0 auto;" title="" id="table-username"></span></td></tr>');
	var $tableTr2 = $('<tr><td style="text-align:right;">电话号码：</td><td style="width:165px;"><span style="width:165px; text-overflow: ellipsis;  -o-text-overflow: ellipsis; white-space:nowrap; overflow: hidden ; display:block; margin:0 auto;" title="" id="table-tel"></span></td></tr>');
	var $tableTr3 = $('<tr><td style="text-align:right;">电子邮件：</td><td style="width:165px;"><span style="width:165px; text-overflow: ellipsis;  -o-text-overflow: ellipsis; white-space:nowrap; overflow: hidden ; display:block; margin:0 auto;" title="" id="table-email"></span></td></tr>');
	var $tableTr4 = $('<tr><td style="text-align:right;">地区：</td><td style="width:165px;"><span style="width:165px; text-overflow: ellipsis;  -o-text-overflow: ellipsis; white-space:nowrap; overflow: hidden ; display:block; margin:0 auto;" title="" id="table-area"></span></td></tr>');
	var $divFooter = $('<div class="modal-footer"></div>');
	var $footerContetn = $('<button id="closeModal" type="button" class="btn btn-default" data-dismiss="modal">关闭</button>');
	
	$('#userTable-userModal').append($div1.append($div2.append($div3.append($divHeader.append($headerContent)).append($divBody.append($bodyTable.append($tableTr0).append($tableTr1).append($tableTr2).append($tableTr3).append($tableTr4))).append($divFooter.append($footerContetn)))));
}


/**
 * 获取用户列表
 */	
function getUserList(pageIndex){
	var index = 1;
	if(pageIndex){
		index = pageIndex;
	}
	var keyword = $('input[id=username]').val();
	var url = 'user/' + index + '/getUserPageModel';
	if(object.url){
		url = object.url;
	}
	var pageSize = 100;
	if(object.pageSize){
		pageSize = object.pageSize;
	}
	if(object.pageIndex){
		index = object.pageIndex;
	}
	$.ajax({
		url : url,
		method : 'get',
		data : {
			pageSize:pageSize,
			userName:keyword
		},
		dataType : 'json',
		success : function(data) {
			fillUserList(data);
		},
		error : function(){
			console.log('请输入正确的url...');
		} 
	});
}

/**
 * 处理用户数据
 */
function fillUserList(data){
	$('#userTable-userList').html('');
	//用户数组
	userArray = [];
	table = $('<table style="width:100%;"></table>');
	var tr;
	$.each(data.list,function(index,object){
		if(index==0||(index)%10==0){
			tr = $('<tr></tr>');
			var checkBox = $('<td  style="border:solid 1px #1963AA;text-align:center; padding:10px 5px;"><input name="userCheck"  type="checkbox" value="'+index+'"></td>');
			tr = tr.append(checkBox);
		}
		userArray[object.id] = object;
		tr = tr.append(createUserDom(object));
		if(index==(data.list.length-1)||(index+1)%10==0){
			table = table.append(tr);
		}
	});
	
	var pageButton = $('<br><div class="row"><div class="col-sm-12" id="userPagination"></div></div>');
	$('#userTable-userList').append(table);
	$('#userTable-userList').append(pageButton);
		//绘制分页栏
		fillUserPageButton(data);
		// 分页栏点击事件
		onclickUserPageButton(data);
	
	//上层控制底层可以用两个修改.prop('checked',boolean).trigger('click');
	//底层控制上层只能用一个修改.prop('checked',boolean)
	//用户单元格点击事件
	$('.user').unbind().click(function (e) {
		// 取消默认事件
		e.preventDefault();
		changeBorder($(this));
		$(this).parent().parent().children(':first').children('input').prop('checked',false);
		if(!$(this).is(':checked')){
			$('#selectCurrentPageUser').prop('checked',false);
			$('#selectAllUser').prop('checked',false);
		}
	});
	
	
	//用户多选框点击事件
	$('td input[name=userCheck]').unbind().click(function(e){
			
		var userUnit = $(this).parent().parent().children().children('.user');
		if($(this).is(':checked')){
			userUnit.each(function(){
				$(this).parent().children('div').html('<i style="color:#007500; font-size:18px;" class="icon-ok-circle"></i>');
				$(this).attr('use','true');
			});
		}else{
			userUnit.each(function(){
				$(this).parent().children('div').html('');
				$(this).attr('use','false');
			});
			$('#selectCurrentPageUser').prop('checked',false);
			$('#selectAllUser').prop('checked',false);
		}
		
	});
	
	
	//当前页用户多选框的点击事件
	$('#selectCurrentPageUser').unbind().click(function(e){
		if(!$(this).is(':checked')){
			$('td input[name=userCheck]').prop('checked',true).trigger('click');
		}else{
			$('td input[name=userCheck]').prop('checked',false).trigger('click');
		}
		//$('td input[name=userCheck]').trigger('click');
		//当所有用户多选框被选中时，当前页用户多选框必须被选中
		if($('#selectAllUser').is(':checked')){
			//必须选中当前页用户多选框
			$(this).prop('checked',true).trigger('click');
		}
	});
	
	//所有用户多选框的点击事件
	$('#selectAllUser').unbind().click(function(e){
		if($(this).is(':checked')){
			if(!$('#selectCurrentPageUser').is(':checked')){
				//触发当前页用户多选框点击事件
				$('#selectCurrentPageUser').trigger('click');
			}
		}else{
			$('#selectCurrentPageUser').trigger('click');
		}
		
	});
	
	//每一次加载用户数据时，如果所有用户多选框被选中，则将所有用户单元格选中
	if($('#selectAllUser').is(':checked')){
		$('#selectCurrentPageUser').prop('checked',true).trigger('click');
	}else{
		
		//每次请求数据后把当前页多选框设为不选中
		//但如果所有用户多选框是被选中的，即使在这里把当前页用户多选框设为不选中状态，在每一次请求用户数据时也会被设为选中装态，这样的效果是合理的
		$('#selectCurrentPageUser').prop('checked',false);
	}
}

/**
 * 改变jq目标样式
 * @param target
 */
function changeBorder(target){
	if(target.attr('use')=='false'){
		//修改边框
		//target.attr('style','border:solid 4px #438EB9;text-align:center; padding:10px 10px;');
		target.parent().children('div').html('<i style="color:#007500; font-size:18px;" class="icon-ok-circle"></i>');
		target.attr('use','true');
	}else{
		//修改边框
		//target.attr('style','border:solid 1px #1963AA;text-align:center; padding:10px 10px;');
		target.parent().children('div').html('');
		target.attr('use','false');
	}
}

/**
* 生成用户标签
*/
function createUserDom(object){
	var td1 = $('<td id="'+object.id+'"  style="border:solid 1px #1963AA;text-align:center; padding:10px 10px; position:relative;"></td>');
	var username = $('<span  style="text-overflow: ellipsis;  -o-text-overflow: ellipsis; white-space:nowrap; width:70px; overflow: hidden ; display:block; margin:0 auto;" TITLE="'+object.username+'">'+'<a  style="z-index: 100;" href="javascript:void(0)" onclick="showUser('+object.id+')">'+object.username+'</a>'+'</span>');
	var img = $('<img class="user" use="false" style="height:70px;width:70px;" src="'+object.userPic+'"><br>');
	var div = $('<div style="height:14px;width:18px; position:absolute; top:1px; right:1px;"></div>');
	
	return td1.append(img).append(username).append(div);
}

/**
* 显示用户详细信息
*/
function showUser(id){
	$('#table-id').html(userArray[id].id);
	$('#table-id').attr('title',userArray[id].id);
	$('#table-username').html(userArray[id].username);
	$('#table-username').attr('title',userArray[id].username);
	$('#table-tel').html(userArray[id].phoneNumber);
	$('#table-tel').attr('title',userArray[id].phoneNumber);
	$('#table-email').html(userArray[id].email);
	$('#table-email').attr('title',userArray[id].email);
	$('#table-area').html(userArray[id].area);
	$('#table-area').attr('title',userArray[id].area);
	$('#table-pic').attr('src',userArray[id].userPic);
	
	$('#myModal').modal('show');
}

/**
* 绘制用户分页栏
* @param pageModel
*/
function fillUserPageButton(pageModel){
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
	$('#userPagination').append($ul).append($div);
}

/**
* 添加用户分页栏点击事件
* @param pageModel
*/
function onclickUserPageButton(pageModel){
	// 当前页
	var $pageIndex = pageModel.pageIndex;
	// 总页数
	var $totalSize = pageModel.totalSize;
	// 上页
	var $preIndex = $pageIndex - 1 > 0 ? ($pageIndex - 1) : 1;
	// 下页
	var $nextIndex = $pageIndex + 1 < $totalSize ? ($pageIndex + 1)
			: $totalSize;
	$('#userPagination ul li:eq(0) a').attr("onClick","userPageSeach(1)");
	$('#userPagination ul li:eq(1) a').attr("onClick","userPageSeach(" + $preIndex+")");
	$('#userPagination ul li:eq(3) a').attr("onClick","userPageSeach(" + $nextIndex+")");
	$('#userPagination ul li:eq(4) a').attr("onClick","userPageSeach(" + $totalSize+")");
}
/**
* 用户分页栏事件
* @param pageIndex
*/
function userPageSeach(pageIndex){
	getUserList(pageIndex);
}
