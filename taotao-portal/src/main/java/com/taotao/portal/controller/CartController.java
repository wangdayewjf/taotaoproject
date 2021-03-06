package com.taotao.portal.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taotao.cart.service.CartService;
import com.taotao.manager.pojo.Cart;
import com.taotao.manager.pojo.User;
import com.taotao.portal.service.CartCookieService;
import com.taotao.portal.util.CookieUtils;
import com.taotao.sso.service.UserService;

@Controller
@RequestMapping("cart")
public class CartController {

	@Value("${TT_TICKET}")
	private String TT_TICKET;

	@Autowired
	private CartService cartService;

	@Autowired
	private UserService userService;

	@Autowired
	private CartCookieService cartCookieService;

	// 添加商品到购物车请求,直接跳转到购物车页
	// http://www.taotao.com/cart/1474391939.html?num=5
	/**
	 * 添加商品到购物车中
	 * 
	 * @param request
	 * @param itemId
	 * @param num
	 * @return
	 */
	@RequestMapping(value = "{itemId}", method = RequestMethod.GET)
	public String addItemForCart(HttpServletRequest request, HttpServletResponse response, @PathVariable Long itemId,
			@RequestParam(value = "num") Integer num) {
		// 获取用户登录信息
		// 获取cookie中的ticket
		String ticket = CookieUtils.getCookieValue(request, this.TT_TICKET);
		// 使用单点登录服务根据ticket查询用户登录数据
		User user = this.userService.queryUserByTicket(ticket);

		// 判断用户信息是否为空
		if (user != null) {
			// 如果不为空，表使用用户已登录
			this.cartService.addItemForCart(user.getId(), itemId, num);

		} else {
			// 如果为空，表使用用户未登录
			this.cartCookieService.addItemForCart(request, response, itemId, num);
		}

		// 重定向到购物车详情页
		return "redirect:/cart/show.html";

	}

	// 展示购物车详情
	// http://www.taotao.com/cart/show.html
	/**
	 * 展示购物车详情
	 * 
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "show", method = RequestMethod.GET)
	public String show(Model model, HttpServletRequest request) {
		// 获取用户信息
		// 从cookie中获取ticket
		String ticket = CookieUtils.getCookieValue(request, this.TT_TICKET);
		// 使用单点登录服务根据ticket查询用户登信息
		User user = this.userService.queryUserByTicket(ticket);

		// 声明存放购物车得list
		List<Cart> list = null;

		// 判断用户数据是否为空
		if (user != null) {
			// 如果不为空表示用户已登录
			list = this.cartService.queryCartByUserId(user.getId());
		} else {
			// 如果为空表示用户未登录
			// 查询cookie中的购物车
			list = this.cartCookieService.queryCartByCookie(request);

		}

		// 把查询到的购物车放到Model中，传递给页面
		model.addAttribute("cartList", list);

		// 跳转到购物车页面
		return "cart";
	}

	// http://www.taotao.com/service/cart/update/num/{itemId}/{num}
	/**
	 * 修改购物车商品数量
	 * 
	 * @param request
	 * @param itemId
	 * @param num
	 */
	@RequestMapping(value = "update/num/{itemId}/{num}", method = RequestMethod.POST)
	@ResponseBody
	public void updateNumByCart(HttpServletRequest request, HttpServletResponse response, @PathVariable Long itemId,
			@PathVariable Integer num) {
		// 获取用户登录信息
		// 获取cookie中得ticket
		String ticket = CookieUtils.getCookieValue(request, this.TT_TICKET);
		// 使用ticket查询单点登录服务，获取用户登录信息
		User user = this.userService.queryUserByTicket(ticket);

		// 判断用户信息是否获取到
		if (user != null) {
			// 如果不为空，表示用户已登录
			// 使用购物车服务把购物车得商品数量修改
			this.cartService.updateNumByCart(user.getId(), itemId, num);
		} else {
			// 如果为空，表示用户未登录
			// 使用购物车服务把购物车的商品数量修改
			this.cartCookieService.updateNumByCart(request, response, itemId, num);
		}

	}

	// http://www.taotao.com/cart/delete/1474391932.html
	/**
	 * 删除购物车中得商品
	 * 
	 * @param request
	 * @param itemId
	 * @return
	 */
	@RequestMapping(value = "delete/{itemId}", method = RequestMethod.GET)
	public String deleteItemByCart(HttpServletRequest request, HttpServletResponse response,
			@PathVariable Long itemId) {
		// 获取用户登录信息
		// 获取cookie中得ticket
		String ticket = CookieUtils.getCookieValue(request, this.TT_TICKET);
		// 使用ticket查询单点登录服务，获取用户登录信息
		User user = this.userService.queryUserByTicket(ticket);

		// 判断用户信息是否获取到
		if (user != null) {
			// 如果不为空，表示用户已登录
			// 使用购物车服务把购物车的商品删除
			this.cartService.deleteItemByCart(user.getId(), itemId);
		} else {
			// 如果为空，表示用户未登录
			// 使用购物车服务把购物车得商品数量修改
			this.cartCookieService.deleteItemByCart(request, response, itemId);
		}

		// 重定向到购物车详情页
		return "redirect:/cart/show.html";
	}

}
