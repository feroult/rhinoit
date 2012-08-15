package voidrepo.servlet;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;

import com.google.appengine.api.memcache.MemcacheServiceFactory;
import com.googlecode.mycontainer.commons.http.HttpClientRequestService;
import com.googlecode.mycontainer.commons.http.RequestAdapter;
import com.googlecode.mycontainer.commons.http.RequestService;

public class AbstractTestCase {

	protected GAETestHelper helper;

	protected RequestService s;

	protected RequestAdapter a;

	@Before
	public void setUp() throws Exception {
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

	public static long now() {
		try {
			Thread.sleep(2l);
			long ret = System.currentTimeMillis();
			Thread.sleep(2l);
			return ret;
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
	}

	public static void assertBeteween(long min, long max, long check) {
		if (min > max) {
			throw new RuntimeException("crazy test: " + min + " " + max);
		}
		if (check <= min) {
			Assert.fail("it should be greater [" + min + " - " + max
					+ "], but was: " + check);
		}
	}

	public static String gen(String str, int maxSizeBody) {
		StringBuilder ret = new StringBuilder();
		for (int i = 0; i < maxSizeBody; i++) {
			ret.append(str);
		}
		return ret.toString();
	}

}
