package rhinoit.js;

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

	public static String jsFunction_info(Context cx, Scriptable thisObj,
			Object[] args, Function funObj) {
		LogConsole console = (LogConsole) thisObj;
		String ret = console.format(args);
		LOG.info(ret);
		return ret;
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
