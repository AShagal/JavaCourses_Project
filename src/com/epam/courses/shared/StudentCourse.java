package com.epam.courses.shared;

import java.io.Serializable;

import com.google.gwt.view.client.ProvidesKey;

public class StudentCourse implements Serializable {

	private int id;
	private int id_course;
	private int id_student;
	private String review;
	private String title;
	private String name;
	private String surname;
	private int mark;
	private boolean is_finished;

	public String getFullName() {
		return surname + " " + name;
	}

	public static final ProvidesKey<StudentCourse> KEY_PROVIDER = new ProvidesKey<StudentCourse>() {
		@Override
		public Object getKey(StudentCourse item) {
			return item == null ? null : item.getId();
		}
	};

	public String getReview() {
		return review;
	}

	public void setReview(String review_student) {
		this.review = review_student;
	}

	public int getIdStudent() {
		return id_student;
	}

	public void setIdStudent(int id_student) {
		this.id_student = id_student;
	}

	public int getIdCourse() {
		return id_course;
	}

	public void setIdCourse(int id_course) {
		this.id_course = id_course;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getMark() {
		return mark;
	}

	public void setMark(int mark) {
		this.mark = mark;
	}

	public boolean isFinished() {
		return is_finished;
	}

}
