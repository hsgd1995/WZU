$(function() {
	$code = null;// 全局变量保存验证码
	loginValidate();
	userLogin();
	getCodeFromSession();
	
	$('#register').unbind().click(function(){
		window.location.href = "register";
	});
});

/**
 * bootstrap表单验证
 */
function loginValidate() {
	$('form').bootstrapValidator(
					{
						feedbackIcons : {
							valid : 'glyphicon glyphicon-ok',
							invalid : 'glyphicon glyphicon-remove',
							validating : 'glyphicon glyphicon-refresh'
						},
						fields : {
							loginName : {
								validators : {
									notEmpty : {
										message : '用户名不能为空'
									},
									stringLength : {
										max : 18,
										message : '用户名不能超过18个字符'
									}
								}
							},
							password : {
								validators : {
									notEmpty : {
										message : '密码不能为空'
									},
									stringLength : {
										min : 6,
										max : 18,
										message : '密码长度必须在6到18位之间'
									},
									regexp : {
										regexp : /^[a-zA-Z0-9_]+$/,
										message : '密码只能包含大写、小写、数字和下划线'
									}
								}
							},
							loginCode : {
								validators : {
									callback : {
										message : '验证码不正确',
										callback : function(value, validator) {
											var $inputCode = $(
													'input[name=loginCode]').val();
											return $code
													&& $inputCode
													&& $code
															.toLocaleUpperCase() == $inputCode
															.toLocaleUpperCase();
										}
									}
								}
							}
						}
					}).on('success.form.bv', function(e) {
				// 阻止默认提交
				e.preventDefault();
				// ajax提交表单
				userLogin();
			});
}

function userLogin() {
	$("#login").unbind().click(function() {
		var $formParams = getParams($('form').serializeArray());
		//console.log($formParams);
		$.ajax({
			url : "userLogin",
			method : 'post',
			data : $formParams,
			dataType : 'json',
			success : function(resp) {
				if (resp.message) {
					//console.log(resp.message);
					/*window.location.href = "learning_process";*/
					
					//不使用window.history.back(-1); 该方式会出现bug，即登录后，在用户信息栏不出现用户信息，
					//原因：该方式为浏览器返回上一个页面，而上一个页面是未登录的，不会存在用户信息
					var last = document.referrer;
					//window.history.back(-1); 
					//重新请求上一次页面，由于已经登录，所以存在用户信息
					if(last.indexOf("login")==-1&&last!=""&&last.indexOf("register")==-1){
						window.location.href = last;
					}else{
						window.location.href = "index";
					}
					
				} else { 
					//console.log("false");
					$('#prompt').removeClass('hide');
				}
			}
		})
		}
	);
}

/**
 * 验证码
 */
function getCodeFromSession() {
	$.ajax({
		url : 'getCode/getSession',
		async : false,
		success : function (data) {
			if(data.code){
				$code = data.code;
				//console.log($code);
			}
		}
	});
}


/**
 * 点击更换验证码
 */
function getCode(that) {
	that.src='getCode?d=' + Math.random();
	getCodeFromSession();
}
