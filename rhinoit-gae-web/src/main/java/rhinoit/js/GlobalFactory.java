package rhinoit.js;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.Scriptable;

import rhinoit.js.Global;
import rhinoit.js.LogConsole;

public class GlobalFactory {

	private static final Object MUTEX = new Object();

	private static Global global = null;

	public static Global global() {
		if (global == null) {
			synchronized (MUTEX) {
				if (global == null) {
					Global g = Global.create();
					g.addSpec(new LogConsole());
					global = g;
				}
			}
		}
		return global;
	}

	public static Scriptable createScope() {
		Global global = global();
		Scriptable ret = Context.getCurrentContext().newObject(global);
		return ret;
	}

}