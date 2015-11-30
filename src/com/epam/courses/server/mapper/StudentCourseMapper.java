package com.epam.courses.server.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.epam.courses.shared.StudentCourse;

public interface StudentCourseMapper {

	@Select("select * from studentcourse where id_course=#{idCourse} and is_finished='0'")
	List<StudentCourse> getListByCourseId(@Param("idCourse") int idCourse);

	@Select("select * from studentcourse where id_course=#{idCourse} and id_student=#{idStudent}")
	StudentCourse isExist(@Param("idCourse") int idCourse, @Param("idStudent") int idStudent);

	@Insert("insert into studentcourse (id_student, id_course) values (#{idStudent}, #{idCourse})")
	void addRecord(@Param("idCourse") int idCourse, @Param("idStudent") int idStudent);

	@Select("select studentcourse.id, course.title, user.name, user.surname, studentcourse.mark, studentcourse.review "
			+ "from studentcourse, course, user where course.id = studentcourse.id_course and user.id=studentcourse.id_student and course.id_lecturer=#{idLecturer};")
	List<StudentCourse> getListReviews(@Param("idLecturer") int idLecturer);

	@Update("update studentcourse set review=#{review} where id=#{idstcourse}")
	void updateReviewById(@Param("idstcourse") int idStCourse, @Param("review") String review);

	@Delete("delete from studentcourse where id=#{id}")
	void deleteById(@Param("id") int id);

	@Select("select studentcourse.id, user.name, user.surname, studentcourse.review, studentcourse.is_finished, course.title, studentcourse.mark from studentcourse, "
			+ "user, course where studentcourse.id_student=#{id} and studentcourse.id_course=course.id and course.id_lecturer=user.id")
	List<StudentCourse> getListStCourse(@Param("id") int id);

	@Select("select id_course from studentcourse where id_student=#{id}")
	List<Integer> getListCourseByStudentId(@Param("id") int idStudent);

	@Delete("delete from studentcourse where id_course=#{id}")
	void deleteByCourseId(@Param("id") int id);

	@Update("update studentcourse set mark=#{mark} where id=#{id}")
	void updateMark(@Param("id") int idStudentCourse, @Param("mark") Integer mark);

	@Update("update studentcourse set is_finished='1' where id=#{id}")
	void updateIsFinished(@Param("id") int idStudentCourse);

}
