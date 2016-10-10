/**
 * 系统名称：共通方法
 * 
 * 功能概要：共通方法的定义和实现
 * 
 * 备注:无
 * 
 * 版本履历 日期：2013/10/10 所属：如云 修改人：王剑 修改概要：初版作成
 */
package summer.base.util;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

public class CommonFun {

	/**
	 * 取得request中的数据
	 * 
	 * @param request
	 *            请求
	 * @return request中的数据
	 */
	public static Map<String, Object> getParamsMap(HttpServletRequest request) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		Enumeration<String> pNames = request.getParameterNames();
		while (pNames.hasMoreElements()) {
			String pName = pNames.nextElement();
			paramMap.put(pName, request.getParameter(pName));
		}
		return paramMap;
	}

	
	/**
	 * 为数据生成主键
	 * @param value
	 * @return
	 */
	public static String getRandomNo(String value){
		String temp = "1234567890";
		String randomNo=value.length()>7?value.substring(0, 7):value;
		for (int i = 0; i < 15; i++) {
			randomNo += temp.charAt((int) Math.floor(Math.random() * temp.length()));
		}
		return randomNo;
		
	}
}