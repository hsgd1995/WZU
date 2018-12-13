$(function() {
	getSession();
	menueBind();
	outlogin();
});

/**
 * 请求服务器session
 */
function getSession() {
	$.get('/getSession', function(data) {
		var $userId = data.id;
		$('#id').text(data.id);
		$('#username').text(data.username);
	});
}

/**
 * 左侧菜单栏事件绑定
 */
function menueBind() {
	$('#menu a').unbind().click(function() {
		// 在主显示区加载页面
		if($(this).attr('name') != null){
			getHtml($(this).attr('name'));
		}
	});
}

/**
 * 在主显示区加载页面
 * 
 * @param {Object}
 *            
 */
function getHtml(pageName) {
	$.get('main/' + pageName, function(html) {
		$('#main').html(html);
	});
}

function outlogin(){
	$(".outlogin").click(function(){ 
		if (confirm("确定要退出吗？")){
			window.location.href = 'outlogin';
		}
	});
}

