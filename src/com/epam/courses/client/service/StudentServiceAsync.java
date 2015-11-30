package com.epam.courses.client.service;

import java.util.List;

import com.epam.courses.shared.User;
import com.epam.courses.shared.StudentCourse;
import com.google.gwt.user.client.rpc.AsyncCallback;

public interface StudentServiceAsync {

	void sendRequestStudents(AsyncCallback<List<User>> callback);

	void updatePersonalData(int id, String name, String surname, AsyncCallback<Boolean> asyncCallback);

}
