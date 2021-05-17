package io.spring.main.dao.favorite;

import java.util.Optional;

public interface MyBatisArticleFavoriteDao {
    void save(ArticleFavorite articleFavorite);

    Optional<ArticleFavorite> find(String articleId, String userId);

    void remove(ArticleFavorite favorite);
}
