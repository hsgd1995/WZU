/*可以先判断浏览器的版本，如果不是Chrome，则可以用video.ontimeupdate=function(){myScript};*/

// 获取 id="myVideo" 的 video 元素
var vvid = document.getElementById("video");
//json文件中的数据
//中文字幕
var jsonCn = null;
//英文字幕
var jsonEn = null;
//字幕标识
var captionFlag = 0;
var currentTime;
var jsonInterval = null;
$.ajaxSettings.async = false;
$.getJSON("../vtt/zimu_cn.json", function(data) {
	jsonCn = data;
});
$.getJSON("../vtt/zimu_en.json", function(data) {
	jsonEn = data;
});
$.ajaxSettings.async = true;

$(function() {
	
	$("#English").click(function(){
		$(".caption_text").html("英文");
		useInterval(1);
	});
	$("#Chinese").click(function(){
		$(".caption_text").html("中文");
		useInterval(0);
	});
	$("#CloseCaption").click(function(){
		$(".caption_text").html("字幕");
	});

	$(".change_speed ul li").click(function(){
		$(".speed_text").html($(this).text());
	});
	
	
	// 为 video 元素添加 ontimeupdate 事件，如果当前播放位置改变则执行函数
	vvid.addEventListener("timeupdate", function() {
		//myFunction();
	}, false);
	
	//useInterval();
	
});

function useInterval(i){
	
	//用于切换字幕，如果点击了另一个字幕则将定时器先清除再开启定时器
	if(captionFlag != i){
		cleInterval(jsonInterval);
		captionFlag = i;
	}
	
	//视频处于播放状态，则点击字幕时马上启动定时器
	if(!vvid.paused){
		jsonInterval = setInterval(function(){
			myFunction(i);
		}, 100);
	}
	
	//点击播放视频则启动定时器
	vvid.onplay = function(){
		jsonInterval = setInterval(function(){
			myFunction(i);
		}, 100);
	};
	
	//暂停播放视频则清除定时器
	vvid.onpause = function(){
		cleInterval(jsonInterval);
	};
}

function cleInterval(intervalName){
	if(intervalName != null){
		clearInterval(intervalName);
	}
}

function myFunction(i) { 
	//如果json不为空，即已经获取到json数据
	if (jsonCn != null) {
		//将当前时间的整数作为key,取出json数据中的值
		if(i==0){
			jsonData = jsonCn[coverTime(vvid.currentTime.toString().match(/^\d+(?:\.\d{0,1})?/).toString())];
		}else{
			jsonData = jsonEn[coverTime(vvid.currentTime.toString().match(/^\d+(?:\.\d{0,1})?/).toString())];
		}
		//如果当前秒在json数据中存在相应的值
		if (jsonData != undefined) {
			//已经不需要考虑到在1秒内可能会执行多次timeupdate事件，因为在同一秒钟获取到的字幕是一样的
			$('#demo').html(jsonData);
		}
	}
}
var s;
var s0;
var s1;
function coverTime(second){
	s = second.split('.');
	s0 = s[0];
	s1 = s[1];
	return secondToDate(s0)+'.'+s1;
}
var h;
var m;
var s;
/**
 * 转换秒
 * @param result
 * @returns {String}
 */
function secondToDate(result) {
	
    h = Math.floor(result / 3600) < 10 ? '0'+Math.floor(result / 3600) : Math.floor(result / 3600);
    m = Math.floor((result / 60 % 60)) < 10 ? '0' + Math.floor((result / 60 % 60)) : Math.floor((result / 60 % 60));
    s = Math.floor((result % 60)) < 10 ? '0' + Math.floor((result % 60)) : Math.floor((result % 60));
    return h + ":" + m + ":" + s;
}