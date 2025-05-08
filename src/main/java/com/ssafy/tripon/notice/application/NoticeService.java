package com.ssafy.tripon.notice.application;

import com.ssafy.tripon.notice.application.command.NoticeSaveCommand;
import com.ssafy.tripon.notice.application.command.NoticeUpdateCommand;
import com.ssafy.tripon.notice.domain.Notice;
import com.ssafy.tripon.notice.domain.NoticeRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class NoticeService {

    private final NoticeRepository noticeRepository;

    public NoticeServiceResponse saveNotice(NoticeSaveCommand command) {
        Notice notice = command.toNotice();
        noticeRepository.save(notice);
        return NoticeServiceResponse.from(notice);
    }

    public List<NoticeServiceResponse> findAllNotices() {
        return noticeRepository.findAll().stream()
                .map(NoticeServiceResponse::from)
                .toList();
    }

    public NoticeServiceResponse findNoticeById(Integer id) {
        return NoticeServiceResponse.from(noticeRepository.findById(id));
    }

    public NoticeServiceResponse updateNotice(NoticeUpdateCommand command) {
        Notice notice = command.toNotice();
        noticeRepository.update(notice);
        Notice updatedNotice = noticeRepository.findById(notice.getId());
        return NoticeServiceResponse.from(updatedNotice);
    }

    public void deleteNoticeById(Integer id) {
        noticeRepository.deleteById(id);
    }
}
