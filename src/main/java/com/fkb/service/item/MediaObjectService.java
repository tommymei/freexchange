package com.fkb.service.item;

import java.util.List;

import com.fkb.model.item.MediaObject;
import com.google.appengine.api.datastore.Key;

public interface MediaObjectService {

	void saveMediaQbject(final MediaObject mediaObject);
	
	MediaObject getMediaObject(final Key key);
	
	void deleteMediaObject(Key key, String username);
	
	List<MediaObject> loadMediaObjectsByUsername(final String username);
	
}
