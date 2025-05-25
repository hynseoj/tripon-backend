package com.ssafy.tripon.comment.application;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.tripon.comment.application.command.CommentSaveCommand;
import com.ssafy.tripon.comment.application.command.CommentUpdateCommand;
import com.ssafy.tripon.comment.domain.Comment;
import com.ssafy.tripon.comment.domain.CommentRepository;
import com.ssafy.tripon.member.domain.Member;
import com.ssafy.tripon.member.domain.MemberRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class CommentService {
	private final CommentRepository commentRepository;
	private final MemberRepository memberRepository;

	// 댓글 생성
	public void saveComment(CommentSaveCommand command) {
		commentRepository.save(command.toComment());
	}

	// 댓글 조회
	public List<CommentServiceResponse> findAllByReviewId(Integer reviewId) {
	    List<Comment> comments = commentRepository.findByReviewId(reviewId);
	    if (comments.isEmpty()) {
	        return List.of(); // 댓글 없으면 그대로 빈 응답 반환
	    }
	    List<String> emails = comments.stream()
	    	    .map(Comment::getMemberId)
	    	    .distinct()
	    	    .toList();
	    Map<String, String> emailToNameMap = memberRepository.findAllByEmails(emails).stream()
	    	    .collect(Collectors.toMap(Member::getEmail, Member::getName));


	    return comments.stream()
	    	    .map(comment -> {
	    	        String name = emailToNameMap.get(comment.getMemberId());
	    	        return CommentServiceResponse.from(comment, name);
	    	    })
	    	    .toList();

	}


	// 댓글 수정
	public void updateComment(CommentUpdateCommand command) {
		commentRepository.update(command.toComment());
	}

	// 댓글 삭제
	public void deleteComment(Integer id) {
		commentRepository.delete(id);
	}
}
