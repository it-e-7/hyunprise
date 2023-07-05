package com.hyunprise.backend.domain.member.controllers;

import com.hyunprise.backend.domain.member.services.MemberService;
import com.hyunprise.backend.domain.member.vo.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("member")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/me")
    public ResponseEntity<Member> getMemberFromJWT(@RequestHeader("Authorization") String authorizationHeader) {
        Member member = memberService.getMemberFromJwt(authorizationHeader);
        return ResponseEntity.ok().body(member);
    }
    @GetMapping("/{memberUUID}")
    public ResponseEntity<Member> getMemberByMemberUUID(@PathVariable String memberUUID) {
        Member member = memberService.selectOneMemberByMemberUUID(memberUUID);
        return Optional.ofNullable(member)
                .map(m -> ResponseEntity.ok().body(m))
                .orElse(ResponseEntity.notFound().build());
    }

}
