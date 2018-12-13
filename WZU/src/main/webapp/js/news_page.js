var conunt = 0;
$(function() {
	
	//分页栏数字单击事件
	 GG = {
       "kk":function(mm){
       	getData(mm);
       }
   }
	//请求数据
	 getData();
});

/**
 * 全部已读
 */
function allRead(){
	 if(confirm("是否将全部信息设置为已读？")){
		 $.ajax({
			 url : "sysMessage/allRead/",
			 method:'post',
			 dataType:'json',
			 success : function(data){
				 if(data.status){
					 alert('全部已读成功！');
					 getData();
				 }else{
					 alert('请先登录...');
					window.location.href="../login";
				 }
			 },
			 error : function(data){
				 alert('修改失败，服务器异常！');
			 }
		 });
	 }
}

/**
 * 全部清空
 */
function allClear(){
	 if(confirm("是否将全部消息清空？")){
		 $.ajax({
			 url : "sysMessage/allClear/",
			 method:'post',
			 dataType:'json',
			 success : function(data){
				 if(data.status){
					 alert('全部清空成功！');
					 getData();
				 }else{
					 alert('请先登录...');
					 window.location.href="../login";
				 }
			 },
			 error : function(data){
				 alert('修改失败，服务器异常！');
			 }
		 });
	 }
}

/**
 * 删除
 */
function del(){
	var length = $('input[type=checkbox]:checked').length;
	if(length==0){
		alert('请选择要删除的消息...');
		return;
	}
	if(confirm('确定删除'+length+'条消息吗？')){
		var $idArr = [];
		$.each($('input[type=checkbox]'),function(index,checkbox){
			if($(checkbox).is(':checked')){
				$idArr.push($(checkbox).val());
			}
		});
		$.ajax({
			url:'sysMessage/del',
			method:'post',
			data:{ids:$idArr},
			dataType:'json',
			success:function(data){
				if(data.status){
					alert('删除成功！');
					// 请求消息数据事件
					getData();
				}else{
					 alert('请先登录...');
					window.location.href="../login";
					 }
			},
			error:function(){
				alert('服务器异常，删除失败！');
			}
		});
		
	}
}

/**
 * 已读
 * @param messageId
 */
function readMessage(id){
	$.ajax({
		url:'sysMessage/read',
		data:{id:id},
		dataType:'json',
		method:'post',
		success:function(data){
			if(data.status){
				//设置图片为已读图片
				$('div[no='+id+']').parent().children('span').attr('class','news_read');
			}else{
				 alert('请先登录...');
					window.location.href="../login";
					 }
		},
		error:function(){
			console.log('服务器异常');
		}
	});
}



/**
 * 获取数据
 * @param currentPage
 */
function getData(currentPage){
	var pageIndex = 1; 
	if(currentPage){
		pageIndex = currentPage;
	}
	var userId = $('#userId').val();
	var pageSize = 15;
	$.get("sysMessage/myMessage/"+pageIndex,{userId:userId,pageSize:pageSize},function(data){
		//清空原先列表
		$('#listMessage').html('');
		//显示分页栏
		if(data.pageIndex){
			$("#page").initPage(data.recordCount,data.pageIndex,GG.kk);
		}
		
		result = {};
		
		//遍历数据集合
		$.each(data.list,function(index,systemMessage){
			result[systemMessage.id] = systemMessage;
			//将标签填充到页面
			$('#listMessage').append(createDOM(systemMessage));
		});
		choice();
	});
}

/**
 * 生成页面标签
 */
function createDOM(systemMessage){
	var li = $('<li><div class="checkbox_course"><input type="checkbox" name="check-box" value="'+ systemMessage.id +'" /></div></li>');
	var divUnread = $('<div class="npr_unread"><img src="img/np_unread.png" /></div>');
	var divRead = $('<div class="npr_unread"><img src="img/np_read.png" /></div>');
	var text = $('<div  style="cursor:pointer" class="npr_text" onclick="details('+ systemMessage.id +')" no="'+systemMessage.id+'"><p>'+ systemMessage.title +'</P></div>');
	var textR = $('<div  style="cursor:pointer" class="npr_text_r" onclick="details('+ systemMessage.id +')" no="'+systemMessage.id+'"><p>'+ systemMessage.title +'</P></div>');
	var date = $('<div class="npr_date"><p>'+ dateFormat(new Date(systemMessage.sendTime),"yyyy-MM-dd") +'</p></div>');
	var dateR = $('<div class="npr_date_r"><p>'+ dateFormat(new Date(systemMessage.sendTime),"yyyy-MM-dd") +'</p></div>');
	var sign = $('<div class="npr_sign"><p>【未读】</p></div>');
	var signR = $('<div class="npr_sign_r"><p>【已读】</p></div>');
	var clearfixDiv = $('<div class="clearfix"></div>');
		if(conunt == 1){
			var li = $('<li><div class="checkbox_course"><div class="check-box checkedBox"><i><input type="checkbox" name="check-box" checked="checked"></i></div></div></li>');
			var nprReturn = $('<div class="npr_return"><a href="sysMessage">【返回类表】</a></div>');
			return li.append(divRead).append(textR).append(dateR).append(signR).append(nprReturn).append(clearfixDiv);
		}
		if(systemMessage.status==0){
			//未读
			return li.append(divUnread).append(text).append(date).append(sign).append(clearfixDiv);
		}else{
			//已读
			return li.append(divRead).append(textR).append(dateR).append(signR).append(clearfixDiv);
	}
	
}

//input样式
function choice(){
	$('input[name="check-box"]').wrap('<div class="check-box"><i></i></div>');
	$.fn.toggleCheckbox = function () {
	    this.attr('checked', !this.attr('checked'));
	}
	$('.check-box').on('click', function () {
	    $(this).find(':checkbox').toggleCheckbox();
	    $(this).toggleClass('checkedBox');
	});
}

//查看细节
function details(that){
	readMessage(that);
	$('.npr_contents').html('');
	//填充数据
	conunt = 1;
	detailsDOM(result[that])
}

/**
 * 生成页面标签（详细信息）
 * @param message
 */
function detailsDOM(systemMessage){
	var div = $('<div class="npr_news"></div>');
	var ul = $('<ul class="list-unstyled"></ul>');
	var div2 = $('<div class="nd_contents"></div>');
	var content = $('<p>'+ systemMessage.content +'</p>');
	
	$('.npr_contents').append(div.append(ul.append(createDOM(systemMessage)))).append(div2.append(content));
}

/**
 * 跳转管理员消息页面
 */
function sa(){
	window.location.href="./managerMessage" ;
}


/**
 * 日期格式化
 * 
 * @param date
 * @param pattern
 * @returns {String}
 */
function dateFormat(date, pattern) {
	var $dateString;
	var $monthStr;
	if (date.getMonth() + 1 < 10) {
		$monthStr = '0' + (date.getMonth() + 1);
	} else {
		$monthStr = date.getMonth() + 1;
	}
	var $dateStr;
	if (date.getDate() < 10) {
		$dateStr = '0' + date.getDate();
	} else {
		$dateStr = date.getDate();
	}
	var $hour;
	if(pattern == "yyyy-MM-dd HH:mm"){
		$dateString = '' + date.getFullYear() + '-' + $monthStr + '-'
		+ $dateStr + " " + date.getHours() + ":" + date.getMinutes();
	}
	
	if (pattern == "yyyy-MM-dd") {
		$dateString = '' + date.getFullYear() + '-' + $monthStr + '-'
				+ $dateStr;
	} else if (pattern == "yyyyMMdd") {
		$dateString = '' + date.getFullYear() + $monthStr + $dateStr;
	} 
	if (pattern == "yyyy") {
		$dateString = '' + date.getFullYear();
	} else if (pattern == "yyyy") {
		$dateString = '' + date.getFullYear();
	}
	if (pattern == "MM") {
		$dateString = '' + $monthStr;
	} else if (pattern == "MM") {
		$dateString = '' + $monthStr;
	}
	return $dateString;
}