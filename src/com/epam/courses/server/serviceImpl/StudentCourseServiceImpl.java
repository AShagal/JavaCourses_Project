package com.epam.courses.server.serviceImpl;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import com.epam.courses.client.service.StudentCourseService;
import com.epam.courses.server.ConnectDatabase;
import com.epam.courses.server.mapper.StudentCourseMapper;
import com.epam.courses.shared.StudentCourse;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public class StudentCourseServiceImpl extends RemoteServiceServlet implements StudentCourseService {

	private SqlSessionFactory sqlSessionFactory = ConnectDatabase.get();

	@Override
	public List<StudentCourse> sendRequestReviewInfo(int idLecturer) {
		SqlSession session = sqlSessionFactory.openSession();
		List<StudentCourse> listStudentCourses = null;
		try {
			StudentCourseMapper mapper = session.getMapper(StudentCourseMapper.class);
			listStudentCourses = mapper.getListReviews(idLecturer);
		} finally {
			session.close();
		}
		return listStudentCourses;
	}

	@Override
	public boolean addReviewById(int idStudentCourse, String review) {
		SqlSession session = sqlSessionFactory.openSession();
		try {
			StudentCourseMapper mapper = session.getMapper(StudentCourseMapper.class);
			mapper.updateReviewById(idStudentCourse, review);
		} finally {
			session.commit();
			session.close();
		}
		return false;
	}

	@Override
	public boolean deleteStudentCourse(int id) {
		SqlSession session = sqlSessionFactory.openSession();
		try {
			StudentCourseMapper mapper = session.getMapper(StudentCourseMapper.class);
			mapper.deleteById(id);
		} finally {
			session.commit();
			session.close();
		}
		return false;
	}

	@Override
	public List<StudentCourse> sendRequestStudentCourse(int id) {
		SqlSession session = sqlSessionFactory.openSession();
		List<StudentCourse> listStCourse = null;
		try {
			StudentCourseMapper mapper = session.getMapper(StudentCourseMapper.class);
			listStCourse = mapper.getListStCourse(id);
		} finally {
			session.commit();
			session.close();
		}
		return listStCourse;
	}

	@Override
	public List<Integer> getCoursesByStudent(int idStudent) {
		SqlSession session = sqlSessionFactory.openSession();
		List<Integer> listStCourse = null;
		try {
			StudentCourseMapper mapper = session.getMapper(StudentCourseMapper.class);
			listStCourse = mapper.getListCourseByStudentId(idStudent);
		} finally {
			session.commit();
			session.close();
		}
		return listStCourse;
	}

	@Override
	public boolean addStudentCourse(int idCourse, int idStudent) {
		SqlSession session = sqlSessionFactory.openSession();
		try {
			StudentCourseMapper mapper = session.getMapper(StudentCourseMapper.class);
			mapper.addRecord(idCourse, idStudent);
		} finally {
			session.commit();
			session.close();
		}
		return false;
	}

	@Override
	public boolean updateMark(int idStudentCourse, Integer mark) {
		SqlSession session = sqlSessionFactory.openSession();
		try {
			StudentCourseMapper mapper = session.getMapper(StudentCourseMapper.class);
			mapper.updateMark(idStudentCourse, mark);
			mapper.updateIsFinished(idStudentCourse);
		} finally {
			session.commit();
			session.close();
		}
		return false;
	}
}
