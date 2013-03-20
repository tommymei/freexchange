package com.fkb.controller;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.fkb.controller.util.BlobInfoUtil;
import com.fkb.form.Item;
import com.fkb.model.item.MediaObject;
import com.fkb.service.item.MediaObjectService;
import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;
import com.google.appengine.api.datastore.KeyFactory;

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

	@Autowired
	private MediaObjectService mediaObjectService;

	/**
	 * Display the create item form.
	 * 
	 * @param model
	 *            the model map
	 * @return the view name
	 */
	@RequestMapping(value = "/post", method = RequestMethod.GET)
	public final String displayCreateItemForm(final ModelMap model) {
		model.addAttribute(ITEM, new Item());
		String uploadUrl = blobstoreService.createUploadUrl("/app/item/post");
		model.addAttribute("uploadUrl", uploadUrl);
		return "postItem";
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
	public final String submitCreateItemForm(
			@ModelAttribute(ITEM) @Valid final Item item,
			final BindingResult binding, final HttpServletRequest request) {

		Authentication authentication = SecurityContextHolder.getContext()
				.getAuthentication();
		String username = ((UserDetails) authentication.getPrincipal())
				.getUsername();

		if (binding.hasErrors()) {
			return "error";
		}

		Map<String, List<BlobKey>> blobs = blobstoreService.getUploads(request);
		BlobInfoUtil bi = new BlobInfoUtil(blobs);

		try {
			Date now = new Date();
			MediaObject mediaObj = new MediaObject(username, bi.getBlobKey(),
					bi.getContentType(), bi.getFileName(), bi.getSize(),
					item.getTitle(), item.getDescription(), item.getZipcode(),
					item.getType(), now, now);
			this.mediaObjectService.saveMediaQbject(mediaObj);
			return "redirect:/item/list/" + username;
		} catch (Exception e) {
			if (bi.getBlobKey() != null) {
				blobstoreService.delete(bi.getBlobKey());
			}
			return "error";
		}
	}

	/**
	 * List items posted by the user
	 * 
	 * @param model
	 *            the model map
	 * @return the path
	 */
	@RequestMapping(value = "/list/{username}", method = RequestMethod.GET)
	public final String listItems(@PathVariable("username") String username,
			final ModelMap model) {
		model.addAttribute("itemList",
				this.mediaObjectService.loadMediaObjectsByUsername(username));
		model.addAttribute("editable", true);
		return "listItems";
	}

	/**
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/listall", method = RequestMethod.GET)
	public final String listAllItems(final ModelMap model) {
		model.addAttribute("itemList",
				this.mediaObjectService.loadMediaObjectsByUsername(""));
		model.addAttribute("editable", false);
		return "listItems";
	}

	/**
	 * 
	 * @param username
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/delete/{websafeKey}", method = RequestMethod.GET)
	public final String delete(@PathVariable("websafeKey") String websafeKey) {
		Authentication authentication = SecurityContextHolder.getContext()
				.getAuthentication();
		String username = ((UserDetails) authentication.getPrincipal())
				.getUsername();

		this.mediaObjectService.deleteMediaObject(
				KeyFactory.stringToKey(websafeKey), username);
		return "redirect:/item/list/" + username;
	}

}
