package com.ssafy.tripon.attraction.presentation.response;

import com.ssafy.tripon.attraction.application.SidoResponse;
import java.util.List;

public record SidoFindAllResponse(
        List<SidoResponse> sidos
) {
    public static SidoFindAllResponse from(List<SidoResponse> sidos) {
        return new SidoFindAllResponse(sidos);
    }
}
