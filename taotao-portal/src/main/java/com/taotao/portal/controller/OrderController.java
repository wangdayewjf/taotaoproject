package com.taotao.portal.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taotao.cart.service.CartService;
import com.taotao.manager.pojo.Cart;
import com.taotao.manager.pojo.Order;
import com.taotao.manager.pojo.User;
//import com.taotao.order.service.OrderService;//这里需要下载包
import com.taotao.portal.util.CookieUtils;
import com.taotao.sso.service.UserService;

@Controller
@RequestMapping("order")
public class OrderController {

	@Value("${TT_TICKET}")
	private String TT_TICKET;

	@Autowired
	private CartService cartService;

	@Autowired
	private UserService userService;

	//@Autowired
	//private OrderService orderService;

	// http://www.taotao.com/order/create.html
	/**
	 * 跳转到订单结算页
	 * 
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "create", method = RequestMethod.GET)
	public String create(Model model, HttpServletRequest request) {
		// 获取cookie的ticket数据
		// String ticket = CookieUtils.getCookieValue(request, this.TT_TICKET);
		// 使用ticket获取用户登录数据
		// User user = this.userService.queryUserByTicket(ticket);

		// 从request中，获取之前拦截器放进去的user数据
		User user = (User) request.getAttribute("user");

		// if (user != null) {
		// 需要把购物车数据查询出来
		// 把用户的所有购物车数据加入到订单中
		List<Cart> list = this.cartService.queryCartByUserId(user.getId());

		// 把购物车数据放到Model的carts
		model.addAttribute("carts", list);
		// }

		// 跳转到订单结算页
		return "order-cart";

	}

	// type : "POST",
	// url : "/service/order/submit",
	// if(result.status == 200)
	// location.href = "/order/success.html?id="+result.data;
	/**
	 * 创建订单
	 * 
	 * @param request
	 * @param order
	 * @return
	 */
	@RequestMapping(value = "submit", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> submit(HttpServletRequest request, Order order) {
		// 获取用户的登录信息，放到订单中
		User user = (User) request.getAttribute("user");
		// 设置用户id
		order.setUserId(user.getId());
		// 设置用户昵称
		order.setBuyerNick(user.getUsername());

		// 调用服务，创建订单
		//String orderId = this.orderService.saveOrder(order);

		// 声明返回的Map
		Map<String, Object> map = new HashMap<>();

		// 判断订单是否创建成功
		
		/*if (StringUtils.isNotBlank(orderId)) {
			// 如果创建成功，封装返回的数据
			// 200
			map.put("status", 200);
			// 订单号
			map.put("data", orderId);
		}
*/
		// 返回Map结果数据
		return map;
	}

	// http://www.taotao.com/order/success.html?id=100000047
	@RequestMapping(value = "success", method = RequestMethod.GET)
	public String success(Model model, @RequestParam(value = "id") String orderId) {
		// 根据订单号查询订单
		//Order order = this.orderService.queryOrderByOrderId(orderId);

		// 把订单数据放到Model中，传递给前台页面
		//model.addAttribute("order", order);

		// 把送达时间放到Model中，传递给前台页面
		// 这里送达时间是当前时间往后推两天,输出的格式：yyyy年MM月dd日 HH时mm分ss秒SSS毫秒
		model.addAttribute("date", new DateTime().plusDays(2).toString("yyyy年MM月dd日 HH时mm分ss秒SSS毫秒"));

		return "success";
	}

}
