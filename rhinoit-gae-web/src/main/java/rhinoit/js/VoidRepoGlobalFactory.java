package rhinoit.js;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.Scriptable;

public class VoidRepoGlobalFactory {

	private static final Object MUTEX = new Object();

	private static VoidRepoGlobal global = null;

	public static VoidRepoGlobal global() {
		if (global == null) {
			synchronized (MUTEX) {
				if (global == null) {
					VoidRepoGlobal g = VoidRepoGlobal.create();
					g.addSpec(new LogConsole());
					global = g;
				}
			}
		}
		return global;
	}

	public static Scriptable createScope() {
		VoidRepoGlobal global = global();
		Scriptable ret = Context.getCurrentContext().newObject(global);
		return ret;
	}

}