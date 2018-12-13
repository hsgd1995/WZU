$(function() {
	//分页   
	 var GG = {
        "kk":function(mm){
        	//alert(mm); 
        } 
    }
    $("#page").initPage(35,1,GG.kk);
    
	 
    $("#NewDiscussion").click(function(){
        $('body,html').animate({ 
            scrollTop: $('#ShowNewDiscussion').offset().top
        }, 1000);                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                               
    });
    
    //最新资讯点击更换样式 
/*    $(".dl_content_top").click(function(){
         $(".dl_content_top").removeClass("bg_red");   
         $(this).addClass("bg_red");
    });*/
    
    
	// 加载新闻数据   
/*	getNewsData();*/
    
    //内容部分超出显示...
    omit1();
    omit2();
});

//最新资讯 
function getNewsData(){
	var pageIndex = 1;
	$.get("news/"+ pageIndex +"/getNewsPageModel",function(data){
		$('#pagination').html('');
		// 加载分页栏
		fillPage(data);
		// 分页栏点击事件
		onclickPage(data);
		$.each(data.list,function(index,news){
			$("#newsUl").append(fillNewsData(news));
		})
		omit();
	}) 
} 

//新闻动态 
function getNewsTrendsData(){
	var pageIndex = 1;
	// 清空列表
	$('#newsUl').html('');
	$("title").html("新闻动态");
	$(".title-name").html('<i class="icon-list-alt"></i>&nbsp;&nbsp;新闻动态');
	$.get("news/"+ pageIndex +"/getTrends",function(data){
		$.each(data.list,function(index,news){
			var a = $("#newsUl").append(fillNewsData(news));
		})
		$h1.after();
		$('.catalog_content').
		omit();
	}) 
} 

//媒体报道 
function getNewsReportData(){
	var pageIndex = 1;
	// 清空列表
	$('.catalog_content').html('');
	$("title").html("媒体报道");
//	$(".title-name").html('<i class="icon-list-alt"></i>&nbsp;&nbsp;媒体报道');
	var $h1 = $('<h1 class="title-name"><i class="icon-list-alt"></i>&nbsp;&nbsp;媒体报道</h1>');
	var $div1 = $('<div class="catalog_bottom"></div>');
	var $ul = $('<ul class="list-unstyled" id = "newsUl"></ul>');
	$.get("news/"+ pageIndex +"/getReport",function(data){
		$.each(data.list,function(index,news){
			$("#newsUl").append(fillNewsData(news));
		})
		
		$('.catalog_content').append($h1).append($div1);
		omit();
	}) 
} 

//新闻详情页面
function getNewsDetails(that){
	var id = that.siblings().text();
	// 清空列表
	$('.catalog_content').html('');
	$(".latest_page").html('');
	$.get("news/"+ id +"/findNewsById",function(data){
		$(".catalog_content").append(fillNewsDetailsData(data));
	}) 
} 

//绑定新闻详情数据
function fillNewsDetailsData(news){
	var $div1 = $('<div></div>');
	var $h1 = $('<h1 class="title-name">'+news.newsName+'</h1>');
	var $div2 = $('<div class="catalog_bottom"></div>');
	var $p = $('<p>'+news.content+'</p>');
	return $div1.append($h1).append($div2).append($p);
}

 
//绑定数据
function fillNewsData(news){
	var $li = $('<li></li>'); 
	var $div1 = $('<div class="new_img"><img src="img/study_03.jpg" /></div>');
	var $div2 = $('<div class="new_text"><h2><span hidden="true">'+news.id+'</span><a href="#" onclick="getNewsDetails($(this))">'+news.newsName+'</a></h2><p>'+news.content+'</p></div>');
	var $div3 = $('<div class="clearfix"></div>');
	return $li.append($div1).append($div2).append($div3); 
}

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


//分页
function onclickPage(pageModel) {
	// 当前页
	var $pageIndex = pageModel.pageIndex;
	// 总页数
	var $totalSize = pageModel.totalSize;
	// 上页
	var $preIndex = $pageIndex - 1 > 0 ? ($pageIndex - 1) : 1;
	// 下页
	var $nextIndex = $pageIndex + 1 < $totalSize ? ($pageIndex + 1)
			: $totalSize;
	$('#pagination ul li:eq(0) a').attr("onClick","pageSeach(1)");
	$('#pagination ul li:eq(1) a').attr("onClick","pageSeach(" + $preIndex+")");
	$('#pagination ul li:eq(3) a').attr("onClick","pageSeach(" + $nextIndex+")");
	$('#pagination ul li:eq(4) a').attr("onClick","pageSeach(" + $totalSize+")");
}

function fillPage(pageModel) {
	// 分页按钮栏
	var $ul = $('<ul class="pagination pagination-md col-sm-6" style="margin: 0;"></ul>');
	// 当前页
	var $pageIndex = pageModel.pageIndex;
	// 总页数
	var $totalSize = pageModel.totalSize;
	// 上页
	var $preIndex = $pageIndex - 1 > 0 ? ($pageIndex - 1) : 1;
	// 下页
	var $nextIndex = $pageIndex + 1 < $totalSize ? ($pageIndex + 1)
			: $totalSize;
	// li元素
	var $li1 = $('<li><a style="cursor:pointer;">首页</a></li>');
	var $li2 = $('<li><a style="cursor:pointer;">上一页</a></li>');
	var $li3 = $('<li><a>第&nbsp;' +$pageIndex+'&nbsp;页</a></li>');
	var $li4 = $('<li><a style="cursor:pointer;">下一页</a></li>');
	var $li5 = $('<li><a style="cursor:pointer;">尾页</a></li>');
	$ul.append($li1).append($li2).append($li3).append($li4).append($li5);
	// 分页信息栏
	var $div = $('<div class="col-sm-6 pull-right" style="height:32px"></div>');
	var $toRecord = pageModel.pageSize * $pageIndex;
	var $desc = '每页显示 '
			+ pageModel.pageSize
			+ ' 条记录, Page '
			+ $pageIndex
			+ ' of '
			+ $totalSize
			+ ', Displaying '
			+ (pageModel.firstLimitParam + 1)
			+ ' to '
			+ ($toRecord >= pageModel.recordCount ? pageModel.recordCount
					: $toRecord) + ' of ' + pageModel.recordCount + ' items.';
	$div.text($desc);
	// 整合分页栏
	$('#pagination').append($ul).append($div);
}