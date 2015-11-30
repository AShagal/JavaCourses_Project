package com.epam.courses.server.serviceImpl;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import com.epam.courses.client.service.AdminService;
import com.epam.courses.client.service.StudentService;
import com.epam.courses.server.ConnectDatabase;
import com.epam.courses.server.mapper.UserMapper;
import com.epam.courses.shared.User;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public class StudentServiceImpl extends RemoteServiceServlet implements StudentService {
	private SqlSessionFactory sqlSessionFactory = ConnectDatabase.get();

	@Override
	public List<User> sendRequestStudents() {
		SqlSession session = sqlSessionFactory.openSession();
		List<User> listStudents = null;
		try {
			UserMapper userMapper = session.getMapper(UserMapper.class);
			listStudents = userMapper.getListStudents();

		} finally {
			session.commit();
			session.close();
		}
		return listStudents;
	}

	@Override
	public boolean updatePersonalData(int id, String name, String surname) {
		SqlSession session = sqlSessionFactory.openSession();
		try {
			UserMapper userMapper = session.getMapper(UserMapper.class);
			userMapper.updateName(id, name);
			userMapper.updateSurname(id, surname);

		} finally {
			session.commit();
			session.close();
		}
		return false;
	}

}
