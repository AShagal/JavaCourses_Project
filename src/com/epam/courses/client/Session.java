package com.epam.courses.client;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.epam.courses.shared.User;

public class Session {

	enum Role {
		STUDENT, LECTURER, ADMIN, USER
	};

	private User person;
	private Role role;
	private List<Integer> studentCourses = null;
	private List<User> lecturers = null;

	public static final Map<Character, Role> ROLES_MAP = createMap();

	private static Map<Character, Role> createMap() {
		Map<Character, Role> result = new HashMap<Character, Role>();
		result.put('A', Role.ADMIN);
		result.put('S', Role.STUDENT);
		result.put('L', Role.LECTURER);
		return Collections.unmodifiableMap(result);
    }

	public Session() {
		role = Role.USER;
	}

	void startSession(User person, Role role) {
		this.role = role;
		this.person = person;
	}

	void endSession() {
		person = null;
		// lecturers = null;
		// studentCourses = null;
		role = Role.USER;
	}

	List<Integer> getStudCourses() {
		return studentCourses;
	}


	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public void setPerson(User person) {
		this.person = person;
	}

	public User getPerson() {
		return person;
	}


	public void updateIdCourses(List<Integer> result) {
		studentCourses = result;
	}

	public void addIdCourse(int id) {
		studentCourses.add(id);
	}

	public void removeIdCourse(int id) {
		studentCourses.remove(id);
	}

	public List<User> getLecturers() {
		return lecturers;
	}

	public void setLecturers(List<User> lecturers) {
		this.lecturers = lecturers;
	}

	public void setPersonalData(String name, String surname) {
		this.person.setName(name);
		this.person.setSurname(surname);

	}
}
