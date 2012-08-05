package rhinoit.js;

import org.junit.Before;

import rhinoit.servlet.RhinoUtil;

public class AbstractRhinoitTestCase {

	protected Global global;

	@Before
	public void setUp() {
		global = Global.create();
		RhinoUtil.sourceClasspath(global, getClass(), getClass()
				.getSimpleName() + ".js");
	}

}
