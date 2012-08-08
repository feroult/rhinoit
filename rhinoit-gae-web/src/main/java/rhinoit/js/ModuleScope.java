package rhinoit.js;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.Scriptable;
import org.mozilla.javascript.ScriptableObject;

public class ModuleScope extends ScriptableObject {

	private static final long serialVersionUID = 6824000248361506662L;

	private Scriptable exports;

	public void jsConstructor(int n) {
		exports = Context.getCurrentContext().newObject(this);
	}

	@Override
	public String getClassName() {
		return "ModuleScope";
	}

	public Scriptable jsGet_exports() {
		return exports;
	}

	public Scriptable getExports() {
		return exports;
	}

	public void load(String uri) {
		RhinoUtil.sourceClasspath(this, ModuleScope.class, uri + ".js");
	}

}
