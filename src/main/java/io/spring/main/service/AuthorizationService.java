package io.spring.main.service;


import io.spring.main.dao.article.Article;
import io.spring.main.dao.comment.Comment;
import io.spring.main.dao.user.User;

public class AuthorizationService {
    public static boolean canWriteArticle(User user, Article article) {
        return user.getId().equals(article.getUserId());
    }

    public static boolean canWriteComment(User user, Article article, Comment comment) {
        return user.getId().equals(article.getUserId()) || user.getId().equals(comment.getUserId());
    }
}
