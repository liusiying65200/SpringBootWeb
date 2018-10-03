/**
 * 
 */
package net.hualisheng.filter;

import java.io.IOException;
import java.util.Enumeration;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * @author lsy
 *
 */
public class UserFilter implements Filter {

	
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		
			String filterName = filterConfig.getFilterName();
			Enumeration<String> initParameterNames = filterConfig.getInitParameterNames();
			ServletContext servletContext = filterConfig.getServletContext();
			String serverInfo = servletContext.getServerInfo();
			System.err.println(serverInfo);
			System.err.println(filterName);
			while (initParameterNames.hasMoreElements()) {
				String string = (String) initParameterNames.nextElement();
				System.err.println(string);
			}
	}

	
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		Enumeration<String> attributeNames = request.getAttributeNames();
		while (attributeNames.hasMoreElements()) {
			String string = (String) attributeNames.nextElement();
			System.err.println(string);
		}

	}

	
	@Override
	public void destroy() {
		System.err.println(this.toString());

	}

}
