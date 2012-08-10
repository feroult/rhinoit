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

	@Override
	public String toString() {
		return "ModuleScope [uri=" + uri + "]";
	}

}
