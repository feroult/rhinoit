package rhinoit.heroku.servlet;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.LinkedHashMap;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.repackaged.com.google.common.util.Base64;
import com.google.appengine.repackaged.com.google.common.util.Base64DecoderException;

public class SecurityFilter implements Filter {

	private static HashMap<String, String> users;

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		users = new LinkedHashMap<String, String>();
		users.put("restitory", "TWADivJAlFXR9y8b");
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {

		final HttpServletRequest req = (HttpServletRequest) request;
		final HttpServletResponse resp = (HttpServletResponse) response;

		HashMap<String, String> header = parseHeader(req
				.getHeader("Authorization"));

		if (!validUser(header.get("username"), header.get("password"))) {
			resp.setStatus(401);
			resp.setHeader("WWW-Authenticate",
					"basic realm=\"Auth (" + System.currentTimeMillis() + ")\"");
			return;
		}

		chain.doFilter(request, response);
	}

	private boolean validUser(String username, String password) {
		return users.get(username) != null
				&& users.get(username).equals(password);
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}

	private HashMap<String, String> parseHeader(String header)
			throws UnsupportedEncodingException {

		HashMap<String, String> parsedHeader = new LinkedHashMap<String, String>();

		if ((header != null) && header.startsWith("Basic ")) {
			byte[] base64Token = header.substring(6).getBytes("UTF-8");
			String token;

			try {
				token = new String(Base64.decode(base64Token), "UTF-8");
			} catch (Base64DecoderException e) {
				throw new RuntimeException(e);
			}

			String username = "";
			String password = "";
			int delim = token.indexOf(":");

			if (delim != -1) {
				username = token.substring(0, delim);
				password = token.substring(delim + 1);
			}

			parsedHeader.put("username", username);
			parsedHeader.put("password", password);
		}

		return parsedHeader;
	}

}
