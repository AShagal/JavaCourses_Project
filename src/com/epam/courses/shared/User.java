package com.epam.courses.shared;

import java.io.Serializable;

import com.google.gwt.view.client.ProvidesKey;

public class User implements Serializable {
	private int id;
	private String name;
	private String surname;
	private String login;
	private String password;
	private char role;

	public String getFullName() {
		return surname + " " + name;
	}

	public static final ProvidesKey<User> KEY_PROVIDER = new ProvidesKey<User>() {
		@Override
		public Object getKey(User item) {
			return item == null ? null : item.getId();
		}
	};

	public User() {
	}

	public User(String name, String surname, String login, String password) {
		this.name = name;
		this.surname = surname;
		this.login = login;
		this.password = password;

	}

	public int getId() {
		return id;
	}

	public void setId(int id_student) {
		this.id = id_student;
	}

	public String getName() {
		return name;
	}

	public void setName(String name_student) {
		this.name = name_student;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname_student) {
		this.surname = surname_student;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password_student) {
		this.password = password_student;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login_registration) {
		this.login = login_registration;
	}

	public char getRole() {
		return role;
	}

	public void setRole(char role) {
		this.role = role;
	}

}
