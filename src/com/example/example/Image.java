package com.example.example;

public class Image {

	private int id;
	private String name;
	private String date;
	private byte[] image;

	public Image(int id, String name, String date, byte[] image) {
		this.id = id;
		this.name = name;
		this.date = date;
		this.image = image;
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

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public byte[] getImage() {
		return image;
	}

	public void setImage(byte[] image) {
		this.image = image;
	}

}
