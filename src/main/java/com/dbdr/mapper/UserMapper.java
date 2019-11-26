package com.dbdr.mapper;

import com.dbdr.domain.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface UserMapper {

	User findByUserId(String id);

}
