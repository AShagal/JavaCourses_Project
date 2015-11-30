package com.epam.courses.server.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.epam.courses.shared.Course;

public interface CourseMapper {

	@Select("select course.id, course.title, user.name, user.surname from course, user where course.id_lecturer = user.id")
	List<Course> getListCourse();

	@Select("select * from course where title=#{title} and id_lecturer=#{idLecturer}")
	List<Course> getListByTitleLecturer(@Param("title") String title, @Param("idLecturer") int idLecturer);

	@Insert("insert into course (title, id_lecturer) values (#{title}, #{idLecturer})")
	void addCourse(@Param("title") String title, @Param("idLecturer") int idLecturer);

	@Select("select * from course where id_lecturer=#{idLecturer}")
	List<Course> getListByLecturerId(@Param("idLecturer") int i);

	@Delete("delete from course where id=#{idCourse}")
	void deleteCourseById(@Param("idCourse") int i);

	@Update("update course set title=#{newTitle} where id=#{id}")
	void uodateTitleById(@Param("id") int id, @Param("newTitle") String title);

	@Update("update course set id_lecturer=#{idLecturer} where id=#{idCourse}")
	void uodateLecturerById(@Param("idCourse") int idCourse, @Param("idLecturer") int idLecturer);

}
