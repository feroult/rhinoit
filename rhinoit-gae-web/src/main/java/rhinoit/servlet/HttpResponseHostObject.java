package rhinoit.servlet;

import javax.servlet.http.HttpServletResponse;

import org.mozilla.javascript.ScriptableObject;

public class HttpResponseHostObject extends ScriptableObject {

	private static final long serialVersionUID = 8392760425756547087L;

	private HttpServletResponse response;

	public static HttpResponseHostObject create(HttpServletResponse response) {
		HttpResponseHostObject ret = new HttpResponseHostObject();
		ret.response = response;
		return ret;
	}

	@Override
	public String getClassName() {
		return "HttpResponse";
	}

	public HttpServletResponse getResponse() {
		return response;
	}

}
