var privacy; // 隐私
var atSchool;// 是否在校
var school;// 院校名称
var major;// 专业
var grade;// 年级
var address;// 通讯地址
var userName;// 用户昵称
var phoneNumber;// 手机号
var sex;// 性别
var education;// 学历
var entrance;// 入学时间
var birthday;// 出生年月日
var personalProfile;// 个人简介
$(function() {
	$(".dropdown_wrap dd").click(function() {
		var lic = $(this).text();
		$(this).addClass("bg_red").siblings().removeClass("bg_red");
		$(this).parent().parent().prev().children("span").text(lic);
	});
	getMyUserSession();
});

function getMyUserSession() {
	$.ajax({
		url : '/HXCL/getUserSession',
		success : function(userSession) {
			// console.log(userSession);
			if (userSession.username != null) {
				$('#userId').text(userSession.id);
				$('#loginName').text(userSession.loginName);
				$('#userName').attr("value", userSession.username);
				$('#pname').text(userSession.username);
				$('#phoneNumber').attr("value", userSession.phoneNumber);
				$('#userPic').attr("src",userSession.userPic);
				userName = userSession.username;
				phoneNumber = userSession.phoneNumber;
			}
		}
	});
	
	$.ajax({
		url : 'mySetUp/getUserInfo',
		dataType : 'json',
		success : function(data) {
			fillUserInfo(data);
		}
	});
}

function fillUserInfo(data){
	if(data.privacy == "所有人"){
		 $("input[name=privacy]")[0].checked = true;
	} else{
		$("input[name=privacy]")[1].checked = true;
	}
	if(data.atSchool == "在校生"){
		 $("input[name=school]")[0].checked = true;
	} else {
		$("input[name=school]")[1].checked = true;
	}
	$('#education')[0].childNodes[0].innerHTML = data.education;
	if(data.school != "undefined"){
		$("input[name=schoolName]").attr("value",data.school);
	}
	if(data.major != "undefined"){
		$("input[name=major]").attr("value",data.major);
	}
	if(data.grade != "undefined"){
		$("input[name=grade]").attr("value",data.grade);
	}
	 var oDate = new Date(data.entrance);
	$('#entranceYear')[0].childNodes[0].innerHTML = oDate.getFullYear();
	$('#entranceMonth')[0].childNodes[0].innerHTML = oDate.getMonth()+1;
	
	$("input[name=address]").attr("value",data.address);
	
	if(data.sex == "男"){
		 $("input[name=sex]")[0].checked = true;
	} else{
		if(data.sex == "女"){
			 $("input[name=sex]")[1].checked = true;
		} else{
			 $("input[name=sex]")[2].checked = true;
		}
	}
	
	var birthday = new Date(data.birthday);
	$('#birthdayYY')[0].childNodes[0].innerHTML = birthday.getFullYear();
	$('#birthdayMM')[0].childNodes[0].innerHTML = birthday.getMonth() + 1;
	$('#birthdayDD')[0].childNodes[0].innerHTML = birthday.getDate();
	
	$('#personalProfile').text(data.personalProfile);
	
}

/**
 * 验证表单(提交表单之前先验证)
 */
function subUserInfo() {
	// 隐私
	privacy = $("input[name=privacy]:radio:checked")[0].parentNode.nextElementSibling.innerHTML;
	// 是否在校生
	atSchool = $("input[name=school]:radio:checked")[0].parentNode.nextElementSibling.innerHTML;
	if (atSchool == "在校生") {
		school = $("input[name=schoolName]")[0].value;
		if (school == "") {
			alert("请输入所在院校！！！");
			return;
		}
		major = $("input[name=major]")[0].value;
		if (major == "") {
			alert("请输入所在专业！！！");
			return;
		}
		grade = $("input[name=grade]")[0].value;
		if (grade == "") {
			alert("请输入所在年级！！！");
			return;
		}
	}
	address = $("input[name=address]")[0].value;
	if (address == "") {
		alert("请输入通讯地址！！！");
		return;
	}
	var newuserName = $('#userName')[0].value;
	if (newuserName == "") {
		alert("昵称不能为空！！！");
		return;
	}
	var nwephoneNumber = $('#phoneNumber')[0].value;
	if (newuserName == "") {
		alert("手机号不能为空！！！");
		return;
	}
	// 性别
	sex = $("input[name=sex]:radio:checked")[0].parentNode.nextElementSibling.innerHTML;
	education = $('#education')[0].childNodes[0].innerHTML;
	if (education == "--请选择--") {
		alert("请选择学历！！！");
		return;
	}
	// 出生年月日
	var birthdayYY = $('#birthdayYY')[0].childNodes[0].innerHTML;
	var birthdayMM = $('#birthdayMM')[0].childNodes[0].innerHTML;
	var birthdayDD = $('#birthdayDD')[0].childNodes[0].innerHTML;
	birthday = birthdayYY + "-" + birthdayMM + "-" + birthdayDD;
	// 入学时间
	var entranceYear = $('#entranceYear')[0].childNodes[0].innerHTML;
	var entranceMonth = $('#entranceMonth')[0].childNodes[0].innerHTML;
	entrance = entranceYear + "-" + entranceMonth + "-01";
	// 个人简介
	personalProfile = $('#personalProfile').val();
	// 保存信息到json
	addUserInfo();
}
/**
 * 提交
 */
function addUserInfo() {
	var formData = new FormData();
	formData.append("userId", $('#userId').text());
	formData.append("privacy", privacy);
	formData.append("atSchool", atSchool);
	formData.append("school", school);
	formData.append("major", major);
	formData.append("grade", grade);
	formData.append("address", address);
	formData.append("sex", sex);
	formData.append("education", education);
	formData.append("birthday", birthday);
	formData.append("entrance", entrance);
	formData.append("personalProfile", personalProfile);

	$.ajax({
		url : "userInfo/saveUserInfo",
		method : 'post',
		data : formData,
		dataType : 'json',
		// 告诉jQuery不要去处理发送的数据
		processData : false,
		// 告诉jQuery不要去设置Content-Type请求头
		contentType : false,
		success : function(data) {
			if (data) {
				addUser();
			}
		},
		error : function() {
			alert('服务器异常，保存失败');
		}
	})
}

function addUser() {
	var newuserName = $('#userName')[0].value;
	var nwephoneNumber = $('#phoneNumber')[0].value;
	if (newuserName == userName && nwephoneNumber == phoneNumber) {
		alert('用户信息更新成功');
		location.reload([true]);
	} else {
		var UserformData = new FormData();
		UserformData.append("id",$('#userId')[0].innerHTML);
		UserformData.append("username", newuserName);
		UserformData.append("phoneNumber", nwephoneNumber);
		$.ajax({
			url : "userInfo/updateUser",
			method : 'post',
			data : UserformData,
			dataType : 'json',
			// 告诉jQuery不要去处理发送的数据
			processData : false,
			// 告诉jQuery不要去设置Content-Type请求头
			contentType : false,
			success : function(data) {
				if (data) {
					getSession();
					alert('用户信息更新成功');
					location.reload([true]);
				}
			},
			error : function() {
				alert('服务器异常，保存失败');
			}
		})
	}

}

/**
 * 请求服务器session
 */
function getSession() {
	$.get('/HXCL/getUserSession', function(data) {
		$('#username').text(data.username);
	});
}