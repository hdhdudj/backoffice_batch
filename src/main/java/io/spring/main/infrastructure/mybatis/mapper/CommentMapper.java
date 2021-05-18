package io.spring.main.infrastructure.mybatis.mapper;

import io.spring.main.dao.comment.Comment;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface CommentMapper {
    void insert(@Param("comment") Comment comment);

    Comment findById(@Param("articleId") String articleId, @Param("id") String id);

    void delete(@Param("id") String id);
}
