package com.ssafy.tripon.like.presentation;

import com.ssafy.tripon.like.application.LikeService;
import com.ssafy.tripon.like.application.command.LikeSaveCommand;
import com.ssafy.tripon.like.presentation.response.LikeCountResponse;
import java.net.URI;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/reviews/{reviewId}/likes")
@RequiredArgsConstructor
public class LikeController {

    private final LikeService likeService;

    @PostMapping
    public ResponseEntity<Void> saveLike(@PathVariable(value = "reviewId") Integer reviewId) {
        likeService.saveLike(new LikeSaveCommand("admin@ssafy.com", reviewId)); // @Todo: 로그인 구현 후 수정
        return ResponseEntity.created(URI.create("/api/v1/reviews/" + reviewId)).build();
    }

    @GetMapping
    public ResponseEntity<LikeCountResponse> getCound(@PathVariable(value = "reviewId") Integer reviewId) {
        Integer likeCount = likeService.getCount(reviewId);
        return ResponseEntity.ok(LikeCountResponse.from(likeCount));
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteLike(@PathVariable(value = "reviewId") Integer reviewId) {
        likeService.deleteLike(reviewId, "admin@ssafy.com"); // @Todo: 로그인 구현 후 수정
        return ResponseEntity.noContent().build();
    }
}
