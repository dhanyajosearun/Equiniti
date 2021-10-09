package com.pancredit.filter;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Base64;
import java.util.Enumeration;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet Filter implementation class AuthorizationFiltter
 *@author DhanyaJ  
 */
public class AuthorizationFilter implements Filter {

	private String username=null;
	private String password=null;

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		username = filterConfig.getInitParameter("username");
		password = filterConfig.getInitParameter("password");

	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

		PrintWriter out = response.getWriter();
		HttpServletRequest httpServletRequest = (HttpServletRequest)request;
		String authHeader = httpServletRequest.getHeader("authorization");
		String encodedAuth = authHeader.substring(authHeader.indexOf(' ') + 1);
		String decodedAuth = new String(Base64.getDecoder().decode(encodedAuth));
		String authUsername = decodedAuth.substring(0, decodedAuth.indexOf(':'));
		String authPassword = decodedAuth.substring(decodedAuth.indexOf(':')+1);
		
			//user name checking
			if(username.equalsIgnoreCase(authUsername)) {
				//make sure password is valid
				if(password.equalsIgnoreCase(authPassword)){
					out.println("Thank You for verifying Your details!!!");
					chain.doFilter(request, response);					
				}
				else {				
					out.println("Unauthorized access request: Password Doesnt match!!!");
					return;
				}
			}else{
				out.println("Unauthorized access request: UserName doesnt match!!!");
				return;
			}
	}

}
