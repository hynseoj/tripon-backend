package com.ssafy.tripon.notice.presentation;

import com.ssafy.tripon.notice.application.NoticeService;
import com.ssafy.tripon.notice.application.NoticeServiceResponse;
import com.ssafy.tripon.notice.presentation.request.NoticeSaveRequest;
import com.ssafy.tripon.notice.presentation.request.NoticeUpdateRequest;
import com.ssafy.tripon.notice.presentation.response.NoticeFindAllResponse;
import com.ssafy.tripon.notice.presentation.response.NoticeFindResponse;
import com.ssafy.tripon.notice.presentation.response.NoticeSaveResponse;
import com.ssafy.tripon.notice.presentation.response.NoticeUpdateResponse;

import jakarta.validation.Valid;

import java.net.URI;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/notices")
@RequiredArgsConstructor
public class NoticeController {

    private static NoticeService noticeService;

    @PostMapping
    public ResponseEntity<NoticeSaveResponse> saveNotice(@Valid @RequestBody NoticeSaveRequest request) {
        NoticeServiceResponse response = noticeService.saveNotice(request.toCommand("admin@ssafy.com")); // @Todo: 로그인 구현 후 수정
        return ResponseEntity.created(URI.create("/api/v1/notices/" + response.id()))
                .body(NoticeSaveResponse.from(response));
    }

    @GetMapping
    public ResponseEntity<NoticeFindAllResponse> findAllNotices() {
        List<NoticeServiceResponse> responses = noticeService.findAllNotices();
        return ResponseEntity.ok(NoticeFindAllResponse.from(responses));
    }

    @GetMapping("/{noticeId}")
    public ResponseEntity<NoticeFindResponse> findNoticeById(@PathVariable(value = "noticeId") Integer id) {
        NoticeServiceResponse response = noticeService.findNoticeById(id);
        return ResponseEntity.ok(NoticeFindResponse.from(response));
    }

    @PutMapping("/{noticeId}")
    public ResponseEntity<NoticeUpdateResponse> updateNotice(
            @PathVariable(value = "noticeId") Integer id,
            @Valid @RequestBody NoticeUpdateRequest request
    ) {
        NoticeServiceResponse response = noticeService.updateNotice(request.toCommand(id, "admin@ssafy.com")); // @Todo: 로그인 구현 후 수정
        return ResponseEntity.ok(NoticeUpdateResponse.from(response));
    }

    @DeleteMapping("/{noticeId}")
    public ResponseEntity<Void> deleteNotice(@PathVariable(value = "noticeId") Integer id) {
        noticeService.deleteNoticeById(id);
        return ResponseEntity.noContent().build();
    }
}
