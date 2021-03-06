package com.taotao.portal.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.taotao.manager.pojo.Cart;
import com.taotao.manager.pojo.Item;
import com.taotao.manager.service.ItemService;
import com.taotao.portal.util.CookieUtils;

@Service
public class CartCookieService {

	private static final ObjectMapper MAPPER = new ObjectMapper();

	@Value("${TT_CART}")
	private String TT_CART;

	@Autowired
	private ItemService itemService;

	/**
	 * 保存商品到cookie中的购物车
	 * 
	 * @param request
	 * @param response
	 * @param itemId
	 * @param num
	 */
	public void addItemForCart(HttpServletRequest request, HttpServletResponse response, Long itemId, Integer num) {
		// 获取cookie中的购物车
		List<Cart> list = this.queryCartByCookie(request);

		// 遍历购物车
		Cart cart = null;
		for (Cart c : list) {
			// 判断购物车中是否存在该商品
			if (c.getItemId().longValue() == itemId.longValue()) {
				cart = c;
				break;
			}
		}

		if (cart != null) {
			// 如果存在商品，商品数量相加
			cart.setNum(cart.getNum() + num);
			cart.setUpdated(new Date());

		} else {
			// 如果不存在该商品，添加商品
			// 根据商品id获取商品的详细信息
			Item item = this.itemService.queryById(itemId);

			cart = new Cart();

			// 设置购物车数据
			cart.setItemId(itemId);
			cart.setUserId(null);
			cart.setNum(num);
			// 只需要一张图片即可
			if (StringUtils.isNotBlank(item.getImage())) {
				cart.setItemImage(item.getImages()[0]);
			} else {
				cart.setItemImage(null);
			}
			cart.setItemPrice(item.getPrice());
			cart.setItemTitle(item.getTitle());
			cart.setCreated(new Date());
			cart.setUpdated(cart.getCreated());

			// 加入到购物车中
			list.add(cart);

		}

		try {
			// 把修改后的购物车放到cookie中
			CookieUtils.setCookie(request, response, this.TT_CART, MAPPER.writeValueAsString(list), 60 * 60 * 24 * 3,
					true);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * 查询cookie中的购物车
	 * 
	 * @param request
	 * @return
	 */
	public List<Cart> queryCartByCookie(HttpServletRequest request) {
		// 获取cookie中的购物车数据
		String json = CookieUtils.getCookieValue(request, this.TT_CART, true);

		// 判断获取的数据不为空
		if (StringUtils.isNotBlank(json)) {
			try {
				// 把json数据转为List集合
				List<Cart> list = MAPPER.readValue(json,
						MAPPER.getTypeFactory().constructCollectionType(List.class, Cart.class));

				// 返回List
				return list;

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		// 如果获取的数据为空，或者解析异常，不能返回null，需要返回一个空集合，直接new
		return new ArrayList<Cart>();
	}

	/**
	 * 修改cookie中得购物车商品数量
	 * 
	 * @param request
	 * @param response
	 * @param itemId
	 * @param num
	 */
	public void updateNumByCart(HttpServletRequest request, HttpServletResponse response, Long itemId, Integer num) {
		// 查询cookie中的购物车
		List<Cart> list = this.queryCartByCookie(request);

		// 遍历购物车
		for (Cart cart : list) {
			try {
				// 判断是否存在购物车
				if (cart.getItemId().longValue() == itemId.longValue()) {
					// 如果存在则直接设置商品数量
					cart.setNum(num);
					cart.setUpdated(new Date());

					// 把修改后的购物车放到cookie
					CookieUtils.setCookie(request, response, this.TT_CART, MAPPER.writeValueAsString(list),
							60 * 60 * 24 * 3, true);

					// 跳出循环
					break;
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		// 如果商品不存在则神马都不干

	}

	/**
	 * 删除cookie中得购物车的商品
	 * 
	 * @param request
	 * @param response
	 * @param itemId
	 */
	public void deleteItemByCart(HttpServletRequest request, HttpServletResponse response, Long itemId) {
		// 查询
		List<Cart> list = this.queryCartByCookie(request);

		// 遍历
		for (Cart cart : list) {
			// 判断
			try {
				if (cart.getItemId().longValue() == itemId.longValue()) {
					// 删除
					list.remove(cart);

					// 保存
					CookieUtils.setCookie(request, response, this.TT_CART, MAPPER.writeValueAsString(list),
							60 * 60 * 24 * 3, true);

					// 跳出循环。删除操作的时候，必须要执行跳出，就会抛异常
					break;
				}
			} catch (JsonProcessingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

}
