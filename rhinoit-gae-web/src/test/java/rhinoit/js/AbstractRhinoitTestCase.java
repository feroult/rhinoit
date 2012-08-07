package rhinoit.js;

import java.io.IOException;
import java.util.logging.LogManager;

import org.junit.Before;

public class AbstractRhinoitTestCase {

	protected Global global;

	@Before
	public void setUp() throws Exception {
		

		System.setProperty("java.util.logging.config.file", "\\Users\\murer\\proj\\GitHub\\rhinoit\\rhinoit-gae-web\\logging.properties");
		LogManager.getLogManager().readConfiguration();

		global = Global.create();

		global.addSpec(new LogConsole());
		global.addSpec(new MymathJSModule());

		RhinoUtil.sourceClasspath(global, getClass(), getClass()
				.getSimpleName() + ".js");
	}

}
