package com.epam.courses.client.service;

import com.epam.courses.shared.User;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("admin")
public interface AdminService extends RemoteService {

	boolean updatePersonalData(int id, String name, String surname);

	boolean addAdmin(User admin);

}
