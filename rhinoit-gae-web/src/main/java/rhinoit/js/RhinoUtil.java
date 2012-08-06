package rhinoit.js;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.URL;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.Function;
import org.mozilla.javascript.Scriptable;

import com.googlecode.mycontainer.commons.io.IOUtil;

public class RhinoUtil {

	public static void source(Scriptable scope, Reader in, String filename) {
		Context cx = Context.enter();
		try {
			cx.evaluateReader(scope, in, filename, 1, null);
		} catch (IOException e) {
			throw new RuntimeException(e);
		} finally {
			Context.exit();
		}
	}

	public static void source(Scriptable scope, URL url) {
		Reader in = null;
		try {
			in = new InputStreamReader(
					new BufferedInputStream(url.openStream()), "utf-8");
			source(scope, in, url.toString());
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		} catch (IOException e) {
			throw new RuntimeException(e);
		} finally {
			IOUtil.close(in);
		}
	}

	public static Object eval(Scriptable scope, String code, String name) {
		Context cx = Context.enter();
		try {
			return cx.evaluateString(scope, code, name, 1, null);
		} finally {
			Context.exit();
		}
	}

	public static void sourceClasspath(Scriptable scope, Class<?> clazz,
			String name) {
		URL url = null;
		if (clazz == null) {
			url = RhinoUtil.class.getClassLoader().getResource(name);
		} else {
			url = clazz.getResource(name);
		}
		if (url == null) {
			throw new RuntimeException("not found: " + name + " using " + clazz);
		}
		source(scope, url);
	}

	public static Scriptable newScopeObject(Scriptable scope, String constructor) {
		Function func = (Function) scope.get(constructor, scope);
		Scriptable ret = func.construct(Context.getCurrentContext(), scope,
				new Object[0]);
		return ret;
	}

}
