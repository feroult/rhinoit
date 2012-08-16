package voidrepo.store.servlet;

import java.net.MalformedURLException;
import java.net.URL;

public class CallInfo {

	private String database;
	private String user;
	private String kind;
	private String id;

	// http://mydb.myuser.voidrepo.com/kind/id
	public static CallInfo parse(String url) throws MalformedURLException {
		URL u = new URL(url);

		CallInfo ci = new CallInfo();

		String[] db_info = u.getHost().split("\\.");
		ci.setDatabase(db_info[0]);
		ci.setUser(db_info[1]);

		String[] kind_info = u.getFile().split("/");
		ci.setKind(kind_info[1]);
		if(kind_info.length > 2) {
			ci.setId(kind_info[2]);
		}

		return ci;
	}

	public String getDatabase() {
		return database;
	}

	public void setDatabase(String database) {
		this.database = database;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getKind() {
		return kind;
	}

	public void setKind(String kind) {
		this.kind = kind;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
}
