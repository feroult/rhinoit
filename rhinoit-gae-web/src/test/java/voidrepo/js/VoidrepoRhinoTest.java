package voidrepo.js;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class VoidrepoRhinoTest extends AbstractVoidrepoRhinoTestCase {

	@Test
	public void testRhino() {
		assertEquals("ok", RhinoUtil.eval(global, "testRhino();", "<test>"));
	}

	@Test
	public void testModules() {
		assertEquals(2.0, RhinoUtil.eval(global, "testModules();", "<test>"));
	}

}
