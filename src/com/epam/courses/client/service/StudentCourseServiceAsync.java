package com.epam.courses.client.service;

import java.util.List;

import com.epam.courses.shared.StudentCourse;
import com.google.gwt.user.client.rpc.AsyncCallback;

public interface StudentCourseServiceAsync {

	void sendRequestReviewInfo(int idLecturer, AsyncCallback<List<StudentCourse>> asyncCallback);

	void addReviewById(int idStudentCourse, String review, AsyncCallback<Boolean> asyncCallback);

	void deleteStudentCourse(int id, AsyncCallback<Boolean> asyncCallback);

	void sendRequestStudentCourse(int id, AsyncCallback<List<StudentCourse>> asyncCallback);

	void getCoursesByStudent(int idStudent, AsyncCallback<List<Integer>> asyncCallback);

	void addStudentCourse(int idCourse, int id, AsyncCallback<Boolean> asyncCallback);

	void updateMark(int idStudentCourse, Integer mark, AsyncCallback<Boolean> asyncCallback);

}
