package rhinoit.js;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.Function;
import org.mozilla.javascript.IdScriptableObject;
import org.mozilla.javascript.Scriptable;
import org.mozilla.javascript.ScriptableObject;

import rhinoit.servlet.RhinoUtil;

public class Global extends IdScriptableObject {

	private static final long serialVersionUID = 1981033374348195776L;

	private Map<String, Scriptable> modules = new HashMap<String, Scriptable>();

	private Set<String> requireStack = new HashSet<String>();

	@Override
	public String getClassName() {
		return "Global";
	}

	public static Object require(Context cx, Scriptable thisObj, Object[] args,
			Function funObj) {
		String uri = (String) args[0];
		Global global = (Global) ScriptableObject.getTopLevelScope(thisObj);
		return global.module(uri);
	}

	public synchronized Scriptable module(String uri) {
		Context cx = Context.enter();
		try {
			Scriptable exports = modules.get(uri);
			if (exports == null) {
				if (requireStack.contains(uri)) {
					throw new RuntimeException("it is cyclic: " + uri + " "
							+ requireStack);
				}
				requireStack.add(uri);
				Scriptable scope = cx.newObject(this);
				exports = cx.newObject(this);
				scope.put("exports", scope, exports);
				RhinoUtil.sourceClasspath(scope, Global.class, uri + ".js");
				modules.put(uri, exports);
				requireStack.remove(uri);
			}
			return exports;
		} finally {
			Context.exit();
		}

	}

	public static Global create() {
		Context cx = Context.enter();
		try {
			Global ret = (Global) cx.initStandardObjects(new Global(), true);

			int attrs = ScriptableObject.DONTENUM | ScriptableObject.PERMANENT
					| ScriptableObject.READONLY;

			ret.defineFunctionProperties(new String[] { "require" },
					Global.class, attrs);

			return ret;
		} finally {
			Context.exit();
		}
	}

}
