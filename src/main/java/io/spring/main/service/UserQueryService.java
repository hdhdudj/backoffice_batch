package io.spring.main.service;

import io.spring.main.infrastructure.mybatis.readservice.UserReadService;
import io.spring.main.model.UserData;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserQueryService  {
    private UserReadService userReadService;

    public UserQueryService(UserReadService userReadService) {
        this.userReadService = userReadService;
    }

    public Optional<UserData> findById(String id) {
        return Optional.ofNullable(userReadService.findById(id));
    }
}

