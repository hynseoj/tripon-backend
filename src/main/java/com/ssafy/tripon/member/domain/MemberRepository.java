package com.ssafy.tripon.member.domain;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface MemberRepository {

    void save(Member member);
    List<Member> findAll();
    Member findByEmail(String email);
    int update(Member member);
    void deleteByEmail(String email);
    boolean existsByEmail(String email);
    // 댓글 이름 매핑 최적화 위해
    List<Member> findAllByEmails(@Param("list") List<String> emails);

}
