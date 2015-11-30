package com.epam.courses.client.service;

import java.util.List;

import com.epam.courses.shared.User;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("lecturer")
public interface LecturerService extends RemoteService {
	List<User> sendRequestLecturers();

	Boolean sendNewLecturerToServer(User lecturer);

	boolean deleteLecturer(int id);

	boolean updateNameById(int id, String value);

	boolean updateSurnameById(int id, String value);

	boolean updatePersonalData(int id, String name, String surname);
}
