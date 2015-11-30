package com.epam.courses.client.service;

import java.util.List;

import com.epam.courses.shared.Course;
import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * The async counterpart of <code>GreetingService</code>.
 */
public interface CourseServiceAsync {

	void sendRequestCourses(AsyncCallback<List<Course>> callback);

	void sendNewCourseToServer(Course course, AsyncCallback<Boolean> asyncCallback);

	void deleteCourseById(int id, AsyncCallback<Boolean> asyncCallback);

	void updateCourseTitleById(int id, String value, AsyncCallback<Boolean> asyncCallback);

	void updateCourseLecturer(int idCourse, int idLecturer, AsyncCallback<Boolean> asyncCallback);

}
