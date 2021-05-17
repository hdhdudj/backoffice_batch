package io.spring.main.controller;

import io.spring.main.dao.article.Article;
import io.spring.main.dao.article.MyBatisArticleDao;
import io.spring.main.dao.favorite.ArticleFavorite;
import io.spring.main.dao.favorite.MyBatisArticleFavoriteDao;
import io.spring.main.dao.user.User;
import io.spring.main.infrastructure.util.exception.ResourceNotFoundException;
import io.spring.main.model.ArticleData;
import io.spring.main.service.ArticleQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "articles/{slug}/favorite")
public class ArticleFavoriteController {
    private final MyBatisArticleFavoriteDao myBatisArticleFavoriteDao;
    private final MyBatisArticleDao articleRepository;
    private final ArticleQueryService articleQueryService;

    @PostMapping
    public ResponseEntity favoriteArticle(@PathVariable("slug") String slug,
                                          @AuthenticationPrincipal User user) {
        Article article = getArticle(slug);
        ArticleFavorite articleFavorite = new ArticleFavorite(article.getId(), user.getId());
        myBatisArticleFavoriteDao.save(articleFavorite);
        return responseArticleData(articleQueryService.findBySlug(slug, user).get());
    }

    @DeleteMapping
    public ResponseEntity unfavoriteArticle(@PathVariable("slug") String slug,
                                            @AuthenticationPrincipal User user) {
        Article article = getArticle(slug);
        myBatisArticleFavoriteDao.find(article.getId(), user.getId()).ifPresent(favorite -> {
            myBatisArticleFavoriteDao.remove(favorite);
        });
        return responseArticleData(articleQueryService.findBySlug(slug, user).get());
    }

    private ResponseEntity<HashMap<String, Object>> responseArticleData(final ArticleData articleData) {
        return ResponseEntity.ok(new HashMap<String, Object>() {{
            put("article", articleData);
        }});
    }

    private Article getArticle(String slug) {
        return articleRepository.findBySlug(slug).map(article -> article)
            .orElseThrow(ResourceNotFoundException::new);
    }
}
