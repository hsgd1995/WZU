$(function() {
	registerValidate();
	
	$('input[name = phoneNumber]').blur(function () {
		var that = $(this);
		//phone_validate(that);
	});
	
	getCode();
	
	$('#login').unbind().click(function(){
		window.location.href = "login";
	});
});

function registerValidate() {
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
								min  : 6,
								max : 18,
								message : '用户名长度必须在6到18位之间'
							},
				            regexp: {
		                        regexp: /^[a-zA-Z0-9_]+$/,
		                        message: '用户名只能包含大写、小写、数字和下划线'
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
					confirm : {
						validators : {
							notEmpty : {
								message : '确认密码不能为空'
							},
							identical: {// 相同
		                         field: 'password',
		                         message: '两次密码不一致'
		                     }
						}
					},
					username : {
						validators : {
							notEmpty : {
								message : '姓名不能为空'
							},
						}
					},
					/*phoneNumber : {
						validators : {
							notEmpty : {
								message : '手机号不能为空'
							},
							regexp : {
								regexp : /^[0-9_]+$/,
								message : '手机号只允许输入数字'
//		                        regexp: /^1[3|5|8]{1}[0-9]{9}$/,
//		                        message: '请输入正确的手机号码'
							} 
						}
					},*/
					/*code : {
						validators : {
							notEmpty : {
								message : '验证码不能为空'
							},
							regexp : {
								regexp : /^[0-9_]+$/,
								message : '验证码只允许输入数字'
							},
							stringLength : {
								min : 6,
								max : 6,
								message : '验证码长度为6'
							}
						}
					},*/
					email : {
						validators : {
							notEmpty : {
								message : '邮箱不能为空'
							},
							emailAddress: {
		                         message: '请输入正确的邮件地址如：123@qq.com'
		                    }
						}
					}
/*					cardId : {
						validators : {
							notEmpty : {
								message : '身份证号码不能为空'
							},
	                        stringLength: {
	                            min: 15,
	                            max: 18,
	                            message: '身份证不少于15位,不高于18位'
	                        },
	                        remote: {
	                            type: 'GET',
	                            //以get方式调用接口根据接口返回的valid,true为通过false为未通过
	                            url: 'idCardValidate',
	                            message: '身份证不合法或该ID已注册',
	                            delay: 500
	                        }
						}
					}*/
				}
			}).on('success.form.bv', function(e) {
		// 阻止默认提交
		e.preventDefault();
		// ajax提交表单
		submitHandler();
	});
}

function submitHandler() {
	$('#regist').unbind().click(function(e) {
		// 阻止默认提交
		e.preventDefault();
	// 获得验证码session
	$.ajax({
		url : 'getCodeSession',
		success : function(code) {
			/*var getCode = $('input[name = code]').val();
			if(getCode != code){
				alert('验证码错误');
			} else {
				submitForm();
			}*/
			submitForm();
		}
	});
	});
}

function submitForm() {
	
	var $params = getParams($('form').serializeArray());
	//console.log($params);
	$.ajax({
		url : "registerUser",
		method : 'post',
		data : $params,
		dataType : 'json',
		success : function(resp) {
			if (resp.status) {
				alert(resp.message);
				window.location.href = "login";
			}
		}
	});
	
}

function phone_validate(that) {
	var obj = $('#getCode');
	$.ajax({
		url : 'phoneValidate',
		data : {
			phoneNumber : that.val()
		},
		dataType : 'json',
		success : function(data) {
			if (data.status) {
				$('.text-danger:eq(0)').show();
				dissabled(obj);
			} else {
				$('.text-danger:eq(0)').hide();
			}
		}
	});
}
var countdown=60; 

function getCode() {
	$('#getCode').unbind().click(function (e) {
		// 阻止默认提交
		e.preventDefault();
		var $phone = $('input[name = phoneNumber ]').val();
		var obj = $('#getCode'); 
//		settime(obj);
		$.ajax({
			 url : 'getCode/'+ $phone +'/SendCheckCode',
			 data : $phone,
			 dataType : 'json',
			 success : function(data) {
			 if (data.status) {
			 alert("发送成功");
			 settime(obj);
			 } else {
			 alert("发送失败");
			 }
			 if(data.message){
				 alert("每日发送次数不能超过五次");
			 }
			 }
		});
	});
}

function settime(obj) { // 发送验证码倒计时
    if (countdown == 0) { 
        obj.attr('disabled',false); 
        // obj.removeattr("disabled");
        obj.val("获取验证码");
        countdown = 60; 
        return;
    } else { 
        obj.attr('disabled',true);
        obj.val("重新发送(" + countdown + ")");
        countdown--; 
    } 
setTimeout(function() { 
    settime(obj) }
    ,1000) 
}

function dissabled(obj){
	 obj.attr('disabled',true);
}