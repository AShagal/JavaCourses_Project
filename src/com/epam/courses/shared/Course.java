package com.epam.courses.shared;

import java.io.Serializable;

import com.google.gwt.view.client.ProvidesKey;

public class Course implements Serializable {

	private int id;
	private String title;
	private String name;
	private String surname;
	private int id_lecturer;

	public String getFullName() {
		return surname + " " + name;
	}

	public static final ProvidesKey<Course> KEY_PROVIDER = new ProvidesKey<Course>() {
		@Override
		public Object getKey(Course item) {
			return item == null ? null : item.getId();
		}
	};

	public Course() {
		// TODO Auto-generated constructor stub
	}

	public Course(String title, int idLecturer) {
		this.title = title;
		this.id_lecturer = idLecturer;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getSurnameLecturer() {
		return surname;
	}

	public void setSurnameLecturer(String surnameLecturer) {
		this.surname = surnameLecturer;
	}

	public String getNameLecturer() {
		return name;
	}

	public void setNameLecturer(String nameLecturer) {
		this.name = nameLecturer;
	}

	public int getIdLecturer() {
		return id_lecturer;
	}

	public void setIdLecturer(int id_lecturer) {
		this.id_lecturer = id_lecturer;
	}

}
