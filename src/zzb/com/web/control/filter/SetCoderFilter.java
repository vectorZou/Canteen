package zzb.com.web.control.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.dispatcher.StrutsRequestWrapper;


import zzb.com.web.utils.ReqParamsEncoding;

public class SetCoderFilter implements Filter {

	@Override
	public void destroy() {
		
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;
		String utf_8 = "UTF-8";
		/***
		 * 对应前台通过post提交过来的数据，我们可以直接通过request对象来设置parameter的编码
		 * 但是通过get提交过来的数据，这种方式则不起作用，原因有很多，所以，我做了一个辅助类，用于处理这种乱码的问题；
		 */
		res.setCharacterEncoding(utf_8);
		res.setHeader("pragma", "no-cache");
		res.setHeader("cache-contdol", "no-cache");
		res.setHeader("expires", "0");
		
		String method = req.getMethod();
		if("post".equals(method.toLowerCase())) {
			req.setCharacterEncoding(utf_8);
			chain.doFilter(new StrutsRequestWrapper(req), res); // 可以查看StrutsRequestWrapper的源码就知道了，因为它不会再解析request了！
		}else{
			String iso_8895_1 = "ISO-8859-1";
			req.setAttribute("parameterMap", new ReqParamsEncoding(req.getParameterMap(), iso_8895_1, utf_8));
			chain.doFilter(req, res);
		}
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		
	}

}
