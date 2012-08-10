package rhinoit.js;

import static org.junit.Assert.assertEquals;

import org.junit.Ignore;
import org.junit.Test;

public class RhinoitTest extends AbstractRhinoitTestCase {

	@Test
	@Ignore
	public void testRhinoit() {
		assertEquals("ok", RhinoUtil.eval(global, "testRhinoit();", "<test>"));
	}

	@Test
	@Ignore
	public void testModules() {
		assertEquals(2.0, RhinoUtil.eval(global, "testModules();", "<test>"));
	}

}
