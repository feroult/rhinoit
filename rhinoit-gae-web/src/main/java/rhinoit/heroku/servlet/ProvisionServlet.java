package rhinoit.heroku.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import rhinoit.heroku.model.DatabaseService;

import com.google.appengine.api.datastore.Entity;
import com.google.gson.JsonObject;
import com.googlecode.mycontainer.commons.servlet.ServletUtil;

public class ProvisionServlet extends HttpServlet {

	private static final long serialVersionUID = 7446302940798909745L;

	private static final String RESTITORY_URL = "http://localhost:8080/restitory";

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
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
