package io.spring.main.dao.comment;

import java.util.Optional;

public interface MyBatisCommentDao {
    void save(Comment comment);

    Optional<Comment> findById(String articleId, String id);

    void remove(Comment comment);
}
