package rhinoit.heroku.servlet;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

public class SecurityFilter implements Filter {

	private static HashMap<String, String> users;

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		users = new LinkedHashMap<String, String>();
		users.put("jow", "3213123");
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}

}
