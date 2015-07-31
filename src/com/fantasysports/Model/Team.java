package com.fantasysports.Model;

public class Team {
	private int id;
	private int sportId;
	private String name;
	private String imgURL;

	public Team(int sportId, String name, String imgURL) {	
		this.sportId = sportId;
		this.name = name;
		this.imgURL = imgURL;
	}

	public Team(int id, int sportId, String name, String imgURL) {
		this.id = id;
		this.sportId = sportId;
		this.name = name;
		this.imgURL = imgURL;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getSportId() {
		return sportId;
	}

	public void setSportId(int sportId) {
		this.sportId = sportId;
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
