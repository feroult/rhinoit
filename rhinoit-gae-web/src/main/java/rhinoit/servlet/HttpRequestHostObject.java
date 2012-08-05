package rhinoit.servlet;

import javax.servlet.http.HttpServletRequest;

import org.mozilla.javascript.ScriptableObject;

public class HttpRequestHostObject extends ScriptableObject {

	private static final long serialVersionUID = 8392760425756547087L;

	private HttpServletRequest request;

	public static HttpRequestHostObject create(HttpServletRequest request) {
		HttpRequestHostObject ret = new HttpRequestHostObject();
		ret.request = request;
		return ret;
	}

	@Override
	public String getClassName() {
		return "HttpRequest";
	}

	public HttpServletRequest getRequest() {
		return request;
	}

}
