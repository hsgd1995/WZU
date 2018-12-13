$(function() {
	var cEnglish="<track id='cEnglish' label='英文' kind='subtitles' srclang='en' src='../vtt/zimu_en.vtt' default>";
	var cChinese="<track id='cChinese' label='中文' kind='subtitles' srclang='zh' src='../vtt/12.vtt' default>";
	
	$("#English").click(function(){
		$(".caption_text").html("英文");
		$("#cChinese").remove();
		//如果英文字幕不存在，才显示英文字幕
		if(!$("#cEnglish")[0]){
			$("#cCaptions").after(cEnglish);
		}
		
	});
	$("#Chinese").click(function(){
		$(".caption_text").html("中文");
		$("#cEnglish").remove();
		//如果中文字幕不存在，才显示中文字幕
		if(!$("#cChinese")[0]){
			$("#cCaptions").after(cChinese);
		}
	});
	$("#CloseCaption").click(function(){
		$(".caption_text").html("字幕");
		$("#cChinese").remove();
		$("#cEnglish").remove();
	});

	$(".change_speed ul li").click(function(){
		$(".speed_text").html($(this).text());
	});
});