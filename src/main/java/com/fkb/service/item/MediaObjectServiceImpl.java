package com.fkb.service.item;

import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import org.datanucleus.util.StringUtils;

import com.fkb.model.item.MediaObject;
import com.fkb.model.item.PMF;
import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;

public class MediaObjectServiceImpl implements MediaObjectService {

	private BlobstoreService blobstoreService = BlobstoreServiceFactory
			.getBlobstoreService();

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.fkb.service.item.MediaObjectService#saveMediaQbject(com.fkb.model
	 * .item.MediaObject)
	 */
	@Override
	public void saveMediaQbject(MediaObject mediaObject) {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		try {
			pm.makePersistent(mediaObject);
		} finally {
			pm.close();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.fkb.service.item.MediaObjectService#getMediaObject(java.lang.String)
	 */
	@Override
	public MediaObject getMediaObject(Key key) {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		try {
			Query q = pm.newQuery(MediaObject.class, "key == keyParam");
			q.declareImports("import "
					+ "com.google.appengine.api.datastore.Key");
			q.declareParameters("Key keyParam");
			q.setUnique(true);
			return (MediaObject) q.execute(key);
		} finally {
			pm.close();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.fkb.service.item.MediaObjectService#loadMediaObjectsByUsername(java
	 * .lang.String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<MediaObject> loadMediaObjectsByUsername(String username) {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		List<MediaObject> results;
		try {
			Query query = pm.newQuery(MediaObject.class);
			query.setRange(0, 500);
			query.setOrdering("modification desc");

			if (!StringUtils.isEmpty(username)) {
				query.setFilter("username == userParam");
				query.declareParameters("String userParam");
				results = (List<MediaObject>) query.execute(username);
			} else {
				results = (List<MediaObject>) query.execute();
			}
			return (List<MediaObject>) pm.detachCopyAll(results);
		} finally {
			pm.close();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.fkb.service.item.MediaObjectService#deleteMediaObject(com.fkb.model
	 * .item.MediaObject)
	 */
	@Override
	public void deleteMediaObject(Key key, String username) {
		MediaObject mediaObject = this.getMediaObject(key);

		if (mediaObject == null) {
			throw new RuntimeException("Cannot find object for key : "
					+ KeyFactory.keyToString(key));
		}

		// check if user can delete
		if (!mediaObject.getUsername().equals(username)) {
			throw new RuntimeException("User can not delete for key : "
					+ KeyFactory.keyToString(key));
		}

		PersistenceManager pm = PMF.get().getPersistenceManager();
		try {
			if (mediaObject.getBlob() != null) {
				blobstoreService.delete(mediaObject.getBlob());
			}
			Query q = pm.newQuery(MediaObject.class, "key == keyParam");
			q.declareImports("import "
					+ "com.google.appengine.api.datastore.Key");
			q.declareParameters("Key keyParam");
			q.deletePersistentAll(key);
		} finally {
			pm.close();
		}
	}

}
