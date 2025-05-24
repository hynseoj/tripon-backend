package com.ssafy.tripon.attraction.domain;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface GugunRepository {
    List<Gugun> findAllGuguns(Integer sidoCode);
}
