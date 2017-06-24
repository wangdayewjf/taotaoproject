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

<link href="/js/kindeditor-4.1.10/themes/default/default.css" type="text/css" rel="stylesheet">
<script type="text/javascript" charset="utf-8" src="/js/kindeditor-4.1.10/kindeditor-all-min.js"></script>
<script type="text/javascript" charset="utf-8" src="/js/kindeditor-4.1.10/lang/zh_CN.js"></script>
<title>新增商品</title>
</head>
<body>
<div style="padding:10px 10px 10px 10px">
	<form id="itemAddForm" class="itemForm" method="post">
	    <table cellpadding="5">
	        <tr>
	            <td>商品类目:</td>
	            <td>
	            	<a href="javascript:void(0)" class="easyui-linkbutton selectItemCat">选择类目</a>
	            	<span ></span>
	            	<input type="hidden" name="cid" style="width: 280px;"></input>
	            </td>
	        </tr>
	        <tr>
	            <td>商品标题:</td>
	            <td><input class="easyui-textbox" type="text" name="title" data-options="required:true" style="width: 280px;"></input></td>
	        </tr>
	        <tr>
	            <td>商品卖点:</td>
	            <td><input class="easyui-textbox" name="sellPoint" data-options="multiline:true,validType:'length[0,150]'" style="height:60px;width: 280px;"></input></td>
	        </tr>
	        <tr>
	            <td>商品价格:</td>
	            <td><input class="easyui-numberbox" type="text" name="priceView" data-options="min:1,max:99999999,precision:2,required:true" />
	            	<input type="hidden" name="price"/>
	            </td>
	        </tr>
	        <tr>
	            <td>库存数量:</td>
	            <td><input class="easyui-numberbox" type="text" name="num" data-options="min:1,max:99999999,precision:0,required:true" /></td>
	        </tr>
	        <tr>
	            <td>条形码:</td>
	            <td>
	                <input class="easyui-textbox" type="text" name="barcode" data-options="validType:'length[1,30]'" />
	            </td>
	        </tr>
	        <tr>
	            <td>商品图片:</td>
	            <td>
	            	 <a href="javascript:void(0)" class="easyui-linkbutton picFileUpload">上传图片</a>
	            	 <div class="pics"><ul></ul></div>
	                 <input type="hidden" name="image"/>
	            </td>
	        </tr>
	        <tr>
	            <td>商品描述:</td>
	            <td>
	                <textarea style="width:800px;height:300px;visibility:hidden;" name="desc"></textarea>
	            </td>
	        </tr>
	        <tr class="params hide">
	        	<td>商品规格:</td>
	        	<td>
	        		
	        	</td>
	        </tr>
	    </table>
	    <input type="hidden" name="itemParams"/>
	</form>
	<div style="padding:5px">
	    <a href="javascript:void(0)" class="easyui-linkbutton" onclick="submitForm()">提交</a>
	    <a href="javascript:void(0)" class="easyui-linkbutton" onclick="clearForm()">重置</a>
	</div>
</div>
<script type="text/javascript">
	//编辑器参数
	kingEditorParams = {
		filePostName  : "uploadFile",   //上传的图片的参数名
		uploadJson : '/rest/pic/upload',	//上传路径
		dir : "image" //上传的文件类型，这里是图片类型
	};
	
	var itemAddEditor ;
	//页面加载后执行以下逻辑
	$(function(){
		//创建富文本编辑器
		itemAddEditor = KindEditor.create("#itemAddForm [name=desc]", kingEditorParams);
		//初始化类目选择
		initItemCat();
		//初始化图片上传
		initPicUpload();
	});
	
	//提交商品信息到后台
	function submitForm(){
		//校验表单
		if(!$('#itemAddForm').form('validate')){
			$.messager.alert('提示','表单还未填写完成!');
			return ;
		}
		//把以元为单位的价格，乘以100，写入到提交到后台的input标签
		//eval：计算字符串类型的数字。  不使用eval："1"+"1"="11", 使用eval： "1"+"1"="2"
		$("#itemAddForm [name=price]").val(eval($("#itemAddForm [name=priceView]").val()) * 100);
		//调用富文本编辑器的同步方法，目的就是把富文本编辑器编辑区域的内容，写入到多行文本域中，提交到后台
		itemAddEditor.sync();
				
		//提交到后台的RESTful
		$.ajax({
		   type: "POST",
		   url: "/rest/item",
		   data: $("#itemAddForm").serialize(),
		   success: function(msg){
			   $.messager.alert('提示','新增商品成功!');
		   },
		   error: function(){
			   $.messager.alert('提示','新增商品失败!');
		   }
		});
	}
	
	function clearForm(){
		$('#itemAddForm').form('reset');
		itemAddEditor.html('');
	}
	
	//类目选择初始化
	function initItemCat(){
		//class选择器，其实找到的是选择类目的按钮的a标签
		var selectItemCat = $(".selectItemCat");
		//给选择类目按钮，绑定点击事件
   		selectItemCat.click(function(){
   			//创建div标签，html("<ul>")：在div里添加一个ul标签
   			//打开窗口
   			$("<div>").css({padding:"5px"}).html("<ul>")
   			.window({
   				width:'500',
   			    height:"450",
   			    modal:true,
   			    closed:true,
   			    iconCls:'icon-save',
   			    title:'选择类目',
   			    //在窗口打开之后执行
   			    onOpen : function(){
   			    	//{"ul",_win}:标签选择器，查询范围是窗口
   			    	//创建树
   			    	var _win = this;
   			    	$("ul",_win).tree({
   			    		url:'/rest/item/cat',
   			    		method:'GET',
   			    		animate:true,
   			    		//给创建的树上的所有的节点，绑定点击事件
   			    		onClick : function(node){
   			    			//判断是否是叶子节点，如果是叶子节点，执行以下逻辑
   			    			if($(this).tree("isLeaf",node.target)){
   			    				// 填写到cid中
   			    				//获取提交到后台的input，把节点(商品类目)的id放到其中
   			    				selectItemCat.parent().find("[name=cid]").val(node.id);
   			    				//获取按钮的下一个元素span标签，输入节点的名称，回显数据
   			    				selectItemCat.next().text(node.text);
   			    				//关闭窗口
   			    				$(_win).window('close');
   			    			}
   			    		}
   			    	});
   			    },
   			    onClose : function(){
   			    	$(this).window("destroy");
   			    }
   			}).window('open');
   		});
    }
	
	//图片上传初始化
	function initPicUpload(){
		//获取上传图片按钮，绑定点击事件
       	$(".picFileUpload").click(function(){
       		//id选择器，获取from表单
       		var form = $('#itemAddForm');
       		//参考文档实现图片上传组件的加载
       		KindEditor.editor(kingEditorParams).loadPlugin('multiimage',function(){
       			//获取富文本编辑器
       			var editor = this;
       			//显示上传界面
       			editor.plugin.multiImageDialog({
       				//当点击"全部插入按钮"执行以下逻辑
       				//urlList：上传成功的图片的url集合
					clickFn : function(urlList) {
						//查询class为pics的元素，找里面的li标签移除，没有发现li，干嘛要删除？清理原来的回显的图片
						$(".pics li").remove();
						var imgArray = [];
						//遍历返回的图片的url集合,data就是遍历的变量
						KindEditor.each(urlList, function(i, data) {
							//把图片的url放到前面声明的数组中
							imgArray.push(data.url);
							//获取class为pics的元素里的ul比标签，在里面追加li
							//回显图片
							$(".pics ul").append("<li><a href='"+data.url+"' target='_blank'><img src='"+data.url+"' width='80' height='50' /></a></li>");
						});
						//在form表单中查询name=image的元素，其实找到的就是把图片url提交到后台的input
						//imgArray.join(",")：把数组转为字符串，多个值中间用","分隔
						form.find("[name=image]").val(imgArray.join(","));
						//关闭图片上传界面
						editor.hideDialog();
					}
				});
       		});
       	});
	}
	
</script>
</body>
</html>