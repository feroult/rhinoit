package rhinoit.js;

import java.net.MalformedURLException;
import java.net.URL;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.Scriptable;
import org.mozilla.javascript.ScriptableObject;

public class ModuleScope extends ScriptableObject {

	private static final long serialVersionUID = 6824000248361506662L;

	private Scriptable exports;

	private String uri;

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

	public Object load(String uri) {
		try {
			this.uri = uri;
			return RhinoUtil.source(this, new URL(uri));
		} catch (MalformedURLException e) {
			throw new RuntimeException(e);
		}

	}

	public Scriptable jsFunction_require(String uri) {
		VoidRepoGlobal global = (VoidRepoGlobal) ScriptableObject
				.getTopLevelScope(this);
		return global.module(this, uri);
	}

	@Override
	public String toString() {
		return "ModuleScope [uri=" + uri + "]";
	}

	public Scriptable loadSub(String uri) {
		Context cx = Context.getCurrentContext();
		String path = getPath() + "/" + uri;
		ModuleScope scope = (ModuleScope) cx.newObject(this, "ModuleScope");
		scope.load(path);
		return scope.getExports();
	}

	private String getPath() {
		String ret = this.uri;
		int idx = ret.lastIndexOf("/");
		if (idx < 0) {
			throw new RuntimeException("should be a path: " + this.uri);
		}
		return this.uri.substring(0, idx);
	}

}
