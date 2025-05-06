package com.ssafy.tripon.notice.application;


import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

import com.ssafy.tripon.notice.application.command.NoticeSaveCommand;
import com.ssafy.tripon.notice.application.command.NoticeUpdateCommand;
import com.ssafy.tripon.notice.domain.Notice;
import com.ssafy.tripon.notice.domain.NoticeRepository;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class NoticeServiceTest {

    @Autowired
    private NoticeService noticeService;

    @Autowired
    private NoticeRepository noticeRepository;

    @Test
    void 공지사항을_저장할_수_있다() {
        // given
        NoticeSaveCommand command = new NoticeSaveCommand("admin@ssafy.com", "테스트 제목", "테스트 내용");

        // when
        NoticeServiceResponse response = noticeService.saveNotice(command);

        // then
        Notice savedNotice = noticeRepository.findById(response.id());
        assertThat(savedNotice.getTitle()).isEqualTo("테스트 제목");
        assertThat(savedNotice.getContent()).isEqualTo("테스트 내용");
    }

    @Test
    void 공지사항_목록을_조회할_수_있다() {
        // given
        noticeService.saveNotice(new NoticeSaveCommand("admin@ssafy.com", "제목1", "내용1"));
        noticeService.saveNotice(new NoticeSaveCommand("admin@ssafy.com", "제목2", "내용2"));

        // when
        List<NoticeServiceResponse> notices = noticeService.findAllNotice();

        // then
        assertThat(notices).hasSizeGreaterThanOrEqualTo(2);
    }

    @Test
    void 공지사항을_아이디로_조회할_수_있다() {
        // given
        NoticeServiceResponse saved = noticeService.saveNotice(new NoticeSaveCommand("admin@ssafy.com", "제목", "내용"));

        // when
        NoticeServiceResponse found = noticeService.findNoticeById(saved.id());

        // then
        assertThat(found.title()).isEqualTo("제목");
    }

    @Test
    void 공지사항을_수정할_수_있다() {
        // given
        NoticeServiceResponse saved = noticeService.saveNotice(new NoticeSaveCommand("admin@ssafy.com", "제목", "내용"));

        // when
        NoticeServiceResponse updated = noticeService.updateNotice(new NoticeUpdateCommand(saved.id(), "admin@ssafy.com", "수정 제목", "수정 내용"));

        // then
        assertThat(updated.title()).isEqualTo("수정 제목");
        assertThat(updated.content()).isEqualTo("수정 내용");
    }

    @Test
    void 공지사항을_삭제할_수_있다() {
        // given
        NoticeServiceResponse saved = noticeService.saveNotice(new NoticeSaveCommand("admin@ssafy.com", "제목", "내용"));

        // when
        noticeService.deleteNoticeById(saved.id());

        // then
        List<NoticeServiceResponse> notices = noticeService.findAllNotice();
        assertThat(notices.stream().anyMatch(n -> n.id().equals(saved.id()))).isFalse();
    }
}
