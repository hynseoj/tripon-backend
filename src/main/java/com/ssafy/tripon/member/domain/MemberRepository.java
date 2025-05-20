package com.ssafy.tripon.member.domain;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MemberRepository {

    void save(Member member);
    List<Member> findAll();
    Member findByEmail(String email);
    int update(Member member);
    void deleteByEmail(String email);
    boolean existsByEmail(String email);
}
