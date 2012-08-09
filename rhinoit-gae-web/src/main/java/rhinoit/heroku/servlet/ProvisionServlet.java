package rhinoit.heroku.servlet;

import java.io.IOException;
import java.io.PrintWriter;

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


	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		DatabaseService s = DatabaseService.getDatabaseService();
		Entity e = s.create();

		long id = e.getKey().getId();

		JsonObject ret = new JsonObject();
		ret.addProperty("id", id);

		JsonObject config = new JsonObject();
		config.addProperty("RESTITORY_URL", "/" + id);
		ret.add("config", config);

		ServletUtil.write(resp, ret);
	}


	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		resp.setContentType("text/plain");
		resp.setCharacterEncoding("UTF-8");

		PrintWriter out = resp.getWriter();

	}

}
