package com.taotao.search.controller;

import java.io.UnsupportedEncodingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.taotao.common.pojo.TaoResult;
import com.taotao.manager.pojo.Item;
import com.taotao.search.service.SearchService;

@Controller
@RequestMapping("search")
public class SearchController {

	@Autowired
	private SearchService searchService;

	@Value("${TAOTAO_SEARCH_ITEM}")
	private Integer rows;

	// http://search.taotao.com/search.html?q=小米
	/**
	 * 搜索商品
	 * 
	 * @param model
	 * @param q
	 * @param page
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET)
	public String search(Model model, String q, @RequestParam(value = "page", defaultValue = "1") Integer page) {

		try {
			// 解决get请求的乱码问题
			q = new String(q.getBytes("ISO-8859-1"), "UTF-8");
			//q = new String(q.getBytes("ISO-8859-1"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// 调用服务查询商品
		TaoResult<Item> taoResult = this.searchService.search(q, page, this.rows);

		// 把查询关键词放到Model中。
		model.addAttribute("query", q);
		// 把查询结果集放到Model中。
		model.addAttribute("itemList", taoResult.getRows());
		// 把当前页放到Model中。
		model.addAttribute("page", page);
		// 把总页数放到Model中。
		// 计算总页数：(total+rows-1)/rows
		// 获取总记录数
		long total = taoResult.getTotal();
		model.addAttribute("totalPages", (total + this.rows - 1) / this.rows);

		return "search";

	}

}
