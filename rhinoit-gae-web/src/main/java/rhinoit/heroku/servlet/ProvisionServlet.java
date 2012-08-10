package rhinoit.heroku.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import rhinoit.heroku.model.DatabaseService;

import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Text;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.googlecode.mycontainer.commons.servlet.ServletUtil;
import com.googlecode.mycontainer.commons.util.JsonUtil;

public class ProvisionServlet extends HttpServlet {

	private static final long serialVersionUID = 7446302940798909745L;

	private static final String RESTITORY_URL = "http://localhost:8080/restitory";

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		// Montando json
		JsonObject obj = new JsonObject();
		
		obj.addProperty("name", "xxx"); 
		obj.add("x", new JsonPrimitive("x")); 
		
		obj.add("x", new JsonObject());
		obj.add("x", new JsonArray());

		// heranca: JsonElement is super class for JsonObject, JsonArray, JsonPrimitive, JsonNull
		// JsonPrimitive is used for String, number, boolean.
		
		// Parse json
		JsonElement json = JsonUtil.parse("{}");
		// json format
		json.toString();
		
		// read json from the request
		json = ServletUtil.readJson(req);
		
		// Write json to response
		ServletUtil.write(resp, json);
		
		// gravar no gae usando "Text"
		Entity x = new Entity("xxx");
		x.setUnindexedProperty("doc", new Text(json.toString()));
		
		// setUnindexedProperty over setProperty :)
		
		
		DatabaseService s = DatabaseService.getDatabaseService();
		Entity e = s.create();

		long id = e.getKey().getId();

		JsonObject ret = new JsonObject();
		ret.addProperty("id", id);

		JsonObject config = new JsonObject();
		config.addProperty("RESTITORY_URL", RESTITORY_URL);
		ret.add("config", config);

		ServletUtil.write(resp, ret);
	}

	@Override
	protected void doDelete(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		DatabaseService s = DatabaseService.getDatabaseService();
		s.destroy(getResourceId(req));
	}

	@Override
	protected void doPut(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		JsonObject obj = ServletUtil.readJson(req).getAsJsonObject();
		DatabaseService s = DatabaseService.getDatabaseService();
		s.update(getResourceId(req), "plan", obj.get("plan").getAsString());
	}

	private Long getResourceId(HttpServletRequest req) {
		String uri = req.getRequestURI();
		String[] split = uri.split("/");
		return new Long(split[split.length - 1]);
	}

}
