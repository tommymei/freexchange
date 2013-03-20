package com.fkb.controller.util;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.google.appengine.api.blobstore.BlobInfo;
import com.google.appengine.api.blobstore.BlobInfoFactory;
import com.google.appengine.api.blobstore.BlobKey;

/**
 * Util to extract info from the first blob
 * 
 * @author tommymei
 * 
 */
public class BlobInfoUtil {

	final String contentType;
	final long size;
	final String fileName;
	final BlobKey blobKey;

	public BlobInfoUtil(Map<String, List<BlobKey>> blobs) {
		if (!blobs.keySet().isEmpty()) {
			Iterator<String> names = blobs.keySet().iterator();
			String blobName = names.next();
			List<BlobKey> blobKeys = blobs.get(blobName);
			blobKey = blobKeys.get(0);

			BlobInfoFactory blobInfoFactory = new BlobInfoFactory();
			BlobInfo blobInfo = blobInfoFactory.loadBlobInfo(blobKey);

			contentType = blobInfo.getContentType();
			size = blobInfo.getSize();
			fileName = blobInfo.getFilename();
		} else {
			fileName = null;
			contentType = null;
			size = 0;
			blobKey = null;
		}
	}

	public String getContentType() {
		return contentType;
	}

	public long getSize() {
		return size;
	}

	public String getFileName() {
		return fileName;
	}

	public BlobKey getBlobKey() {
		return blobKey;
	}
	
	
}
