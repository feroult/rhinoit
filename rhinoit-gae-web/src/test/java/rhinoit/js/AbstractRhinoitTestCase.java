package rhinoit.js;

import java.net.URL;
import java.util.logging.LogManager;

import org.junit.Before;
import org.mozilla.javascript.ContextFactory;

public class AbstractRhinoitTestCase {

	protected Global global;

	@Before
	public void setUp() throws Exception {

		if (!ContextFactory.hasExplicitGlobal()) {
			MyContextFactory.init();
		}

		System.setProperty("java.util.logging.config.file",
				"src/test/resources/logging.properties");
		LogManager.getLogManager().readConfiguration();

		global = Global.create();

		global.addSpec(new LogConsole());
		global.addSpec(new MymathJSModule());

		URL url = getClass().getResource(getClass().getSimpleName() + ".js");
		global.load(url);

		// RhinoUtil.sourceClasspath(global, getClass(), getClass()
		// .getSimpleName() + ".js");
	}

}
