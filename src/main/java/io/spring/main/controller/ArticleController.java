package io.spring.main.controller;

import com.fasterxml.jackson.annotation.JsonRootName;
import io.spring.main.dao.article.MyBatisArticleDao;
import io.spring.main.dao.user.User;
import io.spring.main.infrastructure.util.exception.NoAuthorizationException;
import io.spring.main.infrastructure.util.exception.ResourceNotFoundException;
import io.spring.main.model.ArticleData;
import io.spring.main.service.ArticleQueryService;
import io.spring.main.service.AuthorizationService;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(path = "/articles/{slug}")
public class ArticleController {
    private ArticleQueryService articleQueryService;
    private MyBatisArticleDao articleRepository;

    @Autowired
    public ArticleController(ArticleQueryService articleQueryService, MyBatisArticleDao articleRepository) {
        this.articleQueryService = articleQueryService;
        this.articleRepository = articleRepository;
    }

    @GetMapping
    public ResponseEntity<?> article(@PathVariable("slug") String slug,
                                     @AuthenticationPrincipal User user) {
        return articleQueryService.findBySlug(slug, user)
            .map(articleData -> ResponseEntity.ok(articleResponse(articleData)))
            .orElseThrow(ResourceNotFoundException::new);
    }

    @PutMapping
    public ResponseEntity<?> updateArticle(@PathVariable("slug") String slug,
                                           @AuthenticationPrincipal User user,
                                           @Valid @RequestBody UpdateArticleParam updateArticleParam) {
        return articleRepository.findBySlug(slug).map(article -> {
            if (!AuthorizationService.canWriteArticle(user, article)) {
                throw new NoAuthorizationException();
            }
            article.update(
                updateArticleParam.getTitle(),
                updateArticleParam.getDescription(),
                updateArticleParam.getBody());
            articleRepository.save(article);
            return ResponseEntity.ok(articleResponse(articleQueryService.findBySlug(slug, user).get()));
        }).orElseThrow(ResourceNotFoundException::new);
    }

    @DeleteMapping
    public ResponseEntity deleteArticle(@PathVariable("slug") String slug,
                                        @AuthenticationPrincipal User user) {
        return articleRepository.findBySlug(slug).map(article -> {
            if (!AuthorizationService.canWriteArticle(user, article)) {
                throw new NoAuthorizationException();
            }
            articleRepository.remove(article);
            return ResponseEntity.noContent().build();
        }).orElseThrow(ResourceNotFoundException::new);
    }

    private Map<String, Object> articleResponse(ArticleData articleData) {
        return new HashMap<String, Object>() {{
            put("article", articleData);
        }};
    }
}

@Getter
@NoArgsConstructor
@JsonRootName("article")
class UpdateArticleParam {
    private String title = "";
    private String body = "";
    private String description = "";
}
