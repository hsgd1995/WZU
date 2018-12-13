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
		alert("Your browser does not support XMLHTTP.");
	}
}

function state_Change() {
	if (xmlhttp.readyState == 4) {// 4 = "loaded"
		if (xmlhttp.status == 200) {// 200 = OK
			// ...our code here...
			console.log('成功获取资源');
			//document.getElementById('test').innerHTML = xmlhttp.responseText;

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
			//显示字幕
			//captions.forEach(myFunction);
			videoFunction();
		} else {
			alert("Problem retrieving XML data");
		}
	}
}
demoP = document.getElementById("test");
myVideo = document.getElementById("video");

function videoFunction() {
	myVideo.ontimeupdate = function() {
		captions.forEach(myFunction);
	}

}
/**
 * 判断字幕文件中的格式
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
 */
var formatFlag = false;//默认false，表示默认为第一种格式

function myFunction(item, index) {

	//demoP.innerHTML = demoP.innerHTML + "index[" + index + "]: " + item + "<br>";
	//demoP.innerHTML = demoP.innerHTML + "  " + index + "  " + item + "<br>";
	//demoP.innerHTML = demoP.innerHTML + "  "+ item + "<br>";
	//demoP.innerHTML = demoP.innerHTML + "  " + video_timecode_min(item.toString()) + "<br>";
	if (myVideo.currentTime > video_timecode_min(item.toString())
			&& myVideo.currentTime < video_timecode_max(item.toString())) {
		if (!formatFlag) {
			//第一种格式
			demoP.innerHTML = item.toString().split(',')[1];
		} else {
			//第二种格式
			demoP.innerHTML = item.toString().split(',')[2];
		}
	} else {
		//demoP.innerHTML = "" ;
	}

}

// Utilities for caption time codes
function video_timecode_min(tc) {
	var tcpair = [];
	tcpair = tc.split(" --> ");
	if (tcpair[0].indexOf(',') != -1) {
		formatFlag = true;
		return videosub_tcsecs(tcpair[0].split(',')[1]);
	}
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
		//console.log('tc1',tc1[0]);
		tc2 = tc1[0].split(":");
		tc3 = tc1[0].split(".")[1];
		seconds = Math.floor(tc2[0] * 60 * 60) + Math.floor(tc2[1] * 60)
				+ Math.floor(tc2[2]) + +getDecimals(tc3);
		//console.log('seconds',seconds);
		return parseInt(seconds);
	}
}

function getDecimals(str) {
	if (str) {
		return parseInt(str.substr(0, 1)) / 10;
	}
	return 0;
}
