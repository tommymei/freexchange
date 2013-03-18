package com.fkb.controller;

import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.fkb.form.Item;
import com.fkb.model.item.MediaObject;
import com.fkb.model.item.PMF;
import com.google.appengine.api.blobstore.BlobInfo;
import com.google.appengine.api.blobstore.BlobInfoFactory;
import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;

/**
 * The item controller.
 */
@Controller
@RequestMapping("/item")
@SessionAttributes(ItemController.ITEM)
public class ItemController {

	/**
	 * The item form attribute name.
	 */
	protected static final String ITEM = "item";

	private BlobstoreService blobstoreService = BlobstoreServiceFactory
			.getBlobstoreService();

	/**
	 * Display the create item form.
	 * 
	 * @param model
	 *            the model map
	 * @return the view name
	 */
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public final String create(final ModelMap model) {
		model.addAttribute(ITEM, new Item());
		String uploadUrl = blobstoreService.createUploadUrl("/app/item/post");
		model.addAttribute("uploadUrl", uploadUrl);
		return "createItem";
	}

	/**
	 * Handle the create item form submission.
	 * 
	 * @param item
	 *            the item form bean
	 * @param binding
	 *            the binding result
	 * @param request
	 *            the HTTP servlet request
	 * @return the path
	 */
	@RequestMapping(value = "/post", method = RequestMethod.POST)
	public final String submit(@ModelAttribute(ITEM) @Valid final Item item,
			final BindingResult binding, final HttpServletRequest request) {

		if (binding.hasErrors()) {
			return "error";
		}

		// TODO: create a service for this so it can be swapped out
		String contentType = null;
		long size = 0;
		String fileName = "";
		Date creation = new Date();
		BlobKey blobKey = null;

		Map<String, List<BlobKey>> blobs = blobstoreService.getUploads(request);
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
		}

		try {
			MediaObject mediaObj = new MediaObject("tommy4", blobKey,
					contentType, fileName, size, item.getTitle(),
					item.getDescription(), item.getZipcode(), item.getType(),
					creation, creation);
			PMF.get().getPersistenceManager().makePersistent(mediaObj);
			return "redirect:/";
		} catch (Exception e) {
			blobstoreService.delete(blobKey);
			return "error";
		}

	}

}
