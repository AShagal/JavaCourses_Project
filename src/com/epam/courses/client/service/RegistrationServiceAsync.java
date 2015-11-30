package com.epam.courses.client.service;

import java.util.AbstractMap.SimpleEntry;
import java.util.List;

import com.epam.courses.shared.User;
import com.google.gwt.user.client.rpc.AsyncCallback;

import javafx.util.Pair;

public interface RegistrationServiceAsync {

	void registerStudent(String name, String surname, String login, String password,
			AsyncCallback<Boolean> asyncCallback);

	void getUserByLoginPassword(String login, String password, AsyncCallback<User> callback);

}
