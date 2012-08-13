package rhinoit.restitory.servlet;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import rhinoit.servlet.AbstractTestCase;

import com.google.gson.JsonObject;
import com.googlecode.mycontainer.commons.http.Request;

public class RestitoryBasicTest extends AbstractTestCase {

	@Test
	public void testCreate() {
		put("/pessoas/1", "{nome:fernando}");
		JsonObject e = get("/pessoas/1");

		assertEquals("fernando", e.get("nome").getAsString());
	}

	private void put(String url, String json) {
		Request.create("PUT", url).contentJson(json).exec(s);
	}

	private JsonObject get(String url) {
		return Request.create("GET", url).jsonObject(s);
	}

}
