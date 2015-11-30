package com.epam.courses.server.serviceImpl;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import com.epam.courses.client.service.AdminService;
import com.epam.courses.server.ConnectDatabase;
import com.epam.courses.server.mapper.UserMapper;
import com.epam.courses.shared.User;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public class AdminServiceImpl extends RemoteServiceServlet implements AdminService {
	private SqlSessionFactory sqlSessionFactory = ConnectDatabase.get();

	@Override
	public boolean updatePersonalData(int id, String name, String surname) {
		SqlSession session = sqlSessionFactory.openSession();
		try {
			UserMapper adminMapper = session.getMapper(UserMapper.class);
			adminMapper.updateName(id, name);
			adminMapper.updateSurname(id, name);
		} finally {
			session.commit();
			session.close();
		}
		return false;
	}

	@Override
	public boolean addAdmin(User admin) {
		SqlSession session = sqlSessionFactory.openSession();
		List<User> regRecord = null;
		try {
			UserMapper mapper = session.getMapper(UserMapper.class);
			regRecord = mapper.getListByLogin(admin.getLogin());
			if (regRecord.size() == 0) {
				mapper.addAdmin(admin.getName(), admin.getSurname(), admin.getLogin(), admin.getPassword());
			}

		} finally {
			session.commit();
			session.close();
		}
		return (regRecord.size() == 0) ? true : false;
	}

}
