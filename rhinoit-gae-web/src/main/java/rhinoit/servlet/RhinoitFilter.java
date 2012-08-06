package rhinoit.servlet;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.net.URL;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.Function;
import org.mozilla.javascript.Scriptable;
import org.mozilla.javascript.ScriptableObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import rhinoit.js.RhinoUtil;

public class RhinoitFilter implements Filter {

	private static final Logger LOG = LoggerFactory
			.getLogger(RhinoitFilter.class);

	private Function service;

	private Scriptable scope;

	@Override
	public void init(FilterConfig cfg) throws ServletException {
		Context cx = Context.enter();
		try {
			String resource = "/WEB-INF/rhinoit.js";
			URL url = cfg.getServletContext().getResource(resource);
			LOG.info("Rhinoit: " + url);
			scope = cx.initStandardObjects();
			ScriptableObject.defineClass(scope, HttpRequestHostObject.class);
			ScriptableObject.defineClass(scope, HttpResponseHostObject.class);
			RhinoUtil.source(scope, url);
			check();
		} catch (MalformedURLException e) {
			throw new RuntimeException(e);
		} catch (IllegalAccessException e) {
			throw new RuntimeException(e);
		} catch (InstantiationException e) {
			throw new RuntimeException(e);
		} catch (InvocationTargetException e) {
			throw new RuntimeException(e);
		} finally {
			Context.exit();
		}
	}

	private void check() {
		Object service = scope.get("service", scope);
		if (service == null) {
			throw new RuntimeException("service is required");
		}
		if (!(service instanceof Function)) {
			throw new RuntimeException("service must be a function: " + service);
		}
		this.service = (Function) service;
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		HttpRequestHostObject req = HttpRequestHostObject
				.create((HttpServletRequest) request);
		HttpResponseHostObject resp = HttpResponseHostObject
				.create((HttpServletResponse) response);
		Context cx = Context.enter();
		try {
			service.call(cx, scope, scope, new Object[] { req, resp });
		} finally {
			Context.exit();
		}
	}

	@Override
	public void destroy() {
		service = null;
		scope = null;
	}

}
