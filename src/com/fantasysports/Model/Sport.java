package com.fantasysports.Model;

public class Sport {
	private int id;
	private String name;
	private String imgURL;

	public Sport(String name, String imgURL) {
		this.name = name;
		this.imgURL = imgURL;
	}

	public Sport(int id, String name, String imgURL) {
		this.id = id;
		this.name = name;
		this.imgURL = imgURL;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getImgURL() {
		return imgURL;
	}

	public void setImgURL(String imgURL) {
		this.imgURL = imgURL;
	}

}
