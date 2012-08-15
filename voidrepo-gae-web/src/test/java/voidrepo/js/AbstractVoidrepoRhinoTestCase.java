package voidrepo.js;

import java.net.URL;
import java.util.logging.LogManager;

import org.junit.After;
import org.junit.Before;
import org.mozilla.javascript.ContextFactory;

import voidrepo.js.gae.UrlFetcherHostObject;
import voidrepo.servlet.GAETestHelper;

import com.google.appengine.api.memcache.MemcacheServiceFactory;
import com.googlecode.mycontainer.commons.http.HttpClientRequestService;
import com.googlecode.mycontainer.commons.http.RequestAdapter;

public class AbstractVoidrepoRhinoTestCase {

	protected VoidRepoGlobal global;
	protected GAETestHelper helper;
	protected HttpClientRequestService s;
	protected RequestAdapter a;

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
		global.addSpec(new UrlFetcherHostObject());

		URL url = getClass().getResource(getClass().getSimpleName() + ".js");
		global.load(url);

		// RhinoUtil.sourceClasspath(global, getClass(), getClass()
		// .getSimpleName() + ".js");

		helper = new GAETestHelper();
		helper.prepareLocalServiceTestHelper();
		helper.bootMycontainer();

		s = new HttpClientRequestService("http://localhost:8580");
		a = new RequestAdapter(s);

		MemcacheServiceFactory.getMemcacheService().clearAll();
	}

	@After
	public void tearDown() {
		if (helper != null) {
			helper.shutdownMycontainer();
		}
	}

}
