package com.ssafy.tripon.attraction.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ContentType {

    private Integer contentTypeId;
    private String contentTypeName;
}
