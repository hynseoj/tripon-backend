package com.ssafy.tripon.attraction.application;

import com.ssafy.tripon.attraction.domain.Sido;

public record SidoResponse(
        Integer sidoCode,
        String sidoName
) {
    public static SidoResponse from(Sido sido) {
        return new SidoResponse(sido.getSidoCode(), sido.getSidoName());
    }
}
