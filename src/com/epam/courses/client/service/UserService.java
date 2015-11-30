package com.epam.courses.client.service;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("user")
public interface UserService extends RemoteService {

	boolean updatePassword(int id, String newPassword);

	boolean updatePersonalData(String name, String surname, int id);

}
