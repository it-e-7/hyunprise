package com.hyunprise.backend.domain.member.mappers;


import com.hyunprise.backend.domain.member.vo.Member;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MemberMapper {
    Member selectOneMemberByMemberUUID(String memberUUID);
    Member selectOneMemberByMemberEmail(String email);
    int upsertOneMember(Member member);

}
