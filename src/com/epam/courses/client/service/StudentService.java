package com.epam.courses.client.service;

import java.util.List;

import com.epam.courses.shared.User;
import com.epam.courses.shared.StudentCourse;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("student")
public interface StudentService extends RemoteService {
	List<User> sendRequestStudents();

	boolean updatePersonalData(int id, String name, String surname);

}
