package com.ssafy.tripon.notice.domain;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface NoticeRepository {

    void save(Notice notice);
    List<Notice> findAll();
    Notice findById(Integer id);
    void update(Notice notice);
    void deleteById(Integer id);
}
