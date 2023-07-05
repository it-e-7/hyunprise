package com.hyunprise.backend.domain.member.services;

import com.hyunprise.backend.domain.member.vo.Member;

public interface MemberService {
    Member selectOneMemberByMemberUUID(String memberUUID);
    Member selectOneMemberByMemberEmail(String email);
    int upsertOneMember(Member member);
}
