package com.ssafy.tripon.notice.application;

import com.ssafy.tripon.common.exception.CustomException;
import com.ssafy.tripon.common.exception.ErrorCode;
import com.ssafy.tripon.notice.application.command.NoticeSaveCommand;
import com.ssafy.tripon.notice.application.command.NoticeUpdateCommand;
import com.ssafy.tripon.notice.domain.Notice;
import com.ssafy.tripon.notice.domain.NoticeRepository;
import java.util.List;
import java.util.Optional;

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
		return noticeRepository.findAll().stream().map(NoticeServiceResponse::from).toList();
	}

	public NoticeServiceResponse findNoticeById(Integer id) {
		// 예외처리
		Notice notice = Optional.ofNullable(noticeRepository.findById(id))
				.orElseThrow(() -> new CustomException(ErrorCode.NOTICES_NOT_FOUND));

		return NoticeServiceResponse.from(notice);
	}

	public NoticeServiceResponse updateNotice(NoticeUpdateCommand command) {
		Notice notice = command.toNotice();
		int result = noticeRepository.update(notice);
		
		// 업데이트 예외처리
		if(result == 0) {
			throw new CustomException(ErrorCode.NOTICES_NOT_FOUND);
		}
		
		// 조회 예외처리
		Notice updatedNotice = Optional.ofNullable(noticeRepository.findById(notice.getId()))
				.orElseThrow(() -> new CustomException(ErrorCode.NOTICES_NOT_FOUND));
		
		return NoticeServiceResponse.from(updatedNotice);
	}

	public void deleteNoticeById(Integer id) {
		int result = noticeRepository.deleteById(id);
		
		// 예외처리
		if(result == 0) {
			throw new CustomException(ErrorCode.NOTICES_NOT_FOUND);
		}
	}
}
