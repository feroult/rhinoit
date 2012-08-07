package rhinoit.js;

import java.util.logging.LogManager;

import org.junit.Before;

public class AbstractRhinoitTestCase {

	protected Global global;

	@Before
	public void setUp() throws Exception {

		System.setProperty("java.util.logging.config.file",
				"src/test/resources/logging.properties");
		LogManager.getLogManager().readConfiguration();

		global = Global.create();

		global.addSpec(new LogConsole());
		global.addSpec(new MymathJSModule());

		RhinoUtil.sourceClasspath(global, getClass(), getClass()
				.getSimpleName() + ".js");
	}

}
