package rhinoit.js;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import rhinoit.servlet.RhinoUtil;

public class RhinoitTest extends AbstractRhinoitTestCase {

	@Test
	public void testRhinoit() {
		assertEquals("ok", RhinoUtil.eval(global, "testRhinoit();", "<test>"));
	}

	@Test
	public void testModules() {
		assertEquals(2.0, RhinoUtil.eval(global, "testModules();", "<test>"));
	}

}
