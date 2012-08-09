package rhinoit.heroku.model;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.KeyFactory;

public class DatabaseService {

	public static DatabaseService getDatabaseService() {
		return new DatabaseService();
	}

	private DatastoreService getDatastore() {
		return DatastoreServiceFactory.getDatastoreService();
	}

	public Entity create() {
		Entity ret = new Entity("Database");
		long now = System.currentTimeMillis();
		ret.setProperty("created", now);
		put(ret);
		return ret;
	}

	public Entity get(Long id) {
		try {
			DatastoreService s = DatastoreServiceFactory.getDatastoreService();
			Entity ret = s.get(KeyFactory.createKey("Scrap", id));
			return ret;
		} catch (EntityNotFoundException e) {
			return null;
		}
	}

	private void put(Entity entity) {
		DatastoreService ds = getDatastore();
		ds.put(entity);
	}
}
