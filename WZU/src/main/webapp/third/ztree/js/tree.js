var setting = {
	view: {
		selectedMulti: false
	},
	check: {
		enable: true
	},
	data: {
		simpleData: {
			enable: true
		}
	},
	edit: {
		enable: true
	},
	callback: {
		onCheck: getOnCheck
	}
};


$(document).ready(function() {
	var t = new Array();
	$.ajax({
		url:'role/getAuthorityList',
		type:'get',
		dataType:'json',
		success:function(data){
			$.each(data.list,function(i,vo1){
				t.push({id:vo1.id,pId:0,name:vo1.name+"---"+vo1.state,open:false});
				$.each(vo1.list,function(j,vo2){
					t.push({id:vo2.id,pId:vo1.id,name:vo2.name+"---"+vo2.state,open:false});
					$.each(vo2.list,function(k,vo3){
						t.push({id:vo3.id,pId:vo2.id,name:vo3.name+"---"+vo3.state});
					});
				});
			});
			$.fn.zTree.init($("#treeDemo"), setting, t);
		}
	});
});

function getOnCheck(event, treeId, treeNode) {
    console.log(treeNode.id , ", " , treeNode.name , "," , treeNode.checked,",",treeId);
    var treeObj=$.fn.zTree.getZTreeObj("treeDemo"),
    nodes=treeObj.getCheckedNodes(true);
    for(var i=0;i<nodes.length;i++){
    	console.log("节点id:"+nodes[i].id); //获取选中节点的值
    }
}