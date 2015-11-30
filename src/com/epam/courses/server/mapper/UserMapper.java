package com.epam.courses.server.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.epam.courses.shared.User;

public interface UserMapper {

	@Update("update user set name=#{newName} where id=#{id}")
	void updateName(@Param("id") int id, @Param("newName") String name);

	@Update("update user set surname=#{newSurname} where id=#{id}")
	void updateSurname(@Param("id") int id, @Param("newSurname") String surname);

	@Delete("delete from user where id=#{idReg}")
	void deleteById(@Param("id") int id);

	@Select("select * from user where login=#{login}")
	List<User> getListByLogin(@Param("login") String login);

	@Select("select * from user where role='L'")
	List<User> getListLecturers();

	@Insert("insert into user (login, password, name, surname, role) values (#{login}, #{password}, #{name}, #{surname}, 'L')")
	void addLecturer(@Param("name") String name, @Param("surname") String surname, @Param("login") String login,
			@Param("password") String password);

	@Select("select * from user where role='S'")
	List<User> getListStudents();

	@Select("select * from user where login=#{login} and password=#{password}")
	User getRecord(@Param("login") String login, @Param("password") String password);

	@Insert("insert into user (name, surname, login, password, role) values (#{name}, #{surname}, #{login}, #{password}, 'S')")
	void addStudent(@Param("name") String name, @Param("surname") String surname, @Param("login") String login,
			@Param("password") String password);

	@Insert("insert into user (name, surname, login, password, role) values (#{name}, #{surname}, #{login}, #{password}, 'A')")
	void addAdmin(@Param("name") String name, @Param("surname") String surname, @Param("login") String login,
			@Param("password") String password);

	@Update("update user set password=#{password} where id=#{id}")
	void updatePassword(@Param("id") int id, @Param("password") String password);

}
