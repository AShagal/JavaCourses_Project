package com.epam.courses.server;

import java.io.IOException;
import java.io.Reader;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import com.epam.courses.server.mapper.CourseMapper;
import com.epam.courses.server.mapper.StudentCourseMapper;
import com.epam.courses.server.mapper.UserMapper;

public class ConnectDatabase {
	private static SqlSessionFactory sqlSessionFactory;

	public static SqlSessionFactory get() {
		if (sqlSessionFactory == null) {
			try {
				Reader resourceReader = Resources.getResourceAsReader("com/epam/courses/resourses/config.xml");
				sqlSessionFactory = new SqlSessionFactoryBuilder().build(resourceReader);

				sqlSessionFactory.getConfiguration().addMapper(CourseMapper.class);
				sqlSessionFactory.getConfiguration().addMapper(UserMapper.class);
				sqlSessionFactory.getConfiguration().addMapper(StudentCourseMapper.class);
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
		return sqlSessionFactory;
	}

	private ConnectDatabase() {
	}

}