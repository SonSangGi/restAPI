package com.dbdr.mapper;

import com.dbdr.domain.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface UserMapper {

	@Select("SELECT EXISTS(SELECT 1 FROM user WHERE id = #{id})")
	boolean checkUserId(String id);

	User findByUserId(String id);

	void insertUser(User user);

	void modifyUser(User user);
}
