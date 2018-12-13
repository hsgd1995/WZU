$(function() {
	//分页   
	 var GG = {
        "kk":function(mm){
        	
        	
        	
        	if(window.location.href.search("latest_information")!=-1){
        		window.location.href = 'latest_information?pageIndex='+mm;
        	}
        	if(window.location.href.search("newsReport")!=-1){
        		window.location.href = 'newsReport?pageIndex='+mm;
        	}
        	if(window.location.href.search("newsTrends")!=-1){
        		window.location.href = 'newsTrends?pageIndex='+mm;
        	}
        } 
    }
	 
    $("#page").initPage($('#recordCount').val(),$('#pageIndex').val(),GG.kk);
    
	
    //内容部分超出显示...
    omit1();
    omit2();
});


function omit1(){ 
	//超出部分显示省略号 
	   $(".newsNameOmit a").each(function() {
			var maxwidth = 45;
			if($(this).text().length > maxwidth) {
				$(this).text($(this).text().substring(0, maxwidth));
				$(this).html($(this).html() +'...');
			}
		});
}

function omit2(){
	//超出部分显示省略号 
	   $(".contentOmit").each(function() {
			var maxwidth = 100;
			if($(this).text().length > maxwidth) {
				$(this).text($(this).text().substring(0, maxwidth));
				$(this).html($(this).html() +'...');
			}
		});
}