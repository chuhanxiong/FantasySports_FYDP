package com.fantasysports.Model;

public class Player {
	private int id;
	private int userId;
	private int teamId;
	private int score;
	private String name;
	private String imgURL;
	private long timestamp;

	public Player(int userId, int teamId, String name, long timestamp) {
		this.userId = userId;
		this.teamId = teamId;
		this.name = name;
		this.timestamp = timestamp;
	}

	public Player(int teamId, String name) {
		this.userId = -1;
		this.teamId = teamId;
		this.name = name;
	}

	public Player(int id, int userId, int teamId, int score, String name,
			String imgURL, long timestamp) {
		this.id = id;
		this.userId = userId;
		this.teamId = teamId;
		this.score = score;
		this.name = name;
		this.imgURL = imgURL;
		this.timestamp = timestamp;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getTeamId() {
		return teamId;
	}

	public void setTeamId(int teamId) {
		this.teamId = teamId;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
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

	public long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}

}
