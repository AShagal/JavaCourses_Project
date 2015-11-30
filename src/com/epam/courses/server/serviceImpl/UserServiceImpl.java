package com.epam.courses.server.serviceImpl;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import com.epam.courses.client.service.UserService;
import com.epam.courses.server.ConnectDatabase;
import com.epam.courses.server.mapper.UserMapper;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

@SuppressWarnings("serial")
public class UserServiceImpl extends RemoteServiceServlet implements UserService {
	private SqlSessionFactory sqlSessionFactory = ConnectDatabase.get();

	@Override
	public boolean updatePassword(int id, String password) {
		SqlSession session = sqlSessionFactory.openSession();
		try {
			UserMapper mapper = session.getMapper(UserMapper.class);
			mapper.updatePassword(id, password);

		} finally {
			session.commit();
			session.close();
		}
		return false;
	}

	@Override
	public boolean updatePersonalData(String name, String surname, int id) {
		SqlSession session = sqlSessionFactory.openSession();
		try {
			UserMapper mapper = session.getMapper(UserMapper.class);
			mapper.updateName(id, name);
			mapper.updateSurname(id, surname);

		} finally {
			session.commit();
			session.close();
		}
		return false;
	}

}
