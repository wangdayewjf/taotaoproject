var TTCart = {
	load : function(){ // 加载购物车数据
		
	},
	itemNumChange : function(){
		//class选择器，选择的就是+得按钮
		$(".increment").click(function(){//＋
			//siblings：获取同级元素，input标签，就是存放购物车商品数量的标签
			var _thisInput = $(this).siblings("input");
			//把标签中得商品数量加一
			_thisInput.val(eval(_thisInput.val()) + 1);
			//发起Ajax得post请求，url：http://www.taotao.com/service/cart/update/num/{itemId}/{num}
			//attr:获取元素的属性
			$.post("/service/cart/update/num/"+_thisInput.attr("itemId")+"/"+_thisInput.val(),function(data){
				//刷新总价
				TTCart.refreshTotalPrice();
			});
		});
		$(".decrement").click(function(){//-
			var _thisInput = $(this).siblings("input");
			//判断商品数量是否为1
			if(eval(_thisInput.val()) <= 1){
				return ;
			}
			_thisInput.val(eval(_thisInput.val()) - 1);
			$.post("/service/cart/update/num/"+_thisInput.attr("itemId")+"/"+_thisInput.val(),function(data){
				TTCart.refreshTotalPrice();
			});
		});
		$(".quantity-form .quantity-text").rnumber(1);//限制只能输入数字
		$(".quantity-form .quantity-text").change(function(){
			var _thisInput = $(this);
			$.post("/service/cart/update/num/"+_thisInput.attr("itemId")+"/"+_thisInput.val(),function(data){
				TTCart.refreshTotalPrice();
			});
		});
	},
	refreshTotalPrice : function(){ //重新计算总价
		var total = 0;
		$(".quantity-form .quantity-text").each(function(i,e){
			var _this = $(e);
			total += (eval(_this.attr("itemPrice")) * 10000 * eval(_this.val())) / 10000;
		});
		$(".totalSkuPrice").html(new Number(total/100).toFixed(2)).priceFormat({ //价格格式化插件
			 prefix: '￥',
			 thousandsSeparator: ',',
			 centsLimit: 2
		});
	}
};

$(function(){
	TTCart.load();
	TTCart.itemNumChange();
});