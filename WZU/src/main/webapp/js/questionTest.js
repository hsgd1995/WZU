// 秒表
var oclock = document.getElementById("clock");
var start1 = oclock.innerHTML;
var finish = "00:00:00:00";
var timer = null;
// 记录课程ID
var courseVideoId = $('#courseVideoId').val();
// 存放获取到的测试题的数量
var maxCQ = 0;
var maxJP = 0;
var maxCL = 0;
var max = 0;
// 存放用户选择的答案
var answer = $('#answer').val();
//
var conunt = 0;
//最开始锁定的题目
var listr = 0;
// 控制滑轮事件的执行
var is_running = false

$(function() {
	if (answer == "0") {
		getQuestion(courseVideoId);
		run();
	} else {
		alert("您已完成过该测试！！！");
		tiaozhuang();
	}
	gunlun();
});

function tiaozhuang() {
	window.location.href = "../result/" + courseVideoId;
}

function run() {// 定义时间函数，让秒表每100ms变化一次
	timer = setInterval("onTimer()", 100);// 100ms的定时器
}
function onTimer() {
	if (start1 == finish)// 如果倒计时结束清除时间函数
	{
		clearInterval(timer);
		start1 = "00:00:00:10";// (清除时间函数后还是会执行一次 所以多给一个10ms再动态赋值)
		alert("没有在规定时间内提交答案，本次测试不计入成绩！！！");
		window.history.back(-1);
	}

	var hms = new String(start1).split(":");// 以:作为分隔符号取字符串内的数据
	var ms = new Number(hms[3]);// 给每个数据定义对象
	var s = new Number(hms[2]);
	var m = new Number(hms[1]);
	var h = new Number(hms[0]);

	ms -= 10;// 每次执行ms减10

	if (ms < 0)// 判断时间并进行变化
	{
		ms = 90;
		s -= 1;
		if (s < 0) {
			s = 59;
			m -= 1;
		}
		if (m < 0) {
			m = 59;
			h -= 1;
		}
	}
	var ms = ms < 10 ? ("0" + ms) : ms;// 如果出现个位数给个位数前面添加0
	var ss = s < 10 ? ("0" + s) : s;
	var sm = m < 10 ? ("0" + m) : m;
	var sh = h < 10 ? ("0" + h) : h;
	start1 = sh + ":" + sm + ":" + ss + ":" + ms;
	oclock.innerHTML = start1;// 重新给oclock赋值
}

/**
 * 打开结果页面
 * 
 * @param that
 */
function openResult() {
	var answer = {};
	var answerCQ = {};
	var answerJP = {};
	var answerCL = {};
	SelectedOptionCQ(answerCQ);
	SelectedOptionJP(answerJP);
	SelectedOptionCL(answerCL);
	answer["answerCQ"] = answerCQ;
	answer["answerJP"] = answerJP;
	answer["answerCL"] = answerCL;
	var newmax = getJsonLength(answerCQ) + getJsonLength(answerJP)
			+ getJsonLength(answerCL);
	if (newmax != max) {
		alert("还有试题没有填写答案！！！");
		return;
	}
	submitAnswer(answer);
}

/**
 * 提交用户选项
 * 
 * @param answer
 */
function submitAnswer(answer) {
	var jsonAnswer = JSON.stringify(answer);
	$.ajax({
		url : '../cq/' + courseVideoId + '/getResult',
		method : 'post',
		data : {
			// keyword : keyword,
			answer : jsonAnswer
		},
		dataType : 'json',
		async : true,
		success : function(data) {
			tiaozhuang();
		}
	});
}

function getQuestion(courseVideoId) {
	// var keyword = null;
	$.ajax({
		url : '../cq/' + courseVideoId + '/getQuestions',
		method : 'post',
		data : {
			// keyword : keyword,
			courseVideoId : courseVideoId
		},
		dataType : 'json',
		success : function(data) {
			fillQuestionModel(data.map);
		}
	});
}

function fillQuestionModel(map) {
	var listCQ = map.listCQ;
	var listJP = map.listJP;
	var listCL = map.listCL;
	maxCQ = listCQ.length;
	maxJP = listJP.length;
	maxCL = listCL.length;
	max = maxCQ + maxJP + maxCL;
	fillViceId(maxCQ + maxJP + maxCL);
	if (listCQ != null) {
		$.each(listCQ, function(index, question) {
			$("#timu").append(fillCQ(index, question));
		});
	}
	if (listJP != null) {
		$.each(listJP, function(index, question) {
			$("#timu").append(fillJP(maxCQ, index, question));
		});
	}
	if (listCL != null) {
		$.each(listCL, function(index, question) {
			$("#timu").append(fillCL(maxCQ + maxJP, index, question));
		});
	}
	xiugai();
	lockingSubject(0);
}

// 拼接选择题试题
function fillCQ(index, question) {
	var titleNo = index + 1;
	var $li = $('<li></li>');
	var $div = $('<div class="problem_list"></div>');
	var h1 = '<h1>' + titleNo + '  .' + question.title + '</h1>';
	var $dl = $('<dl></dl>');
	var $dd = $('<dd></dd>');
	var div2 = '<div class="radio_course" onclick="choiceAnswer(this)" courseId="'
			+ index
			+ '"> <input sign="choiceQ" type="radio" class="rdo" name="course_'
			+ index + '" value="' + question.id + '" /></div>';
	var div3 = '<div class="checkbox_course" onclick="choiceAnswer(this)" courseId="'
			+ index
			+ '"> <input type="checkbox" modSty="check-box" name="course_'
			+ index + '" value="' + question.id + '"  /></div>';
	var p = '<p value = "1">A、' + question.optionOne
			+ '</p> <div class="clearfix"></div>';
	var p2 = '<p value = "2">B、' + question.optionTwo
			+ '</p> <div class="clearfix"></div>';
	var p3 = '<p value = "3">C、' + question.optionThree
			+ '</p> <div class="clearfix"></div>';
	var p4 = '<p value = "4">D、' + question.optionFour
			+ '</p> <div class="clearfix"></div>';
	if (question.typeChoice == '0') {
		return $li.append($div.append(h1).append(
				$dl.append($dd.append(div2).append(p)).append(
						$dd.append(div2).append(p2)).append(
						$dd.append(div2).append(p3)).append(
						$dd.append(div2).append(p4))));
	} else {
		return $li.append($div.append(h1).append(
				$dl.append($dd.append(div3).append(p)).append(
						$dd.append(div3).append(p2)).append(
						$dd.append(div3).append(p3)).append(
						$dd.append(div3).append(p4))));
	}
}

// 显示右侧对应的测试题序号
function fillViceId(max) {
	for (var i = 0; i < max; i++) {
		$("#ViceId").append(
				'<li class="ordinaryLi"  onclick="lockingSubject(' + i + ')">'
						+ (i + 1) + '</li>');
	}
	$("#ViceId").append('<div class="clearfix"></div>');
}

// 锁定指定题目
function lockingSubject(that) {
	if (conunt != 0) {
		return;
	}
	listr = that;
	var locking = document.getElementsByClassName("lockingLi")[0];
	if (locking != undefined && locking != "") {
		$(locking).addClass('ordinaryLi').removeClass('lockingLi');
	}
	var lis = document.getElementById("ViceId").getElementsByTagName("li");
	$(lis[that]).addClass('lockingLi').removeClass('ordinaryLi');
	var liarr = document.getElementById("shubiao").getElementsByTagName("li");
	if (that < "1") {
		$('.problem').animate({'scrollTop' : 0}, 250,function(){is_running = false;});
		$(".top_mask").addClass('none').siblings().removeClass('none');
	} else {
		if (that == "" + (max - 1)) {
			$('.problem').animate({'scrollTop' : that * 250}, 250,function(){is_running = false;});
			$(".bottom_mask").addClass('none').siblings().removeClass('none');
		} else {
			$('.problem').animate({'scrollTop' : (that - 1) * 250}, 250,function(){is_running = false;});
			$(".middle_mask").addClass('none').siblings().removeClass('none');
		}
	}
	$(liarr[that]).addClass('problem_o').siblings().removeClass('problem_o');
}

// 用户答题之后对应的题目(选择题、判断题)
function choiceAnswer(that) {
	var liIndex = $(that).attr("courseId");
	var lis = document.getElementById("ViceId").getElementsByTagName("li");
	lis[liIndex].style.color = "#0c89e2fc";
	lis[liIndex].style.borderColor = "#0c89e2fc";
}

// 用户答题之后对应的题目(填空题)
function choiceAnswerCL(that) {
	var liIndex = $(that).attr("idIndex");
	var lis = document.getElementById("ViceId").getElementsByTagName("li");
	if (that.value != "") {
		lis[liIndex].style.color = "#0c89e2fc";
		lis[liIndex].style.borderColor = "#0c89e2fc";
	} else {
		lis[liIndex].style.color = "red";
		lis[liIndex].style.borderColor = "red";
	}
}
// 拼接判断题试题
function fillJP(max, index, question) {
	var titleNo = index + max + 1;
	var $li = $('<li class="problem_o"></li>');
	var $div = $('<div class="problem_list"></div>');
	var h1 = '<h1>' + titleNo + '  .' + question.title + '</h1>';
	var $dl = $('<dl"></dl>');
	var $dd = $('<dd></dd>');
	var div2 = '<div class="radio_course" onclick="choiceAnswer(this)" courseId='
			+ (index + max)
			+ '> <input sign="judgmenJP" type="radio" class="rdo" name="course_'
			+ (index + max) + '" value="' + question.id + '" /></div>';
	var p = '<p value = "1"> 对 </p> <div class="clearfix"></div>';
	var p2 = '<p value = "0"> 错</p> <div class="clearfix"></div>';
	return $li.append($div.append(h1).append(
			$dl.append($dd.append(div2).append(p)).append(
					$dd.append(div2).append(p2))));
}
// 拼接填空题试题
function fillCL(max, index, question) {
	var titleNo = index + max + 1;
	var $li = $('<li class="problem_o"></li>');
	var $div = $('<div class="problem_list"></div>');
	var h1 = '<h1 value="'
			+ question.id
			+ '">'
			+ titleNo
			+ '  .'
			+ question.title
			+ ' 。<input name="Completion" type="text" onblur="choiceAnswerCL(this)" idIndex="'
			+ (titleNo - 1) + '"></h1>';
	return $li.append($div.append(h1));

}

// 获取用户选择的选择题选项
function SelectedOptionCQ(answer) {
	var checked = $("input[sign=choiceQ]:radio:checked");
	var checkbox = document.getElementsByClassName("checkedBox");
	if (checked.length != 0) {
		for (i = 0; i < checked.length; i++) {
			answer[checked[i].value] = checked[i].parentNode.nextSibling
					.getAttribute("value");
		}
	}
	if (checkbox.length != 0) {
		for (i = 0; i < checkbox.length; i++) {
			if (answer[checkbox[i].firstChild.firstChild.value] == null
					|| answer[checkbox[i].firstChild.firstChild.value] == undefined) {
				answer[checkbox[i].firstChild.firstChild.value] = checkbox[i].parentNode.nextSibling
						.getAttribute("value");
			} else {
				var val = answer[checkbox[i].firstChild.firstChild.value];
				answer[checkbox[i].firstChild.firstChild.value] = val
						+ ","
						+ checkbox[i].parentNode.nextSibling.getAttribute("value");
			}
		}
	}
}

// 获取用户判断题答案
function SelectedOptionJP(answer) {
	var checked = $("input[sign=judgmenJP]:radio:checked");
	if (checked.length != 0) {
		for (i = 0; i < checked.length; i++) {
			answer[checked[i].value] = checked[i].parentNode.nextSibling
					.getAttribute("value");
		}
	}
}
// 获取用户填空题答案
function SelectedOptionCL(answer) {
	var completions = $("input[name=Completion]");
	if (completions.length != 0) {
		for (i = 0; i < completions.length; i++) {
			if (completions[i].value != null && completions[i].value != '') {
				answer[completions[i].parentNode.getAttribute("value")] = completions[i].value;
			}
		}
	}
}
// 获取json长度
function getJsonLength(jsonData) {
	var jsonLength = 0;
	if (jsonData != null || jsonData != '') {
		for ( var item in jsonData) {
			jsonLength++;
		}
	}
	return jsonLength;
}

// 修改多选题默认样式
function xiugai() {
	$('input[modSty="check-box"]').wrap('<div class="check-box"><i></i></div>');
	$.fn.toggleCheckbox = function() {
		this.attr('checked', !this.attr('checked'));
	}
	$('.check-box').on('click', function() {
		$(this).find(':checkbox').toggleCheckbox();
		$(this).toggleClass('checkedBox');
	});
}

// 滚轮事件
function gunlun() {
	var problem = $('.problem');
	// 1.禁用div的滚轮事件
	$('.problem').mousewheel(function(e) {
		return false;
	});
	// 2.如果滚动条到底底部的时候 禁用window的滚轮滚动
	// 3.判断滚动的方向
	$(".problem,.mask_bg").mousewheel(function(e, delta) {
		var decoration = delta > 0 ? 'Up' : 'down';
		if (is_running == false) {
			is_running = true;
			if (decoration == "Up") {
				if (listr == 0) {
					listr = 0;
					lockingSubject(listr);
				} else {
					listr = listr - 1;
					lockingSubject(listr);
				}
			} else {
				if (listr == max) {
					listr = max;
					lockingSubject(listr);
				} else {
					listr = listr + 1;
					lockingSubject(listr);
				}
			}
		}
		return false;
	});
}