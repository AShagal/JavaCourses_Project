package com.epam.courses.client.service;

import com.epam.courses.shared.User;
import com.google.gwt.user.client.rpc.AsyncCallback;

public interface AdminServiceAsync {

	void updatePersonalData(int id, String name, String surname, AsyncCallback<Boolean> asyncCallback);

	void addAdmin(User admin, AsyncCallback<Boolean> asyncCallback);

}
