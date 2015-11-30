package com.epam.courses.client.service;

import java.util.List;
import java.util.AbstractMap.SimpleEntry;

import com.epam.courses.shared.User;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import javafx.util.Pair;

@RemoteServiceRelativePath("registration")
public interface RegistrationService extends RemoteService {

	User getUserByLoginPassword(String login, String password);

	boolean registerStudent(String name, String surname, String login, String password);

}
