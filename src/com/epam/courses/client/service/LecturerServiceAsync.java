package com.epam.courses.client.service;

import java.util.List;

import com.epam.courses.shared.User;
import com.google.gwt.user.client.rpc.AsyncCallback;

public interface LecturerServiceAsync {
	void sendRequestLecturers(AsyncCallback<List<User>> callback);

	void sendNewLecturerToServer(User lecturer, AsyncCallback<Boolean> callback);

	void deleteLecturer(int id, AsyncCallback<Boolean> callback);

	void updateNameById(int id, String value, AsyncCallback<Boolean> asyncCallback);

	void updateSurnameById(int id, String value, AsyncCallback<Boolean> asyncCallback);

	void updatePersonalData(int id, String name, String surname, AsyncCallback<Boolean> asyncCallback);
}
