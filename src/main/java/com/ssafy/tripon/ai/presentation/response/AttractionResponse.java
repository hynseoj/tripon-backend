package com.ssafy.tripon.ai.presentation.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AttractionResponse {
    private String title;
    private Integer areaCode;
    private Integer siGunGuCode;
    private Double latitude;
    private Double longitude;
    private String address;
}

