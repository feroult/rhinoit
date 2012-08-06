package rhinoit.js;

import org.junit.Before;


public class AbstractRhinoitTestCase {

	protected Global global;

	@Before
	public void setUp() {
		global = Global.create();

		global.addSpec(new MymathJSModule());
		
		RhinoUtil.sourceClasspath(global, getClass(), getClass()
				.getSimpleName() + ".js");
	}

}
