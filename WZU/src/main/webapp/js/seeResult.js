// 记录课程ID
var courseVideoId = $('#courseVideoId').val();
//获取之前用户选项
var answer = $('#answer').val();
$(function() {
	var as = answer.replace(/-/g,'"');
	submitAnswer(as);
});

function submitAnswer(str) {
	var jsonAnswer = JSON.stringify(str);
	$.ajax({
		url : '../cq/' + courseVideoId + '/getResult',
		method : 'post',
		data : {
			// keyword : keyword,
			answer : str
		},
		dataType : 'json',
		success : function(data) {
			fillResultModel(data.map, data.newmap);
		}
	})
}

//显示答题结果信息
function fillResultModel(map, newmap) {
	// 选择题
	var answerCQ = map.listCQ;
	var strCQ = newmap.errorAnsewrCQ;
	var errorAnsewrCQ = JSON.parse(strCQ)
	// 判断题
	var answerJP = map.listJP;
	var strJP = newmap.errorAnsewrJP;
	var errorAnsewrJP = JSON.parse(strJP);
	// 填空题
	var answerCL = map.listCL;
	var strCL = newmap.errorAnsewrCL;
	var errorAnsewrCL = JSON.parse(strCL);
	var max = answerCQ.length + answerJP.length + answerCL.length;
	fillViceId(max);
	//得分
	var fra = newmap.fraction;
	var fraction = parseFloat(fra).toFixed(1);
	document.getElementById("fraction").innerHTML = "得分：" + fraction;
	// 填充选择题结果
	var i = 0;
	if (answerCQ != null) {
		for (i; i < answerCQ.length; i++) {
			var errorRecord = errorAnsewrCQ[answerCQ[i].id];
			var timu = fillResultCQ(i, answerCQ[i], errorRecord);
			$("#Result").append(timu);
		}
	}
	// 填充判断题结果
	var j = 0;
	if (answerJP != null) {
		for (j; j < answerJP.length; j++) {
			var errorRecord = errorAnsewrJP[answerJP[j].id];
			var timu = fillResultJP(j, i, answerJP[j], errorRecord);
			$("#Result").append(timu);
		}
	}
	// 填充判断题结果
	var m = 0;
	if (answerCL != null) {
		for (var m = 0; m < answerCL.length; m++) {
			var errorRecord = errorAnsewrCL[answerCL[m].id];
			var timu = fillResultCL(m, i + j, answerCL[m], errorRecord);
			$("#Result").append(timu);
		}
	}
}

//显示右侧对应的测试题序号
function fillViceId(max) {
	for (var i = 0; i < max; i++) {
		$("#ViceId").append(
				'<li style ="border-color:red; color:red;" onclick="lockingSubject('
						+ i + ')"> ' + (i + 1) + '</li>');
	}
	$("#ViceId").append('<div class="clearfix"></div>');
}

//拼接选择题样式及内容
function fillResultCQ(index, question, errorRecord) {
	var titleNo = index + 1;
	var $li = $('<li></li>');
	var $div_result_list = $('<div class="result_list"></div>');
	var $dl = $('<dl><dt>' + titleNo + '.  ' + question.title + '</dt></dl>');
	if (question.typeChoice == 0) {
		for (var i = 0; i < 4; i++) {
			var newoption = SplicingSingleOption(i, question, errorRecord);
			$dl.append(newoption);
		}

	} else {
		for (var i = 0; i < 4; i++) {
			var newoption = SplicingDoubleOption(i, question, errorRecord);
			$dl.append(newoption);
		}
	}
	return $li.append($div_result_list.append($dl));
}

// 拼接单选题选项
function SplicingSingleOption(i, question, errorRecord) {
	var option = [ "A 、" + question.optionOne, "B 、" + question.optionTwo,
			"C 、" + question.optionThree, "D 、" + question.optionFour ];
	var $dd = $('<dd></dd>');
	var $div_unchoice = $('<div class="result_box" data-state="unchoice">');
	var $div_checked = $('<div class="result_box" data-state="checked">');
	var $div_unchecked = $('<div class="result_box" data-state="unchecked">');
	var divOption = $('<div class="option"><p>' + option[i] + '</p></div>');
	var imgRight = $('<div class="right_img"><img src="../img/right_img.png"></div>');
	var imgError = '<div class="right_img"><img src="../img/error_img.png"></div>';
	var divClearfix = $('<div class="clearfix"></div>');
	if (errorRecord != null && errorRecord == (i + 1)) {
		return $dd.append($div_unchecked.append(divOption).append(imgError))
				.append(divClearfix);
	}
	if (question.answer == (i + 1)) {
		return $dd.append($div_checked.append(divOption).append(imgRight))
				.append(divClearfix);
	}
	return $dd.append($div_unchoice.append(divOption).append(imgRight)).append(
			divClearfix);

}

// 拼接多选题选项
function SplicingDoubleOption(i, question, errorRecord) {
	var option = [ "A 、" + question.optionOne, "B 、" + question.optionTwo,
			"C 、" + question.optionThree, "D 、" + question.optionFour ];
	var $dd = $('<dd></dd>');
	var $div_checked = $('<div class="result_box" data-state="checked">');
	var $div_unchecked = $('<div class="result_box" data-state="unchecked">');
	var $div_unchoice = $('<div class="result_box" data-state="unchoice">');
	var div1 = '<div class="checkbox_course"><div class="check-box"><i><input type="checkbox" name="check-box"></i></div>';
	var div2 = '<div class="checkbox_course"><div class="check-box checkedBox"><i><input type="checkbox" name="check-box" checked="checked"></i></div></div>';
	var divOption = '<div class="option"><p>' + option[i] + '</p></div>';
	var imgRight = '<div class="right_img"><img src="../img/right_img.png"></div>';
	var imgError = '<div class="right_img"><img src="../img/error_img.png"></div>';
	var divClearfix = '<div class="clearfix"></div>';

	if (question.answer.indexOf(i + 1) != -1) {
		return $dd.append(
				$div_checked.append(div2).append(divOption).append(imgRight))
				.append(divClearfix);
	}
	if (errorRecord != null && errorRecord.indexOf(i + 1) != -1) {
		return $dd.append(
				$div_unchecked.append(div2).append(divOption).append(imgError))
				.append(divClearfix);
	}
	return $dd.append(
			$div_unchoice.append(div1).append(divOption).append(imgRight))
			.append(divClearfix);

}

// 拼接判断题样式及内容
function fillResultJP(index, max, question, errorRecord) {
	var titleNo = index + max + 1;
	var $li = $('<li></li>');
	var $div_result_list = $('<div class="result_list"></div>');
	var $dl = $('<dl><dt>' + titleNo + '  .' + question.title + '</dt></dl>');
	for (var i = 0; i < 2; i++) {
		var newoption = SplicingOptionJP(i, question, errorRecord);
		$dl.append(newoption);
	}

	return $li.append($div_result_list.append($dl));

}

// 拼接判断题选项
function SplicingOptionJP(i, question, errorRecord) {
	var option = [ "  对", "  错" ];
	var $dd = $('<dd></dd>');
	var $div_unchoice = $('<div class="result_box" data-state="unchoice">');
	var $div_checked = $('<div class="result_box" data-state="checked">');
	var $div_unchecked = $('<div class="result_box" data-state="unchecked">');
	var divOption = $('<div class="option"><p>' + option[i] + '</p></div>');
	var imgRight = $('<div class="right_img"><img src="../img/right_img.png"></div>');
	var imgError = '<div class="right_img"><img src="../img/error_img.png"></div>';
	var divClearfix = $('<div class="clearfix"></div>');
	if (errorRecord != null && errorRecord == i) {
		return $dd.append($div_unchecked.append(divOption).append(imgError))
				.append(divClearfix);
	}
	if (question.answer == i) {
		return $dd.append($div_checked.append(divOption).append(imgRight))
				.append(divClearfix);
	}
	return $dd.append($div_unchoice.append(divOption).append(imgRight)).append(
			divClearfix);
}

// 拼接填空题样式以及内容
function fillResultCL(index, max, question, errorRecord) {
	var titleNo = index + +max + 1;
	var title = question.title.split('*');
	var $li = $('<li></li>');
	var $div_result_list = $('<div class="result_list"></div>');
	var $dl = $('<dl></dl>');
	var $dt = $('<dt>' + titleNo + '  .' + title[0]
			+ '<span class="result_before"> ' + errorRecord + '</span>'
			+ title[1] + '</dt>');
	var $dt2 = $('<dt>' + titleNo + '  .' + title[0]
			+ '<span class="result_after"> ' + question.answer + '</span>'
			+ title[1] + '</dt>');
	var dd = $('<dd><div class="fill"><p>正确答案：<span class="result_after">'
			+ question.answer + '</span></p></div></dd>');

	if (errorRecord != null) {
		return $li.append($div_result_list.append($dl.append($dt).append(dd)));
	}
	return $li.append($div_result_list.append($dl.append($dt2)));
}

//获取json长度
function getJsonLength(jsonData) {
	var jsonLength = 0;
	if (jsonData != null || jsonData != '') {
		for ( var item in jsonData) {
			jsonLength++;
		}
	}
	return jsonLength;
}