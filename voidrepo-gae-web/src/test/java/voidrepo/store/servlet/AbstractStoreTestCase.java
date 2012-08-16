package voidrepo.store.servlet;

import org.junit.After;
import org.junit.Before;

import voidrepo.servlet.GAETestHelper;

import com.google.appengine.api.memcache.MemcacheServiceFactory;
import com.googlecode.mycontainer.commons.http.HttpClientRequestService;
import com.googlecode.mycontainer.commons.http.RequestAdapter;
import com.googlecode.mycontainer.commons.http.RequestService;

public class AbstractStoreTestCase {

	protected GAETestHelper helper;

	protected RequestService s;

	protected RequestAdapter a;

	@Before
	public void setUp() throws Exception {
		helper = new GAETestHelper();
		helper.prepareLocalServiceTestHelper();
		helper.bootMycontainer();

		s = new HttpClientRequestService("http://test.jow.voidrepo:8580");
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
