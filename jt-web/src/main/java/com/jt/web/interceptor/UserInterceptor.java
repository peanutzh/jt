package com.jt.web.interceptor;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.ibatis.plugin.Intercepts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.jt.common.po.User;
import com.jt.common.util.MapperUtil;
import com.jt.common.util.UserThreadLocalUtil;

import redis.clients.jedis.JedisCluster;
public class UserInterceptor implements HandlerInterceptor{
	
	@Autowired
	private JedisCluster jedisCluster;
	/**
	 * 执行业务之前拦截   返回值true请求放行 false请求拦截,添加重定向地址
	 * 1.通过request对象获取Cookie.
	 * 2.从Cookie中获取token数据.
	 * 	 如果没有数据.则证明用户没有登陆.
	 * 3.根据token数据获取redis中的信息
	 * 	 如果redis缓存中,没有数据,则需要用户重新登陆.
	 * 4.如果token中有数据.redis中有记录.则表示用户
	 *   已经登陆.页面正确跳转.
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		//1.获取cookie
		Cookie[] cookies = request.getCookies();
		String token = null;
		for (Cookie cookie : cookies) {
			
			if("JT_TICKET".equals(cookie.getName())) {
				token = cookie.getValue();
				break;
			}
		}
		//2.判断是否有记录
		if(!StringUtils.isEmpty(token)) {
			//2.2配置redis中是否有记录
			String userJSON = 
					jedisCluster.get(token);
			if(!StringUtils.isEmpty(userJSON)) {
				
				User user = MapperUtil
						.toObject(userJSON, User.class);
				Long userId = user.getId();
				request
					//.getSession()
					.setAttribute("JT_WEB_USER", userId);
				UserThreadLocalUtil.set(user);
				return true; //表示放行
			}
		}
		//证明用户没有登陆,需要重定向到登陆页面
		response.sendRedirect("/user/login.html");
		return false;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		//删除数据,防止内存泄漏
		request.getSession().removeAttribute("JT_WEB_USER");
		UserThreadLocalUtil.remove();
	}

}
