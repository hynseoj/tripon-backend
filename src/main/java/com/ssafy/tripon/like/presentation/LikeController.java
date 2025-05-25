package com.ssafy.tripon.like.presentation;

import java.net.URI;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.tripon.common.auth.config.LoginMember;
import com.ssafy.tripon.like.application.LikeService;
import com.ssafy.tripon.like.application.command.LikeSaveCommand;
import com.ssafy.tripon.like.presentation.response.LikeCountResponse;
import com.ssafy.tripon.member.domain.Member;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/reviews/{reviewId}/likes")
@RequiredArgsConstructor
public class LikeController {

    private final LikeService likeService;

    @PostMapping
    public ResponseEntity<Void> saveLike(@PathVariable(value = "reviewId") Integer reviewId,  @LoginMember Member member) {
        likeService.saveLike(new LikeSaveCommand(member.getEmail(), reviewId)); // @Todo: 로그인 구현 후 수정
        return ResponseEntity.created(URI.create("/api/v1/reviews/" + reviewId)).build();
    }

    @GetMapping
    public ResponseEntity<LikeCountResponse> getCount(@PathVariable(value = "reviewId") Integer reviewId) {
        Integer likeCount = likeService.getCount(reviewId);
        return ResponseEntity.ok(LikeCountResponse.from(likeCount));
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteLike(@PathVariable(value = "reviewId") Integer reviewId,  @LoginMember Member member) {
        likeService.deleteLike(reviewId, member.getEmail()); // @Todo: 로그인 구현 후 수정
        return ResponseEntity.noContent().build();
    }
}
