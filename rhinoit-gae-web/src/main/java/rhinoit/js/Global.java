package rhinoit.js;

import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.Function;
import org.mozilla.javascript.FunctionObject;
import org.mozilla.javascript.IdScriptableObject;
import org.mozilla.javascript.Scriptable;
import org.mozilla.javascript.ScriptableObject;

public class Global extends IdScriptableObject {

	private static final long serialVersionUID = 1981033374348195776L;

	private Map<String, ScriptableObject> specs = new HashMap<String, ScriptableObject>();

	private Map<String, Scriptable> mods = new HashMap<String, Scriptable>();

	private Set<String> requireStack = new HashSet<String>();

	@Override
	public String getClassName() {
		return "Global";
	}

	public static Object require(Context cx, Scriptable thisObj, Object[] args,
			Function funObj) {
		String uri = (String) args[0];
		FunctionObject f = (FunctionObject) funObj;
		System.out.println("start: " + uri + " " + Context.toString(thisObj));
		Global global = (Global) ScriptableObject.getTopLevelScope(thisObj);
		Scriptable ret = global.module(thisObj, uri);
		System.out.println("end: " + uri + " " + Context.toString(thisObj));
		return ret;
	}

	private synchronized Scriptable module(Scriptable thisObj, String uri) {
		Scriptable exports = mods.get(uri);
		if (exports == null) {
			if (requireStack.contains(uri)) {
				throw new RuntimeException("it is cyclic: " + uri + " "
						+ requireStack);
			}
			requireStack.add(uri);
			exports = loadSpec(thisObj, uri);
			if (exports == null && uri.startsWith(".")) {
				exports = loadModule(thisObj, uri);
			}
			requireStack.remove(uri);
			if (exports == null) {
				throw new RuntimeException("module not found: " + uri);
			}
			mods.put(uri, exports);

		}
		return exports;
	}

	private Scriptable loadModule(Scriptable thisObj, String uri) {
		Context cx = Context.getCurrentContext();
		ModuleScope scope = (ModuleScope) cx.newObject(thisObj, "ModuleScope");
		scope.load(uri);
		return scope.getExports();
	}

	private Scriptable loadSpec(Scriptable thisObj, String uri) {
		try {
			ScriptableObject spec = specs.get(uri);
			if (spec == null) {
				return null;
			}
			Context cx = Context.getCurrentContext();
			Scriptable scope = cx.newObject(thisObj);
			ScriptableObject.defineClass(scope, spec.getClass());
			Scriptable ret = RhinoUtil.newScopeObject(scope,
					spec.getClassName());
			return ret;
		} catch (IllegalAccessException e) {
			throw new RuntimeException(e);
		} catch (InstantiationException e) {
			throw new RuntimeException(e);
		} catch (InvocationTargetException e) {
			throw new RuntimeException(e);
		}
	}

	public static Global create() {
		Context cx = Context.enter();
		try {
			Global ret = (Global) cx.initStandardObjects(new Global(), true);

			int attrs = ScriptableObject.DONTENUM | ScriptableObject.PERMANENT
					| ScriptableObject.READONLY;

			//ret.defineFunctionProperties(new String[] { "require" },
			//		Global.class, attrs);
			
			ret.defineProperty("require", new Require() {
				@Override
				public Object call(Context cx, Scriptable scope,
						Scriptable thisObj, Object[] args) {
					System.out.println("START: " + Context.toString(scope) + " " + Arrays.toString(args));
					Object ret = super.call(cx, scope, thisObj, args);
					System.out.println("END: " + Context.toString(scope) + " " + Arrays.toString(args));
					return ret;
				}
			}, DONTENUM);
			
			ScriptableObject.defineClass(ret, ModuleScope.class);

			return ret;
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

	public void addSpec(ScriptableObject spec) {
		specs.put(spec.getClassName(), spec);
	}

	public Object load(URL url) {
		Context cx = Context.enter();
		try {
			System.out.println("start: " + url + " " + Context.toString(this));
			ModuleScope scope = (ModuleScope) cx.newObject(this, "ModuleScope");
			Object ret = scope.load(url.toString());
			System.out.println("end: " + url + " " + Context.toString(this));
			return ret;
		} finally {
			Context.exit();
		}
	}

}
