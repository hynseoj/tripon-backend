package com.ssafy.tripon.attraction.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Gugun {

    private Integer no;
    private Integer sidoCode;
    private Integer gugunCode;
    private String gugunName;
}
