<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
<link rel="stylesheet" type="text/css" href="/js/jquery-easyui-1.4.1/themes/default/easyui.css" />
<link rel="stylesheet" type="text/css" href="/js/jquery-easyui-1.4.1/themes/icon.css" />
<link rel="stylesheet" type="text/css" href="/css/taotao.css" />
<script type="text/javascript" src="/js/jquery-easyui-1.4.1/jquery.min.js"></script>
<script type="text/javascript" src="/js/jquery-easyui-1.4.1/jquery.easyui.min.js"></script>
<script type="text/javascript" src="/js/jquery-easyui-1.4.1/locale/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="/js/common.js"></script>

<title>内容分类管理</title>
</head>
<body>
<div>
	 <ul id="contentCategory" class="easyui-tree">
    </ul>
</div>
<div id="contentCategoryMenu" class="easyui-menu" style="width:120px;" data-options="onClick:menuHandler">
    <div data-options="iconCls:'icon-add',name:'add'">添加</div>
    <div data-options="iconCls:'icon-remove',name:'rename'">重命名</div>
    <div class="menu-sep"></div>
    <div data-options="iconCls:'icon-remove',name:'delete'">删除</div>
</div>
<script type="text/javascript">
//页面加载成功后执行以下逻辑
$(function(){
	//使用id选择器，找到生命的树的组件，创建树
	$("#contentCategory").tree({
		url : '/rest/content/category',
		animate: true,
		method : "GET",
		//绑定右键点击事件
		onContextMenu: function(e,node){
			//关闭window的默认右键菜单
            e.preventDefault();
			//选中鼠标右键的节点
            $(this).tree('select',node.target);
			//显示EasyUI的Menu菜单
            $('#contentCategoryMenu').menu('show',{
            	//菜单显示的坐标，其实就是鼠标所在的位置
                left: e.pageX,
                top: e.pageY
            });
        },
        
        //在编辑之后执行以下逻辑，参数就是编辑的节点
        onAfterEdit : function(node){
        	//获取树
        	var _tree = $(this);
        	//判断节点的id是否为0，其实就是判断是否是新增节点
        	if(node.id == 0){
        		// 新增节点
        		//发起了Ajax的post请求，url：/rest/content/category/add
        		$.post("/rest/content/category/add",{parentId:node.parentId,name:node.text},function(data){
        			//请求后台成功后执行以下逻辑
        			//update：树的更新方法
        			_tree.tree("update",{
        				target : node.target,
        				id : data.id
        			});
        		});
        	}else{
        		//如果id不为0，表示是修改节点
        		$.ajax({
        			   type: "POST",
        			   url: "/rest/content/category/update",
        			   data: {id:node.id,name:node.text},
        			   success: function(msg){
        				   //$.messager.alert('提示','新增商品成功!');
        			   },
        			   error: function(){
        				   $.messager.alert('提示','重命名失败!');
        			   }
        			});
        	}
        }
	});
});
//Menu菜单所使用的点击逻辑
//参数是选择的菜单
function menuHandler(item){
	//获取树
	var tree = $("#contentCategory");
	//使用树组件的方法，获取选中的树上的节点
	var node = tree.tree("getSelected");
	//==:  1=="1"  结果为true
	//===： 1==="1" 结果为false   1===1  结果为true
	//判断菜单项的名子是否是add，表示我们是否选择的是添加按钮
	if(item.name === "add"){
		//执行树的append追加节点的方法
		tree.tree('append', {
			//在哪个节点后面追加子节点
            parent: (node?node.target:null),
            //新加的节点的数据
            data: [{
                text: '新建分类',
                id : 0,
                parentId : node.id
            }]
        }); 
		//使用树的find方法查询id为0的节点，其实找的是新添加的节点
		var _node = tree.tree('find',0);
		//选中新加的节点，beginEdit：开始编辑新加的节点
		tree.tree("select",_node.target).tree('beginEdit',_node.target);
	//判断选择的菜单项是否是重命名按钮
	}else if(item.name === "rename"){
		//开始编辑选中的节点
		tree.tree('beginEdit',node.target);
	//判断选择的菜单项是否是删除按钮
	}else if(item.name === "delete"){
		//提示用户确认是否删除。如果没有特殊要求，凡事删除操作都需要提示用户
		$.messager.confirm('确认','确定删除名为 '+node.text+' 的分类吗？',function(r){
			//用户选择确认，执行以下逻辑
			if(r){
				$.ajax({
     			   type: "POST",
     			   url: "/rest/content/category/delete",
     			   data : {parentId:node.parentId,id:node.id},
     			   success: function(msg){
     				   //$.messager.alert('提示','新增商品成功!');
     				   //remove：移除节点
     				  tree.tree("remove",node.target);
     			   },
     			   error: function(){
     				   $.messager.alert('提示','删除失败!');
     			   }
     			});
			}
		});
	}
}
</script>
</body>
</html>