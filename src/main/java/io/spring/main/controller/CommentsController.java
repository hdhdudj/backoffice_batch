package io.spring.main.controller;

import com.fasterxml.jackson.annotation.JsonRootName;
import io.spring.main.dao.article.Article;
import io.spring.main.dao.article.MyBatisArticleDao;
import io.spring.main.dao.comment.Comment;
import io.spring.main.dao.comment.MyBatisCommentDao;
import io.spring.main.dao.user.User;
import io.spring.main.infrastructure.util.util.exception.InvalidRequestException;
import io.spring.main.infrastructure.util.util.exception.NoAuthorizationException;
import io.spring.main.infrastructure.util.util.exception.ResourceNotFoundException;
import io.spring.main.model.CommentData;
import io.spring.main.service.AuthorizationService;
import io.spring.main.service.CommentQueryService;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(path = "/articles/{slug}/comments")
@RequiredArgsConstructor
public class CommentsController {
    private final MyBatisArticleDao articleRepository;
    private final MyBatisCommentDao myBatisCommentDao;
    private final CommentQueryService commentQueryService;

    @PostMapping
    public ResponseEntity<?> createComment(@PathVariable("slug") String slug,
                                                     @AuthenticationPrincipal User user,
                                                     @Valid @RequestBody NewCommentParam newCommentParam,
                                                     BindingResult bindingResult) {
        Article article = findArticle(slug);
        if (bindingResult.hasErrors()) {
            throw new InvalidRequestException(bindingResult);
        }
        Comment comment = new Comment(newCommentParam.getBody(), user.getId(), article.getId());
        myBatisCommentDao.save(comment);
        return ResponseEntity.status(201).body(commentResponse(commentQueryService.findById(comment.getId(), user).get()));
    }

    @GetMapping
    public ResponseEntity getComments(@PathVariable("slug") String slug,
                                      @AuthenticationPrincipal User user) {
        Article article = findArticle(slug);
        List<CommentData> comments = commentQueryService.findByArticleId(article.getId(), user);
        return ResponseEntity.ok(new HashMap<String, Object>() {{
            put("comments", comments);
        }});
    }

    @RequestMapping(path = "{id}", method = RequestMethod.DELETE)
    public ResponseEntity deleteComment(@PathVariable("slug") String slug,
                                        @PathVariable("id") String commentId,
                                        @AuthenticationPrincipal User user) {
        Article article = findArticle(slug);
        return myBatisCommentDao.findById(article.getId(), commentId).map(comment -> {
            if (!AuthorizationService.canWriteComment(user, article, comment)) {
                throw new NoAuthorizationException();
            }
            myBatisCommentDao.remove(comment);
            return ResponseEntity.noContent().build();
        }).orElseThrow(ResourceNotFoundException::new);
    }

    private Article findArticle(String slug) {
        return articleRepository.findBySlug(slug).map(article -> article).orElseThrow(ResourceNotFoundException::new);
    }

    private Map<String, Object> commentResponse(CommentData commentData) {
        return new HashMap<String, Object>() {{
            put("comment", commentData);
        }};
    }
}

@Getter
@NoArgsConstructor
@JsonRootName("comment")
class NewCommentParam {
    @NotBlank(message = "can't be empty")
    private String body;
}
