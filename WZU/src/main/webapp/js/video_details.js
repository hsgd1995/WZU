//用户id
var userId = null;
// 视频长度
var duration;
// 当前视频进度
var currentTime;
// 课程id
var courseId;
// 视频id
var courseVideoId;
// 提交视频进度的定时器
var t = null;
//
var contor = 0;
// 视频元素
var vid;
// // 默认提交间隔为6秒
// var intervalTime = 6000;
// 上一次提交的视频进度
var preCurrentTime = 0;
//
var kongzhiDiv = document.getElementById('kongzhiDiv');
// 记录上次视频播放进度
var process = 0;

$(function() {

	dealVideoProcess();

	var Accordion = function(el, multiple) {
		this.el = el || {};
		this.multiple = multiple || false;

		// Variables privadas
		var links = this.el.find('.link');
		// Evento
		links.on('click', {
			el : this.el,
			multiple : this.multiple
		}, this.dropdown)
	};

	Accordion.prototype.dropdown = function(e) {
		var $el = e.data.el;
		$this = $(this);
		$next = $this.next();

		$next.slideToggle();
		$this.parent().toggleClass('open');

		if (!e.data.multiple) {
			$el.find('.submenu').not($next).slideUp().parent().removeClass(
					'open');
		}
	};

	var accordion = new Accordion($('#accordion'), false);
	$('.submenu li').click(function() {
		$(this).addClass('current').siblings('li').removeClass('current');
		var a = $(this).children("a").attr('val');
		var b = $('.courseVideoId').text();
		if (a == b) {
			$(this).addClass('current');
		}
	});

});

/**
 * 处理视频进度
 */
function dealVideoProcess() {

	// 保存视频进度
	vid = $('#video')[0];
	// 视频完成播放
	vid.onended = function(){
		userId = $('#userId').val();
		// 课程视频id
		courseVideoId = $('#courseVideoId').val();
		// 课程id
		courseId = $('#courseId').val();
		// 视频总长度
		duration = $('#video')[0].duration;
		// 视频当前进度
		currentTime = $('#video')[0].currentTime;
		
		if( userId && currentTime>=duration ){
			
			$.ajaxSettings.async = false;
			//确保在修改is_finish前，数据库有该条记录
			saveProcess();
			$.get('../courseVideo/setVideoProcessFinish', {
				userId : userId,
				courseVideoId : courseVideoId,
				courseId : courseId
			}, function(data) {
			},'json');
			$.ajaxSettings.async = true;
		}
	};
	
	// 视频已经准备好开始播放，确保视频已经准备好开始播放才进行保存进度相关操作
	vid.oncanplay = function() {
		//视频上次播放进度
		var videoProcess = $('#videoProcess').val();
		userId = $('#userId').val();
		
			if (videoProcess > 1 && contor == 0) {
				contor++;
				process = videoProcess;
				var newprocess = formatSeconds(process);
				$('#shipingjindu').text('上次播放:' + newprocess);
				kongzhiDiv.style.display = 'block';
			}
		// 窗口刷新，关闭，跳转，当离开当前页面时执行
		window.onbeforeunload = function() {
			$.ajaxSettings.async = false;
			saveProcess();
			$.ajaxSettings.async = true;
		};
	}

	// 视频暂停，每次暂停都会提交进度
	vid.onpause = function() {
		saveProcess();
	};

	// 视频进度条被手动改变
	vid.onseeked = function() {
		saveProcess();
	};
}

/**
 * 继续播放
 */
function ContinuePlay() {
	kongzhiDiv.style.display = 'none';
	$('#video')[0].currentTime = process;
	$('video').trigger('play');
	document.getElementById("c-play").className = "icon-c-pause";
	preCurrentTime = process;
	//document.getElementById('zhangting').style.display='block';
}
/**
 * 从0开始播放
 */
function PlayZero() {
	kongzhiDiv.style.display = 'none';
	$('video').trigger('play');
	document.getElementById("c-play").className = "icon-c-pause";
}
/**
 * 保存进度
 */
function saveProcess() {
	$.ajaxSettings.async = false;
	// 当前视频进度
	currentTime = $('#video')[0].currentTime;
	// 课程id
	courseId = $('#courseId').val();
	// 视频id
	courseVideoId = $('#courseVideoId').val();
	//用户id
	userId = $('#userId').val();
	// 当前进度不等于上一次保存的进度
	if (userId  && currentTime != 0 && preCurrentTime != currentTime) {
		// 提交进度
		$.get('../courseVideo/saveVideoProcess', {
			userId : userId,
			courseVideoId : courseVideoId,
			courseId : courseId,
			currentTime : currentTime
		}, function(data) {
			// 替换上一次提交的进度
			preCurrentTime = currentTime;
		}, 'json');
	}
	$.ajaxSettings.async = true;
}

/**
 * 设置视频播放速度
 * 
 * @param speed
 */
function setPlaySpeed(speed) {
	if(speed==0.5){
		$('#video')[0].playbackRate = 0.5;
	}else if(speed==0.75){
		$('#video')[0].playbackRate = 0.75;
	}else{
		$('#video')[0].playbackRate = 1.0;
	}
}

/**
 * 触发鼠标移入事件 给id=rotateLcon，添加一个header类名
 */
function bigImg() {
	document.getElementById('rotateLcon').classList.add('header');
}
/**
 * 触发鼠标移出事件 给id=rotateLcon，删除一个header类名
 */
function normalImg() {
	document.getElementById("rotateLcon").classList.remove("header");
}

/**
 * 将秒装换成时分秒
 * 
 * @param value
 * @returns {String}
 */
function formatSeconds(value) {
	var theTime = parseInt(value);// 秒
	var theTime1 = 0;// 分
	var theTime2 = 0;// 小时
	// alert(theTime);
	if (theTime > 60) {
		theTime1 = parseInt(theTime / 60);
		theTime = parseInt(theTime % 60);
		// alert(theTime1+"-"+theTime);
		if (theTime1 > 60) {
			theTime2 = parseInt(theTime1 / 60);
			theTime1 = parseInt(theTime1 % 60);
		}
	}
	var result = "" + parseInt(theTime) + "秒";
	if (theTime1 > 0) {
		result = "" + parseInt(theTime1) + "分" + result;
	}
	if (theTime2 > 0) {
		result = "" + parseInt(theTime2) + "小时" + result;
	}
	return result;
}