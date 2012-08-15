package voidrepo.restitory.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.JsonObject;
import com.googlecode.mycontainer.commons.servlet.ServletUtil;

public class RestitoryServlet extends HttpServlet {

	private static final long serialVersionUID = -6453129665137506165L;

	@Override
	protected void doPut(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {


		JsonObject obj = new JsonObject();
	    obj.addProperty("nome", "fernando");

	    ServletUtil.write(resp, obj);

	}


}
