package com.epam.courses.server.serviceImpl;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import com.epam.courses.client.service.CourseService;
import com.epam.courses.server.ConnectDatabase;
import com.epam.courses.server.mapper.CourseMapper;
import com.epam.courses.server.mapper.StudentCourseMapper;
import com.epam.courses.shared.Course;
import com.epam.courses.shared.StudentCourse;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

/**
 * The server-side implementation of the RPC service.
 */
@SuppressWarnings("serial")
public class CourseServiceImpl extends RemoteServiceServlet
		implements CourseService {

	private SqlSessionFactory sqlSessionFactory = ConnectDatabase.get();

	@Override
	public List<Course> sendRequestCourses() throws IllegalArgumentException {
		SqlSession session = sqlSessionFactory.openSession();
		List<Course> listCourses = null;
		try {
			CourseMapper mapper = session.getMapper(CourseMapper.class);
			listCourses = mapper.getListCourse();
		} finally {
			session.commit();
			session.close();
		}
		return listCourses;
	}

	@Override
	public Boolean sendNewCourseToServer(Course course) {
		SqlSession session = sqlSessionFactory.openSession();
		List<Course> courses;
		try {
			CourseMapper courseMapper = session.getMapper(CourseMapper.class);
			courses = courseMapper.getListByTitleLecturer(course.getTitle(), course.getIdLecturer());
			if (courses.size() == 0) {
				courseMapper.addCourse(course.getTitle(), course.getIdLecturer());
			}

		} finally {
			session.commit();
			session.close();
		}
		return courses.size() == 0 ? true : false;
	}

	@Override
	public boolean deleteCourseById(int id) {
		SqlSession session = sqlSessionFactory.openSession();
		try {
			CourseMapper courseMapper = session.getMapper(CourseMapper.class);
			StudentCourseMapper stcourseMapper = session.getMapper(StudentCourseMapper.class);
			List<StudentCourse> listCourse = stcourseMapper.getListByCourseId(id);
			if (!listCourse.isEmpty()) {
				return false;
			}
			courseMapper.deleteCourseById(id);
			stcourseMapper.deleteByCourseId(id);
			
		} finally {
			session.commit();
			session.close();
		}
		return true;
	}

	@Override
	public boolean updateCourseTitleById(int id, String title) {
		SqlSession session = sqlSessionFactory.openSession();
		try {
			CourseMapper courseMapper = session.getMapper(CourseMapper.class);
			courseMapper.uodateTitleById(id, title);

		} finally {
			session.commit();
			session.close();
		}
		return false;
	}

	@Override
	public boolean updateCourseLecturer(int idCourse, int idLecturer) {
		SqlSession session = sqlSessionFactory.openSession();
		try {
			CourseMapper courseMapper = session.getMapper(CourseMapper.class);
			courseMapper.uodateLecturerById(idCourse, idLecturer);

		} finally {
			session.commit();
			session.close();
		}
		return false;
	}
}
