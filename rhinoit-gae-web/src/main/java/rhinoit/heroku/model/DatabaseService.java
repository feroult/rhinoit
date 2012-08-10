package rhinoit.heroku.model;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.Key;
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

	public void destroy(Long id) {
		DatastoreService s = getDatastore();
		s.delete(createKey(id));
	}

	public void update(Long id, String... param) {
		Entity e = get(id);
		for(int i = 0; i < param.length; i += 2) {
			e.setUnindexedProperty(param[i], param[i+1]);
		}
	}

	public Entity get(Long id) {
		try {
			DatastoreService s = DatastoreServiceFactory.getDatastoreService();
			Entity ret = s.get(createKey(id));
			return ret;
		} catch (EntityNotFoundException e) {
			return null;
		}
	}

	private void put(Entity entity) {
		DatastoreService ds = getDatastore();
		ds.put(entity);
	}

	private Key createKey(Long id) {
		return KeyFactory.createKey("Database", id);
	}
}
