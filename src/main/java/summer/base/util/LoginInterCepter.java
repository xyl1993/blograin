package summer.base.util;

import java.io.PrintWriter;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import summer.core.constant.BusinessError;
import summer.core.constant.GlobalConstant;
import login.mapper.LoginMapper;
import login.model.UserInfo;


public class LoginInterCepter implements HandlerInterceptor {

	@Autowired
	LoginMapper userInfoMapper;

	public String[] allowUrls;// 还没发现可以直接配置不拦截的资源，所以在代码里面来排除

	public void setAllowUrls(String[] allowUrls) {
		this.allowUrls = allowUrls;
	}

	/**
	 * preHandle方法是进行处理器拦截用的，顾名思义，该方法将在Controller处理之前进行调用，
	 * SpringMVC中的Interceptor拦截器是链式的，可以同时存在
	 * 多个Interceptor，然后SpringMVC会根据声明的前后顺序一个接一个的执行
	 * ，而且所有的Interceptor中的preHandle方法都会在
	 * Controller方法调用之前调用。SpringMVC的这种Interceptor链式结构也是可以进行中断的
	 * ，这种中断方式是令preHandle的返 回值为false，当preHandle的返回值为false的时候整个请求就结束了。
	 */
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		// TODO Auto-generated method stub
		boolean flag = false;

		HashMap<String, Object> map = new HashMap<String, Object>();
		String requestUrl = request.getRequestURI().replace(
				request.getContextPath(), "");
		if (null != allowUrls && allowUrls.length >= 1) {
			for (String url : allowUrls) {
				if (requestUrl.contains(url)) {
					flag = true;
				}
			}
		}
		if (!flag) {
			String token = request.getHeader("token");
			if (token == null || ("").equals(token)) {
				// 跳转到返回数据的controller处理
				map.put(GlobalConstant.CODE, BusinessError.UNAUTHORIZED_USER);
				PrintWriter out = response.getWriter();
				JSONObject json = JSONObject.fromObject(map);
				out.println(json);
				flag = false;
			} else {
				// 判断是否过期
				UserInfo userInfo = userInfoMapper.getUserDetail(token);
				if (userInfo == null) {
					// 跳转到返回数据的controller处理
					map.put(GlobalConstant.CODE,
							BusinessError.UNAUTHORIZED_USER);
					PrintWriter out = response.getWriter();
					JSONObject json = JSONObject.fromObject(map);
					out.println(json);
					flag = false;
				} else {
					Date lastLoginTime = userInfo.getLastLoginTime();
					// 判断token是否过期
					Calendar calendarCurrent = Calendar.getInstance();
					// 初始化 Calendar 对象，但并不必要，除非需要重置时间
					calendarCurrent.setTime(new Date());
					// 创建 发送验证码时间 Calendar 对象
					Calendar calendarSend = Calendar.getInstance();
					calendarSend.setTime(lastLoginTime);
					// 判断验证使用过或者验证码时间超过（默认10分钟）
					calendarSend.add(Calendar.DAY_OF_MONTH, 10);

					int loginStatus = userInfo.getLoginStatus();
					if (loginStatus == 0) {
//						PrintWriter out = response.getWriter();
//						map.put(GlobalConstant.CODE,
//								BusinessError.USER_NOT_LOGIN);
//						JSONObject json = JSONObject.fromObject(map);
//						out.println(json);
						flag = true;
					} else {
						if (calendarCurrent.compareTo(calendarSend) == -1) {
							flag=true;
						} else {
							PrintWriter out = response.getWriter();
							map.put(GlobalConstant.CODE,
									BusinessError.UNAUTHORIZED_USER);
							JSONObject json = JSONObject.fromObject(map);
							out.println(json);
							flag = false;
						}
					}
				}
			}
		}

		return flag;
	}

	/**
	 * 这个方法只会在当前这个Interceptor的preHandle方法返回值为true的时候才会执行。postHandle是进行处理器拦截用的，
	 * 它的执行时间是在处理器进行处理之
	 * 后，也就是在Controller的方法调用之后执行，但是它会在DispatcherServlet进行视图的渲染之前执行
	 * ，也就是说在这个方法中你可以对ModelAndView进行操
	 * 作。这个方法的链式结构跟正常访问的方向是相反的，也就是说先声明的Interceptor拦截器该方法反而会后调用
	 * ，这跟Struts2里面的拦截器的执行过程有点像，
	 * 只是Struts2里面的intercept方法中要手动的调用ActionInvocation的invoke方法
	 * ，Struts2中调用ActionInvocation的invoke方法就是调用下一个Interceptor
	 * 或者是调用action，然后要在Interceptor之前调用的内容都写在调用invoke之前
	 * ，要在Interceptor之后调用的内容都写在调用invoke方法之后。
	 */
	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		// TODO Auto-generated method stub

	}

	/**
	 * 该方法也是需要当前对应的Interceptor的preHandle方法的返回值为true时才会执行。该方法将在整个请求完成之后，
	 * 也就是DispatcherServlet渲染了视图执行，
	 * 这个方法的主要作用是用于清理资源的，当然这个方法也只能在当前这个Interceptor的preHandle方法的返回值为true时才会执行。
	 */
	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		// TODO Auto-generated method stub

	}

}