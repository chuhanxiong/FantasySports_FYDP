package com.fantasysports.Model;

import java.util.ArrayList;

public class User {
	private int id;
	private int score;
	private ArrayList<Integer> playerList;
	private String name;
	private String password;
	private String facebook;
	private String imgURL;

	public User() {
		this.playerList = new ArrayList<Integer>();
	}

	public User(String name, String password) {
		this.name = name;
		this.password = password;
		this.playerList = new ArrayList<Integer>();
	}

	public User(String name, String password, String facebook) {
		this.name = name;
		this.password = password;
		this.facebook = facebook;
		this.playerList = new ArrayList<Integer>();
	}

	public User(int id, int score, ArrayList<Integer> playerList, String name,
			String password, String facebook, String imgURL) {
		this.id = id;
		this.score = score;
		this.playerList = playerList;
		this.name = name;
		this.password = password;
		this.facebook = facebook;
		this.imgURL = imgURL;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public ArrayList<Integer> getPlayerList() {
		return playerList;
	}

	public void setPlayerList(ArrayList<Integer> playerList) {
		this.playerList = playerList;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFacebook() {
		return facebook;
	}

	public void setFacebook(String facebook) {
		this.facebook = facebook;
	}

	public String getImgURL() {
		return imgURL;
	}

	public void setImgURL(String imgURL) {
		this.imgURL = imgURL;
	}

}
