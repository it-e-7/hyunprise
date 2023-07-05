package com.hyunprise.backend.domain.member.services;

import com.hyunprise.backend.domain.member.mappers.MemberMapper;
import com.hyunprise.backend.domain.member.vo.Member;
import com.hyunprise.backend.global.security.utils.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService{
    private final MemberMapper memberMapper;
    private final JwtUtil jwtUtil;

    @Override
    public Member selectOneMemberByMemberUUID(String memberUUID) {
        return memberMapper.selectOneMemberByMemberUUID(memberUUID);
    }

    @Override
    public Member selectOneMemberByMemberEmail(String email) {
        return memberMapper.selectOneMemberByMemberEmail(email);
    }
    
    @Override
    @Transactional
    public int upsertOneMember(Member member) {
        return memberMapper.upsertOneMember(member);
    }

    @Override
    public Member getMemberFromJwt(String header) {
        String accessToken = jwtUtil.parseTokenFromAuthorizationHeader(header);
        String memberUUID = jwtUtil.getMemberUUIDFromToken(accessToken);
        return memberMapper.selectOneMemberByMemberUUID(memberUUID);
    }
}
