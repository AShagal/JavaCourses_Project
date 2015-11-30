package com.epam.courses.server.serviceImpl;

import java.util.AbstractMap.SimpleEntry;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import com.epam.courses.client.service.RegistrationService;
import com.epam.courses.server.ConnectDatabase;
import com.epam.courses.server.mapper.UserMapper;
import com.epam.courses.shared.User;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import javafx.util.Pair;

public class RegistrationServiceImpl extends RemoteServiceServlet implements RegistrationService {

	private SqlSessionFactory sqlSessionFactory = ConnectDatabase.get();

	@Override
	public User getUserByLoginPassword(String login, String password) {
		SqlSession session = sqlSessionFactory.openSession();
		User person = null;
		try {
			UserMapper mapper = session.getMapper(UserMapper.class);
			person = mapper.getRecord(login, password);
		} finally {
			session.commit();
			session.close();
		}
		return person;
	}

	@Override
	public boolean registerStudent(String name, String surname, String login, String password) {
		SqlSession session = sqlSessionFactory.openSession();
		try {
			UserMapper mapper = session.getMapper(UserMapper.class);
			List<User> regRecords = mapper.getListByLogin(login);
			if (regRecords.size() > 0) {
				return false;
			}
			mapper.addStudent(name, surname, login, password);
		} finally {
			session.commit();
			session.close();
		}
		return true;
	}

}
