package com.ssafy.tripon.review.presentation.request;

import com.ssafy.tripon.review.application.command.ReviewSaveCommand;
import com.ssafy.tripon.review.application.command.ReviewSaveCommand.ReviewDetailSaveCommand;
import java.util.List;
import org.springframework.web.multipart.MultipartFile;

public record ReviewSaveRequest(
        String title,
        List<ReviewDetailSaveRequest> details
) {
    public record ReviewDetailSaveRequest(
            Integer day,
            String content,
            List<Integer> attractions,
            List<MultipartFile> pictures
    ) {
        public ReviewDetailSaveCommand toCommand() {
            return new ReviewDetailSaveCommand(
                    day,
                    content,
                    attractions,
                    pictures
            );
        }
    }

    public ReviewSaveCommand toCommand() {
        return new ReviewSaveCommand(
                title,
                details.stream()
                        .map(detail -> detail.toCommand())
                        .toList()
        );
    }
}
