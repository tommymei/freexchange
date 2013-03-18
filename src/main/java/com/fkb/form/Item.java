package com.fkb.form;

import java.io.Serializable;

import javax.validation.constraints.Pattern;

import org.springframework.web.multipart.commons.CommonsMultipartFile;

/**
 * The registration form bean.
 */
@SuppressWarnings("serial")
public class Item implements Serializable {

	/**
	 * title
	 */
	@Pattern(regexp = ".+")
	private String title;

	/**
	 * description
	 */
	@Pattern(regexp = ".+")
	private String description;

	private int zipcode;

	private String type;

	private CommonsMultipartFile fileData;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getZipcode() {
		return zipcode;
	}

	public void setZipcode(int zipcode) {
		this.zipcode = zipcode;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public CommonsMultipartFile getFileData() {
		return fileData;
	}

	public void setFileData(CommonsMultipartFile fileData) {
		this.fileData = fileData;
	}

}
