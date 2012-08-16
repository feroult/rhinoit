package voidrepo.store.model;

import voidrepo.heroku.model.DatabaseService;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;

public class StoreService {

	public static DatabaseService getDatabaseService() {
		return new DatabaseService();
	}

	private DatastoreService getDatastore() {
		return DatastoreServiceFactory.getDatastoreService();
	}



}
