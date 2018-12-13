/**
 * 标题行复选框事件
 */
function theadChecboxFunc() {
	$('.fill thead input[type=checkbox]').eq(0).unbind().change(function() {
		if($(this).is(':checked')){
			// 标题复选框选中，数据行复选框全部选中
			$('.fill tbody input[type=checkbox]').prop('checked',true);
		} else {
			// 标题复选框选中，数据行复选框全部取消选中
			$('.fill tbody input[type=checkbox]').prop('checked',false);
		}
	});
}

/**
 * 数据行复选框事件
 */
function tbodyChecboxFunc() {
	$('.fill tbody input[type=checkbox]').unbind().change(function() {
		if(isChoosedAll()){
			// 全选，标题复选框选中
			$('.fill thead input[type=checkbox]').eq(0).prop('checked',true);
		} else {
			// 未全选，标题复选框未选中
			$('.fill thead input[type=checkbox]').eq(0).prop('checked',false);
		}
	});
}
/**
 * 判断是否选中
 * 
 * @returns {Boolean}
 */
function isChoosedAny() {
	// 当亲选中的行数
	var $count = 0;
	$.each($('.fill tbody input[type=checkbox]'),function(index,checkbox){
		if($(checkbox).is(':checked')){
			$count++;
		}
	});
	if($count > 0){
		return true;
	}
	return false;
}

/**
 * 判断是否全选
 * 
 * @returns {Boolean}
 */
function isChoosedAll() {
	// 获得tbody中的行数
	var $rows = $('.fill tbody tr').length;
	// 当前选中的行数
	var $count = 0;
	$.each($('.fill tbody input[type=checkbox]'),function(index,checkbox){
		if($(checkbox).is(':checked')){
			$count++;
		}
	});
	if($rows == $count ){
		return true;
	}
	return false;
}

/**
 * 填充分页栏事件
 * @param pageModel
 */
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