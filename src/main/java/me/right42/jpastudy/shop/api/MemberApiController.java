package me.right42.jpastudy.shop.api;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import me.right42.jpastudy.shop.domain.Member;
import me.right42.jpastudy.shop.service.MemberService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MemberApiController {

    private final MemberService memberService;


    public CreateMemberResponse saveMemberV1(@RequestBody @Validated Member member) {
        return new CreateMemberResponse();
    }

    @Data
    private class CreateMemberResponse {
        private Long id;
    }
}
