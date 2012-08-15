package voidrepo.js.gae;

import java.io.UnsupportedEncodingException;

import org.mozilla.javascript.ScriptableObject;
import org.mozilla.javascript.annotations.JSFunction;

import com.google.appengine.api.urlfetch.HTTPResponse;

public class UrlResponseHostObject extends ScriptableObject {

	private static final long serialVersionUID = 7633386385843988315L;

	private HTTPResponse resp;

	@Override
	public String getClassName() {
		return "UrlResponse";
	}

	public HTTPResponse getResp() {
		return resp;
	}

	public void setResp(HTTPResponse resp) {
		this.resp = resp;
	}

	@JSFunction
	public String contentText() {
		try {
			byte[] content = this.resp.getContent();
			String ret = new String(content, "utf-8");
			return ret;
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		}
	}

}
