$(function() {	
//超出部分显示省略号
    $(".opcr_middle p").each(function() {
		var maxwidth = 64;
		if($(this).text().length > maxwidth) {
			$(this).text($(this).text().substring(0, maxwidth));
			$(this).html($(this).html() +'...');
		}
	});
});