package io.spring.main.infrastructure.mybatis.readservice;

import io.spring.main.model.UserData;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserReadService {

    UserData findByUsername(@Param("username") String username);

    UserData findById(@Param("id") String id);
}

