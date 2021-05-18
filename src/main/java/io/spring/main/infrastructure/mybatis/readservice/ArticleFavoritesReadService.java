package io.spring.main.infrastructure.mybatis.readservice;

import io.spring.main.dao.user.User;
import io.spring.main.model.ArticleFavoriteCount;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

@Mapper
public interface ArticleFavoritesReadService {
    boolean isUserFavorite(@Param("userId") String userId, @Param("articleId") String articleId);

    int articleFavoriteCount(@Param("articleId") String articleId);

    List<ArticleFavoriteCount> articlesFavoriteCount(@Param("ids") List<String> ids);

    Set<String> userFavorites(@Param("ids") List<String> ids, @Param("currentUser") User currentUser);
}
