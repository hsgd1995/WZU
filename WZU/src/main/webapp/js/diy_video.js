var myVideo = document.getElementById("video"); //播放器
$(myVideo).bind('contextmenu',function() { return false; }); 
var diyPlay = document.getElementById("diy-play");
var cPlay = document.getElementById("c-play"); //播放按钮
var cProgress = document.getElementById("c-progress");
var cPlayed = document.getElementById("c-played"); //已经播放过的进度条
var cDrag = document.getElementById("c-drag"); //进度条顶端的圆钮
var cCurrentTime = document.getElementById("c-currentTime"); //当前时间span
var cTotalTime = document.getElementById("c-totalTime"); //总时间
var loading = document.getElementsByClassName("icon-c-loading"); //loading 旋转图标
var voice = document.getElementsByClassName("i-voice"); //音量按钮
var voice_mask = document.getElementsByClassName("voice-mask"); //音量总进度条
var voice_bared = document.getElementsByClassName("voice-bared"); //现在的音量的进度条
var voice_dot = document.getElementsByClassName("voice-dot");
var voice_num = 0.5; //初始化当前的音量
var isFinish = $('#isFinish').val();
var readyXhr = false;//外挂字幕文件是否加载完成
var useDivCaption = false;//开启字幕
//字幕数组，用于存放已加载好的字幕
var captionArray = [];

volume(voice_num); //把音量初始化到50
function volume(n) {
	myVideo.volume = n;
	voice_bared[0].style.height = n * 100 + 'px';
}

function playPause() {
	if(myVideo.paused) {
		Play();
	} else {
		Pause();
	}
};

function Play() {
	cPlay.className = "icon-c-pause";
	myVideo.play();
	$(".video_p").hide();
}

function Pause() {
	cPlay.className = "icon-c-play";
	myVideo.pause();
	$(".video_p").show();
}

myVideo.onclick=function(){
    if(myVideo.paused){
    	cPlay.className = "icon-c-pause";
        myVideo.play();
        $(".video_p").hide();
    }else{
    	cPlay.className = "icon-c-play";
        myVideo.pause();
        $(".video_p").show();
    }
}

$(".video_p").click(function(){
  	myVideo.play();
  	cPlay.className = "icon-c-pause";
    $(".video_p").hide();
});

$(".video_p").hover(function(){
    $(".video_p img").attr('src', '../img/L.png');
},function(){
    $(".video_p img").attr('src', '../img/B.png');
});

//refresh[0].onclick = function (){
//Load();
//}
//function Load(){
//Pause();
//myVideo.load();
//cPlayed.style.width = 0+"%";
//cDrag.style.display="none";
//cCurrentTime.innerHTML = "00:00";
//cTotalTime.innerHTML = "00:00";
//}
//播放时间及进度条控制
myVideo.ontimeupdate = function() {
	if(readyXhr&&useDivCaption){
		//console.log('启动字幕');
		captionArray[aId].forEach(myFunction);
	}
	var currTime = this.currentTime, //当前播放时间
		duration = this.duration; // 视频总时长
	if(currTime == duration) {
		Pause();
	}
	//百分比
	var pre = currTime / duration * 100 + "%";
	//显示进度条
	cPlayed.style.width = pre;
	var progressWidth = cProgress.offsetWidth;
	var leftWidth = progressWidth * (currTime / duration);
	if(leftWidth > 8 && (progressWidth - leftWidth) > 4) {
		cDrag.style.display = "block";
	} else {
		cDrag.style.display = "none";
	}
	cDrag.style.left = progressWidth * (currTime / duration) - 4 + "px";
	//显示当前播放进度时间
	cCurrentTime.innerHTML = getFormatTime(currTime, duration);
	cTotalTime.innerHTML = getFormatTime(duration, duration);
};
//当浏览器可在不因缓冲而停顿的情况下进行播放时
myVideo.oncanplaythrough = function() {
	loading[0].style.display = "none";
}
//当视频由于需要缓冲下一帧而停止
myVideo.onwaiting = function() {
	loading[0].style.display = "block";
}
//当用户开始移动/跳跃到音频/视频中的新位置时
myVideo.onseeking = function() {
	if(myVideo.readyState == 0 || myVideo.readyState == 1) {
		loading[0].style.display = "block";
	}
}
//拖拽进度条上的园钮实现跳跃播放
cDrag.onmousedown = function(e) {
	if(cPlay.className == 'icon-c-pause')
		myVideo.pause();
	loading[0].style.display = "block";
	document.onmousemove = function(e) {
		if(isFinish==1){
		
			var diyPlayParent2 = $(diyPlay).parent().parent();
			var diyPlayParent3 = $(diyPlay).parent().parent().parent();
		
			var leftV = e.clientX - diyPlayParent2[0].offsetLeft-diyPlayParent3[0].offsetLeft;
			//var leftV = e.clientX - diyPlay.offsetLeft;
			if(leftV <= 0) {
				leftV = 0;
			}
			if(leftV >= diyPlay.offsetWidth) {
				leftV = diyPlay.offsetWidth - 10;
			}
			cDrag.style.left = leftV + "px";
		}else{
			Pause();
		}

	}
	document.onmouseup = function() {
		var scales = cDrag.offsetLeft / cProgress.offsetWidth;
		var du = myVideo.duration * scales;

		myVideo.currentTime = du;
		//清除字幕
		demoP.innerHTML = "";
		if(cPlay.className == 'icon-c-pause')
			myVideo.play();
		document.onmousemove = null;
		document.onmousedown = null;
		document.onmouseup = null;
		
	}

}
//在进度条上点击跳跃播放
cProgress.onmouseup = function(e) {
	if(isFinish==1){
		var event = e || window.event;
		myVideo.currentTime = (event.offsetX / this.offsetWidth) * myVideo.duration;
		//清除字幕
		demoP.innerHTML = "";
	}else{
		Pause();
	}
	
};
//根据duration总长度返回 00 或 00:00 或 00:00:00 三种格式
function getFormatTime(time, duration) {
	var time = time || 0;

	var h = parseInt(time / 3600),
		m = parseInt(time % 3600 / 60),
		s = parseInt(time % 60);
	s = s < 10 ? "0" + s : s;
	if(duration >= 60 && duration < 3600) {
		m = m < 10 ? "0" + m : m;
		return m + ":" + s;
	}
	if(duration >= 3600) {
		m = m < 10 ? "0" + m : m;
		h = h < 10 ? "0" + h : h;
		return h + ":" + m + ":" + s;
	}
	return s;
}
//音量的控制部分
//点击声音按钮时，把视频静音
voice[0].onclick = function() {
	if(myVideo.muted) {
		voice[0].className = "i-voice icon-c-voice";
		myVideo.muted = false;
		if(voice_num >= 0 && voice_num <= 1) {
			volume(voice_num);
		} else {
			volume(0.8);
		}
	} else {
		voice_num = myVideo.volume; //记录下来静音前的音量
		voice[0].className = 'i-voice icon-c-mute';
		myVideo.muted = true;
		volume(0);
	}
}
//当点击进度条上的一个位置时，变化音量
voice_mask[0].onclick = function(e) {
	var event = e || window.event;
	if(event.offsetY >= 100) {
		voice[0].className = 'i-voice icon-c-mute';
		myVideo.muted = true;
		volume(0);
		return;
	}
	volume((100 - event.offsetY) / 100);
};
voice_mask[0].onmousedown = function(e) {
	document.onmousemove = function(e) {
		volume((100 - e.offsetY) / 100);
		if(event.offsetY == 100) {
			voice[0].className = 'i-voice icon-c-mute';
			myVideo.muted = true;
			volume(0);
		}
	}
	document.onmouseup = function() {
		document.onmousemove = null;
		document.onmousedown = null;
		
	}
}
//全屏的控制部分
var fullscreen = document.getElementById('diy-fullScreen');
var FullScreenTF = true;

function launchFullscreen(element) {
	//此方法不能在异步中进行，否则火狐中不能全屏操作
	if(element.requestFullscreen) {
		element.requestFullscreen();
	} else if(element.mozRequestFullScreen) {
		element.mozRequestFullScreen();
	} else if(element.msRequestFullscreen) {
		element.msRequestFullscreen();
	} else if(element.oRequestFullscreen) {
		element.oRequestFullscreen();
	} else if(element.webkitRequestFullscreen) {
		element.webkitRequestFullScreen();
	} else {
		alert("您的浏览器版本太低，不支持全屏功能！");
	}
	FullScreenTF = false;
};
//退出全屏
function exitFullscreen() {
	if(document.exitFullscreen) {
		document.exitFullscreen();
	} else if(document.msExitFullscreen) {
		document.msExitFullscreen();
	} else if(document.mozCancelFullScreen) {
		document.mozCancelFullScreen();
	} else if(document.oRequestFullscreen) {
		document.oCancelFullScreen();
	} else if(document.webkitExitFullscreen) {
		document.webkitExitFullscreen();
	} else {
		alert("您的浏览器版本太低，不支持全屏功能！");
	}
	FullScreenTF = true;
};
fullscreen.onclick = function() {
	if(FullScreenTF) {
		launchFullscreen(diyPlay);
		fullscreen.className = "icon-c-enlarge";
	} else {
		exitFullscreen();
		fullscreen.className = "icon-c-enlarge";
	}
};
//播放速度
function speedChange(speed){
	//修改控件上播放速度文字
	if(typeof speed === 'number' && speed%1 === 0){
		$('.speed_text').html(speed+".0倍");
	}else{
		$('.speed_text').html(speed+"倍");
	}
	myVideo.playbackRate = speed;
}

//******字幕*******
var xmlhttp;

function loadXMLDoc(url) {
	xmlhttp = null;
	if (window.XMLHttpRequest) {// code for all new browsers
		xmlhttp = new XMLHttpRequest();
	} else if (window.ActiveXObject) {// code for IE5 and IE6
		xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
	}
	if (xmlhttp != null) {
		xmlhttp.onreadystatechange = state_Change;
		xmlhttp.open("GET", url, true);
		xmlhttp.send(null);
	} else {
		console.log("Your browser does not support XMLHTTP.");
	}
}

function state_Change() {
	if (xmlhttp.readyState == 4) {// 4 = "loaded"
		if (xmlhttp.status == 200||xmlhttp.status == 201) {// 200 = OK
			// ...our code here...
			//console.log('成功获取资源');

			captions = [];
			var records = [], record, req = xmlhttp.responseText;
			records = req.split(/\r?\n\r?\n/);
			for (var r = 0; r < records.length; r++) {
				record = records[r];
				captions[r] = [];
				captions[r] = record.split(/\r\n?|\n/);
			}
			// Remove first element ("VTT")
			captions.shift();
			captionArray[aId] = captions;
			//显示字幕
			videoFunction();
		} else {
			console.log("Problem retrieving XML data");
		}
	}
}
demoP = document.getElementById("demo");

function videoFunction() {
	//外挂字幕文件已加载完成
	readyXhr = true;
}
var timeFormat = 1;//判断时间的格式，默认为00:00:21.225 --> 00:00:22.175
var clearTime = null;
function myFunction(item, index) {
	judgeFormat(item);
	if (myVideo.currentTime > video_timecode_min(item.toString())
			&& myVideo.currentTime < video_timecode_max(item.toString())) {
		
		clearTime=video_timecode_max(item.toString());
		
		//新的方式
		if(timeFormat==2){
			//00:00:21,225 --> 00:00:22,175
			demoP.innerHTML = item.toString().split(',')[item.length+1];
		}else{
			//00:00:21.225 --> 00:00:22.175
			//console.log('当前时间：',myVideo.currentTime,'  起始显示时间：',video_timecode_min(item.toString()));
			demoP.innerHTML = item.toString().split(',')[item.length-1];
		}
	} else {
		if(myVideo.currentTime>clearTime){
			demoP.innerHTML = "" ;
		}
	}

}
/**
 * 判断vtt字幕文件中的格式
 *
 * 第一种：
 * 00:00:21.225 --> 00:00:22.175
 *同学们好
 *
 * 第二种：
 *
 * 1
 *00:00:21.225 --> 00:00:22.175
 *同学们好
 *
 *其中每种格式可能包含两种情况：（1）00:00:21.225 --> 00:00:22.175
 *						（2）00:00:21,225 --> 00:00:22,175
 */
function judgeFormat(item){
		
	if(item.length==3&&item[1].indexOf(',') != -1){
		//格式为00:00:21,225 --> 00:00:22,175
		timeFormat = 2;
		return ;
	}
	if(item.length==2&&item[0].indexOf(',') != -1){
		//格式为00:00:21,225 --> 00:00:22,175
		timeFormat = 2;
		return ;
	}
	
	timeFormat = 1;
}


// Utilities for caption time codes
function video_timecode_min(tc) {
	var tcpair = [];
	tcpair = tc.split(" --> ");
	if (tcpair[0].indexOf(',') != -1) {
		//00:00:21,225 --> 00:00:22,175
		if(timeFormat==2){
			if(tcpair[0].split(',').length==2){
				return videosub_tcsecs(tcpair[0].split(',')[0]+'.'+tcpair[0].split(',')[1]);
			}
			if(tcpair[0].split(',').length==3){
				return videosub_tcsecs(tcpair[0].split(',')[1]+'.'+tcpair[0].split(',')[2]);
			}
		}
		/**
		 * 1
		 * 00:00:21.225 --> 00:00:22.175
		 * 同学们好
		 */
		return videosub_tcsecs(tcpair[0].split(',')[1]);
	}
	/**
	 * 00:00:21.225 --> 00:00:22.175
	 * 同学们好
	 */
	return videosub_tcsecs(tcpair[0]);
}

function video_timecode_max(tc) {
	var tcpair = [];
	tcpair = tc.split(" --> ");
	return videosub_tcsecs(tcpair[1]);
}

function videosub_tcsecs(tc) {
	if (tc === null || tc === undefined) {
		return 0;
	} else {
		var tc1 = [], tc2 = [], tc3 = [], seconds;
		tc1 = tc.split(",");
		tc2 = tc1[0].split(":");
		tc3 = tc1[0].split(".")[1];
		seconds = Math.floor(tc2[0] * 60 * 60) + Math.floor(tc2[1] * 60)
				+ Math.floor(tc2[2]) +getDecimals(tc3);
		return seconds;
	}
}
//获取小数点后一位
function getDecimals(str) {
	if (str) {
		return parseInt(str.substr(0, 3)) / 1000;
	}
	return 0;
}


// ******字幕*******

$(function() {
	
	/*var video = document.getElementById("video");
	window.URL = window.URL || window.webkitURL;
	var xhr = new XMLHttpRequest();*/
	//xhr.open("get", "/videoUrl/courseVideo184268685687458.mp4", true);
	//xhr.open("get", "../courseVideo/play/"+$('#courseVideoId').val(), true);
	/*
	 正常我们用AJAX请求的是后台的借口
	 这里直接请求的是一个.MP4的文件；
	 如果你的视频文件和你项目没在同一个域下，会有跨域问题的；
	*/
	/* xhr.responseType = "blob";
	 xhr.onload = function() {
	   if (this.status == 200) {
	        var blob = this.response;
	        video.onload = function(e) {
	            window.URL.revokeObjectURL(video.src);
	        };
	        video.src = window.URL.createObjectURL(blob);
	        var date2 = new Date();
	        console.log('结束：',date2.getHours(),':',date2.getMinutes(),':',date2.getSeconds());
	     }
	  }
	  xhr.send();*/
	  
	
	//url = $('#cChinese').val();
	//loadXMLDoc(url);

	$(".change_caption").hover(function() {
		$(".change_caption ul").show();
	}, function() {
		$(".change_caption ul").hide();
	});
	
	$(".change_speed").hover(function() {
		$(".change_speed ul").show();
	}, function() {
		$(".change_speed ul").hide();
	});
	
	//控件上字幕功能
	
	$('.some-caption').each(function(i){
		$(this).click(function(event){
			//视频控件上显示已选择的字幕名
			$(".caption_text").html($(this).children().text());
			aId = $(this).children().attr('id');
			//url = $(document.getElementsByName("captionSrc")[aId]).val();
			//另一种实现，通过后台
			url = "../courseVideo/getCaption/"+$(document.getElementsByName("captionSrc")[aId]).val();
			//如果字幕数组里没有加载该url对应的字幕文件
			if(!captionArray[aId]){
				loadXMLDoc(url);
			}
			//启用字幕文件
			useDivCaption = true;
		});
	});

	/*$("#Chinese").click(function(){
		$(".caption_text").html("中文");
		useDivCaption = true;
	});*/
	$("#CloseCaption").click(function(){
		$(".caption_text").html("字幕");
		//不开启字幕
		useDivCaption = false;
		//清空显示字幕的div
		$('#demo').html('');
	});

});