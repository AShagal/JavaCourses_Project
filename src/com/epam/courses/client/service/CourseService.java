package com.epam.courses.client.service;

import java.util.List;

import com.epam.courses.shared.Course;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

/**
 * The client-side stub for the RPC service.
 */
@RemoteServiceRelativePath("course")
public interface CourseService extends RemoteService {
	List<Course> sendRequestCourses();

	Boolean sendNewCourseToServer(Course course);

	boolean deleteCourseById(int id);

	boolean updateCourseTitleById(int id, String value);

	boolean updateCourseLecturer(int idCourse, int idLecturer);
}
