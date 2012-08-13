package rhinoit.js;

import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.IdScriptableObject;
import org.mozilla.javascript.Scriptable;
import org.mozilla.javascript.ScriptableObject;

public class VoidRepoGlobal extends IdScriptableObject {

	private static final long serialVersionUID = -721175855611524942L;

	private Map<String, ScriptableObject> specs = new HashMap<String, ScriptableObject>();

	private Map<String, Scriptable> mods = new HashMap<String, Scriptable>();

	private Set<String> requireStack = new HashSet<String>();

	@Override
	public String getClassName() {
		return "VoidRepoGlobal";
	}

	public synchronized Scriptable module(Scriptable thisObj, String uri) {
		Scriptable exports = mods.get(uri);
		if (exports == null) {
			if (requireStack.contains(uri)) {
				throw new RuntimeException("it is cyclic: " + uri + " "
						+ requireStack);
			}
			requireStack.add(uri);
			exports = loadSpec(thisObj, uri);
			if (exports == null && uri.startsWith(".")) {
				exports = ((ModuleScope) thisObj).loadSub(uri + ".js");
			}
			requireStack.remove(uri);
			if (exports == null) {
				throw new RuntimeException("module not found: " + uri);
			}
			mods.put(uri, exports);

		}
		return exports;
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

	public void addSpec(ScriptableObject spec) {
		specs.put(spec.getClassName(), spec);
	}

	public static VoidRepoGlobal create() {
		Context cx = Context.enter();
		try {
			VoidRepoGlobal ret = (VoidRepoGlobal) cx.initStandardObjects(
					new VoidRepoGlobal(), true);

			// int attrs = ScriptableObject.DONTENUM |
			// ScriptableObject.PERMANENT
			// | ScriptableObject.READONLY;

			// ret.defineFunctionProperties(new String[] { "require" },
			// Global.class, attrs);

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

	public Object load(URL url) {
		Context cx = Context.enter();
		try {
			ModuleScope scope = (ModuleScope) cx.newObject(this, "ModuleScope");
			Object ret = scope.load(url.toString());
			return ret;
		} finally {
			Context.exit();
		}
	}

}
