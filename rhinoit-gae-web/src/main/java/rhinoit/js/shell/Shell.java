package rhinoit.js.shell;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.tools.shell.Main;

import rhinoit.js.GlobalFactory;

public class Shell {

	public static void main(String[] args) {
		Context cx = Context.enter();
		try {
			Main.global.init(cx);
			Main.global.setParentScope(GlobalFactory.global());
			Main.main(args);
		} finally {
			Context.exit();
		}
	}
}