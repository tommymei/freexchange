package com.fkb.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.jdo.PersistenceManager;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.fkb.model.item.MediaObject;
import com.fkb.model.item.PMF;
import com.fkb.service.item.MediaObjectService;
import com.fkb.service.item.MediaObjectServiceImpl;
import com.google.appengine.tools.development.testing.LocalBlobstoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;

public class MediaObjectServiceImplTest {

	private final LocalServiceTestHelper helper = new LocalServiceTestHelper(
			new LocalDatastoreServiceTestConfig(),
			new LocalBlobstoreServiceTestConfig());

	MediaObjectService classUnderTest = new MediaObjectServiceImpl();

	@Before
	public void setUp() {
		helper.setUp();
	}

	@After
	public void tearDown() {
		helper.tearDown();
	}

	@Test
	public void testInsertFullConstructor() {
		MediaObject mediaObject = new MediaObject("username", null,
				"image/jpeg", "test.jpeg", 1000, "title", "description", 10010,
				"offer", new Date(), new Date());
		classUnderTest.saveMediaQbject(mediaObject);
		Assert.assertNotNull(mediaObject.getKey());
	}

	@Test
	public void testInsertRequiredOnly() {
		MediaObject mediaObject = new MediaObject("username", "title",
				"description", 10010, "offer", new Date(), new Date());
		classUnderTest.saveMediaQbject(mediaObject);
		Assert.assertNotNull(mediaObject.getKey());
	}

	@Test
	public void testListByUsername() {
		MediaObject mediaObject = new MediaObject("username", "title",
				"description", 10010, "offer", new Date(), new Date());
		classUnderTest.saveMediaQbject(mediaObject);

		// assert 1
		List<MediaObject> results = classUnderTest
				.loadMediaObjectsByUsername("username");
		Assert.assertEquals(1, results.size());
		Assert.assertEquals("title", results.get(0).getTitle());

		// assert 0
		results = classUnderTest.loadMediaObjectsByUsername("usernameXXX");
		Assert.assertEquals(0, results.size());

		// assert 2
		mediaObject = new MediaObject("username", "title2", "description",
				10010, "offer", new Date(), new Date());
		classUnderTest.saveMediaQbject(mediaObject);
		results = classUnderTest.loadMediaObjectsByUsername("username");
		Assert.assertEquals(2, results.size());
		Assert.assertEquals("title2", results.get(0).getTitle());

	}

	@Test
	public void testListByUsernameMax() {
		List<MediaObject> con = new ArrayList<MediaObject>();
		// assert max 500
		for (int i = 0; i < 1001; i++) {
			MediaObject mediaObject = new MediaObject("username", "title",
					"description", 10010, "offer", new Date(), new Date());
			con.add(mediaObject);
		}

		PersistenceManager pm = PMF.get().getPersistenceManager();
		try {
			pm.makePersistentAll(con);
		} finally {
			pm.close();
		}

		List<MediaObject> results = classUnderTest
				.loadMediaObjectsByUsername("username");
		Assert.assertEquals(500, results.size());
	}

	@Test
	public void testDelete() {
		MediaObject mediaObject = new MediaObject("username", null,
				"image/jpeg", "test.jpeg", 1000, "title", "description", 10010,
				"offer", new Date(), new Date());
		classUnderTest.saveMediaQbject(mediaObject);
		
		List<MediaObject> results = classUnderTest
				.loadMediaObjectsByUsername("username");
		Assert.assertEquals(1, results.size());
		
		try {
			classUnderTest.deleteMediaObject(results.get(0).getKey(),
					"notowner");
			Assert.fail("failed owner check");
		} catch (Exception e) {
			
		}
		results = classUnderTest
				.loadMediaObjectsByUsername("username");
		Assert.assertEquals(1, results.size());
		
		classUnderTest.deleteMediaObject(results.get(0).getKey(), "username");
		results = classUnderTest
				.loadMediaObjectsByUsername("username");
		Assert.assertEquals(0, results.size());
	}

}
