package com.epam.courses.client.service;

import java.util.List;

import com.epam.courses.shared.StudentCourse;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("studentcourse")
public interface StudentCourseService extends RemoteService {

	boolean addReviewById(int idStudentCourse, String review);

	List<StudentCourse> sendRequestReviewInfo(int idLecturer);

	boolean deleteStudentCourse(int id);

	List<StudentCourse> sendRequestStudentCourse(int id);

	List<Integer> getCoursesByStudent(int idStudent);

	boolean addStudentCourse(int idCourse, int id);

	boolean updateMark(int idStudentCourse, Integer mark);
}
