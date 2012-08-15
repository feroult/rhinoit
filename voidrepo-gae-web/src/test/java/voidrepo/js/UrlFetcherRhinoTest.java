package voidrepo.js;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class UrlFetcherRhinoTest extends AbstractVoidrepoRhinoTestCase {

	@Test
	public void testFetcher() {
		assertEquals("my test",
				RhinoUtil.eval(global, "testFetcher();", "<test>"));
	}

}
