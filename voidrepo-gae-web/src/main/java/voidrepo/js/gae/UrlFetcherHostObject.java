package voidrepo.js.gae;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.Scriptable;
import org.mozilla.javascript.ScriptableObject;
import org.mozilla.javascript.annotations.JSFunction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import voidrepo.js.LogConsole;
import voidrepo.js.Module;
import voidrepo.js.VoidRepoGlobal;

import com.google.appengine.api.urlfetch.FetchOptions;
import com.google.appengine.api.urlfetch.HTTPMethod;
import com.google.appengine.api.urlfetch.HTTPRequest;
import com.google.appengine.api.urlfetch.HTTPResponse;
import com.google.appengine.api.urlfetch.URLFetchService;
import com.google.appengine.api.urlfetch.URLFetchServiceFactory;

public class UrlFetcherHostObject extends Module {

	private static final long serialVersionUID = -5558107488632928488L;

	private static final Logger LOG = LoggerFactory.getLogger(LogConsole.class);

	@Override
	public void init(VoidRepoGlobal global) {
		try {
			ScriptableObject.defineClass(global, UrlResponseHostObject.class);
		} catch (IllegalAccessException e) {
			throw new RuntimeException(e);
		} catch (InstantiationException e) {
			throw new RuntimeException(e);
		} catch (InvocationTargetException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public String getClassName() {
		return "UrlFetcher";
	}

	@JSFunction
	public Scriptable send(Scriptable opts) {
		try {
			URLFetchService service = URLFetchServiceFactory
					.getURLFetchService();
			HTTPRequest req = toHTTPRequest(opts);
			LOG.info("fetch: " + debug(req));
			HTTPResponse resp = service.fetch(req);
			UrlResponseHostObject ret = (UrlResponseHostObject) Context
					.getCurrentContext().newObject(this, "UrlResponse");
			ret.setResp(resp);
			return ret;
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	private static String debug(HTTPRequest req) {
		byte[] payload = req.getPayload();
		return "[" + req.getMethod() + " " + req.getURL() + " "
				+ (payload == null ? "-1" : payload.length) + "]";
	}

	private static HTTPRequest toHTTPRequest(Scriptable opts) {
		try {
			HTTPMethod type = HTTPMethod.valueOf((String) opts
					.get("type", opts));
			URL url = new URL((String) opts.get("url", opts));
			HTTPRequest req = new HTTPRequest(url, type, FetchOptions.Builder
					.doNotFollowRedirects().disallowTruncate()
					.validateCertificate());
			Object data = opts.get("data", opts);
			if (data instanceof Scriptable) {
				data = encodeParams((Scriptable) data);
			}
			req.setPayload(((String) data).getBytes("utf-8"));
			return req;
		} catch (MalformedURLException e) {
			throw new RuntimeException(e);
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		}
	}

	private static String encodeParams(Scriptable data) {
		try {
			StringBuilder ret = new StringBuilder();
			Object[] ids = data.getIds();
			for (int i = 0; i < ids.length; i++) {
				String name = (String) ids[i];
				String value = (String) data.get(name, data);
				value = URLEncoder.encode(value, "UTF-8");
				ret.append(name).append('=').append(value);
				if (i < ids.length - 1) {
					ret.append('&');
				}
			}
			return ret.toString();
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		}
	}

}
