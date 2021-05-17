package io.spring.main.infrastructure.mybatis.readservice;

import io.spring.main.model.CommentData;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CommentReadService {
    CommentData findById(@Param("id") String id);

    List<CommentData> findByArticleId(@Param("articleId") String articleId);
}
