package com.epam.courses.server.serviceImpl;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import com.epam.courses.client.service.LecturerService;
import com.epam.courses.server.ConnectDatabase;
import com.epam.courses.server.mapper.CourseMapper;
import com.epam.courses.server.mapper.UserMapper;
import com.epam.courses.shared.Course;
import com.epam.courses.shared.User;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

@SuppressWarnings("serial")
public class LecturerServiceImpl extends RemoteServiceServlet implements LecturerService {

	private SqlSessionFactory sqlSessionFactory = ConnectDatabase.get();

	@Override
	public List<User> sendRequestLecturers() {
		SqlSession session = sqlSessionFactory.openSession();
		List<User> listLecturers = null;
		try {
			UserMapper userMapper = session.getMapper(UserMapper.class);
			listLecturers = userMapper.getListLecturers();

		} finally {
			session.commit();
			session.close();
		}
		return listLecturers;
	}

	@Override
	public Boolean sendNewLecturerToServer(User lecturer) {
		SqlSession session = sqlSessionFactory.openSession();
		List<User> record = null;
		try {
			UserMapper userMapper = session.getMapper(UserMapper.class);
			record = userMapper.getListByLogin(lecturer.getLogin());
			if (record.size() == 0) {
				userMapper.addLecturer(lecturer.getName(), lecturer.getSurname(), lecturer.getLogin(),
						lecturer.getPassword());
			}
		} finally {
			session.commit();
			session.close();
		}
		return (record.size() == 0) ? true : false;
	}

	@Override
	public boolean deleteLecturer(int id) {
		SqlSession session = sqlSessionFactory.openSession();
		try {
			UserMapper userMapper = session.getMapper(UserMapper.class);
			CourseMapper courseMapper = session.getMapper(CourseMapper.class);
			List<Course> listCourse = courseMapper.getListByLecturerId(id);
			if (!listCourse.isEmpty()) {
				return false;
			}
			userMapper.deleteById(id);

		} finally {
			session.commit();
			session.close();
		}
		return true;
	}

	@Override
	public boolean updateNameById(int id, String name) {
		SqlSession session = sqlSessionFactory.openSession();
		try {
			UserMapper userMapper = session.getMapper(UserMapper.class);
			userMapper.updateName(id, name);

		} finally {
			session.commit();
			session.close();
		}
		return false;
	}

	@Override
	public boolean updateSurnameById(int id, String surname) {
		SqlSession session = sqlSessionFactory.openSession();
		try {
			UserMapper userMapper = session.getMapper(UserMapper.class);
			userMapper.updateSurname(id, surname);

		} finally {
			session.commit();
			session.close();
		}
		return false;
	}

	@Override
	public boolean updatePersonalData(int id, String name, String surname) {
		SqlSession session = sqlSessionFactory.openSession();
		try {
			UserMapper userMapper = session.getMapper(UserMapper.class);
			userMapper.updateSurname(id, surname);
			userMapper.updateSurname(id, name);

		} finally {
			session.commit();
			session.close();
		}
		return false;
	}

}
