package io.spring.main.controller;

import io.spring.main.dao.user.FollowRelation;
import io.spring.main.dao.user.User;
import io.spring.main.dao.user.UserRepository;
import io.spring.main.infrastructure.util.exception.ResourceNotFoundException;
import io.spring.main.model.ProfileData;
import io.spring.main.service.ProfileQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Optional;

@RestController
@RequestMapping(path = "profiles/{username}")
@RequiredArgsConstructor
public class ProfileController {
    private final ProfileQueryService profileQueryService;
    private final UserRepository userRepository;

    @GetMapping
    public ResponseEntity getProfile(@PathVariable("username") String username,
                                     @AuthenticationPrincipal User user) {
        return profileQueryService.findByUsername(username, user)
            .map(this::profileResponse)
            .orElseThrow(ResourceNotFoundException::new);
    }

    @PostMapping(path = "follow")
    public ResponseEntity follow(@PathVariable("username") String username,
                                 @AuthenticationPrincipal User user) {
        return userRepository.findByUsername(username).map(target -> {
            FollowRelation followRelation = new FollowRelation(user.getId(), target.getId());
            userRepository.saveRelation(followRelation);
            return profileResponse(profileQueryService.findByUsername(username, user).get());
        }).orElseThrow(ResourceNotFoundException::new);
    }

    @DeleteMapping(path = "follow")
    public ResponseEntity unfollow(@PathVariable("username") String username,
                                   @AuthenticationPrincipal User user) {
        Optional<User> userOptional = userRepository.findByUsername(username);
        if (userOptional.isPresent()) {
            User target = userOptional.get();
            return userRepository.findRelation(user.getId(), target.getId())
                .map(relation -> {
                    userRepository.removeRelation(relation);
                    return profileResponse(profileQueryService.findByUsername(username, user).get());
                }).orElseThrow(ResourceNotFoundException::new);
        } else {
            throw new ResourceNotFoundException();
        }
    }

    private ResponseEntity profileResponse(ProfileData profile) {
        return ResponseEntity.ok(new HashMap<String, Object>() {{
            put("profile", profile);
        }});
    }
}
