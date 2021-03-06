package com.taotao.portal.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.taotao.manager.pojo.User;
import com.taotao.portal.util.CookieUtils;
import com.taotao.sso.service.UserService;

public class OrderHandlerInterceptor implements HandlerInterceptor {

	@Value("${TT_TICKET}")
	private String TT_TICKET;

	@Autowired
	private UserService userService;

	// 执行Controller方法之前，前进入这个方法
	// 权限拦截，用户登录拦截
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		// 获取cookie中的ticket，这个表示用户有没有登陆过
		String ticket = CookieUtils.getCookieValue(request, this.TT_TICKET);

		// 判断ticket是否为空
		if (StringUtils.isBlank(ticket)) {
			// 如果为空表示用户未登录
			// 获取用户现在的登录地址
			String redirectURL = request.getRequestURL().toString();
			// System.out.println(redirectURL);
			// 跳转到登录页面,需要保存用户之前的登录地址，作为参数传递
			response.sendRedirect("http://www.taotao.com/page/login.html?redirectURL=" + redirectURL);

			// 拦截
			return false;
		}

		// 使用ticket获取单点登录中redis的用户信息，用户是否登录超时
		User user = this.userService.queryUserByTicket(ticket);
		if (user == null) {
			// 如果查询到的用户数据为空
			// 跳转到登录页
			response.sendRedirect("http://www.taotao.com/page/login.html");

			// 拦截
			return false;
		}
		// 如果不为空，把用户数据放到request中
		request.setAttribute("user", user);

		// 登录成功，放行
		return true;
	}

	// 执行Controller方法之后，但是返回ModeAndView之前进入这个方法
	// 需求：给每个页面都有一个通知栏，所有页面展示的通知栏内容一样
	// 需要展示公共数据的需求
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		// TODO Auto-generated method stub

	}

	// 执行Controller方法之后，而且返回了ModelAndView之后，执行
	// 异常处理，日志
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		// TODO Auto-generated method stub

	}

}
