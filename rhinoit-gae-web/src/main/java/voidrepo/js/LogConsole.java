package voidrepo.js;

import java.util.Formatter;

import org.apache.commons.lang3.ArrayUtils;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.Function;
import org.mozilla.javascript.Scriptable;
import org.mozilla.javascript.ScriptableObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LogConsole extends ScriptableObject {

	private static final long serialVersionUID = 8080161372977668796L;

	private static final Logger LOG = LoggerFactory.getLogger(LogConsole.class);

	@Override
	public String getClassName() {
		return "console";
	}

	public static String jsFunction_error(Context cx, Scriptable thisObj,
			Object[] args, Function funObj) {
		String ret = format(thisObj, args);
		LOG.error(ret);
		return ret;
	}

	public static String jsFunction_info(Context cx, Scriptable thisObj,
			Object[] args, Function funObj) {
		String ret = format(thisObj, args);
		LOG.info(ret);
		return ret;
	}

	private static String format(Scriptable thisObj, Object[] args) {
		LogConsole console = (LogConsole) thisObj;
		String msg = console.format(args);
		return msg;
	}

	public String format(Object[] args) {
		if (args.length == 0) {
			return null;
		}
		String str = args[0] == null ? "" : args[0].toString();
		args = ArrayUtils.remove(args, 0);
		Formatter formatter = new Formatter();
		formatter.format(str, args);
		String ret = formatter.out().toString();
		return ret;
	}

}
