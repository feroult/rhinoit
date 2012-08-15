package voidrepo.js;

import java.net.URL;
import java.util.logging.LogManager;

import org.junit.Before;
import org.mozilla.javascript.ContextFactory;

import voidrepo.js.LogConsole;
import voidrepo.js.MyContextFactory;
import voidrepo.js.VoidRepoGlobal;

public class AbstractVoidrepoRhinoTestCase {

	protected VoidRepoGlobal global;

	@Before
	public void setUp() throws Exception {

		if (!ContextFactory.hasExplicitGlobal()) {
			MyContextFactory.init();
		}

		System.setProperty("java.util.logging.config.file",
				"src/test/resources/logging.properties");
		LogManager.getLogManager().readConfiguration();

		global = VoidRepoGlobal.create();

		global.addSpec(new LogConsole());
		global.addSpec(new MymathJSModule());

		URL url = getClass().getResource(getClass().getSimpleName() + ".js");
		global.load(url);

		// RhinoUtil.sourceClasspath(global, getClass(), getClass()
		// .getSimpleName() + ".js");
	}

}
