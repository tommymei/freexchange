/* Copyright (c) 2009 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.fkb.model.item;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.datastore.Key;

@PersistenceCapable(identityType = IdentityType.APPLICATION)
public class MediaObject {
	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private Key key;

	@Persistent
	private String username;

	@Persistent
	private BlobKey blob;

	@Persistent
	private String contentType;

	@Persistent
	private String filename;

	@Persistent
	private long size;

	@Persistent
	private String title;

	@Persistent
	private String description;

	@Persistent
	private int zipcode;

	@Persistent
	private String type;

	@Persistent
	private Date creation;

	@Persistent
	private Date modification;

	private static final List<String> IMAGE_TYPES = Arrays.asList("image/png",
			"image/jpeg", "image/tiff", "image/gif", "image/bmp");

	public MediaObject(String username, BlobKey blob, String contentType,
			String filename, long size, String title, String description,
			int zipcode, String type, Date creationTime, Date modificationTime) {

		this.username = username;
		this.blob = blob;
		this.contentType = contentType;
		this.filename = filename;
		this.size = size;
		this.title = title;
		this.description = description;
		this.zipcode = zipcode;
		this.type = type;
		this.creation = creationTime;
		this.modification = modificationTime;

	}

	public Key getKey() {
		return key;
	}

	

	public String getDescription() {
		return description;
	}

	public String getTitle() {
		return title;
	}

	public String getFilename() {
		return filename;
	}

	public long getSize() {
		return size;
	}

	public String getUsername() {
		return username;
	}

	public int getZipcode() {
		return zipcode;
	}

	public String getType() {
		return type;
	}

	public Date getCreation() {
		return creation;
	}
	
	public Date getModification() {
		return modification;
	}

	public static List<String> getImageTypes() {
		return IMAGE_TYPES;
	}

	public String getContentType() {
		if (contentType == null) {
			return "text/plain";
		}
		return contentType;
	}

	public String getURLPath() {
		String key = blob.getKeyString();
		return "/resource?key=" + key;
	}

	public String getDisplayURL() {
		String key = blob.getKeyString();
		return "/display?key=" + key;
	}

	public boolean isImage() {
		return IMAGE_TYPES.contains(getContentType());
	}
}
