package com.epam.courses.client.service;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface UserServiceAsync {

	void updatePassword(int id, String newPassword, AsyncCallback<Boolean> asyncCallback);

	void updatePersonalData(String name, String surname, int id, AsyncCallback<Boolean> asyncCallback);

}
