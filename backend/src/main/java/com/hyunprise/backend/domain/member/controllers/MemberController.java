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


    @PostMapping("")
    public ResponseEntity<Member> createOneMember(@RequestBody Member member) {
        memberService.upsertOneMember(member);
        return ResponseEntity.ok().body(member);
    }

    @GetMapping("/{memberUUID}")
    public ResponseEntity<Member> getMemberByMemberUUID(@PathVariable String memberUUID) {
        Member member = memberService.selectOneMemberByMemberUUID(memberUUID);
        return Optional.ofNullable(member)
                .map(m -> ResponseEntity.ok().body(m))
                .orElse(ResponseEntity.notFound().build());
    }

//    @GetMapping("")
//    public ResponseEntity<Member> getMemberByMemberEmail(@RequestParam("email") String memberEmail) {
//        Member member = memberService.selectOneMemberByMemberEmail(memberEmail);
//        return Optional.ofNullable(member)
//                .map(m -> ResponseEntity.ok().body(m))
//                .orElse(ResponseEntity.notFound().build());
//    }
}
