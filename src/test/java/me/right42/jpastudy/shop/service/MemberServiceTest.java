package me.right42.jpastudy.shop.service;

import me.right42.jpastudy.shop.domain.Member;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Transactional
class MemberServiceTest {

    @Autowired
    MemberService memberService;

    @Test
    @DisplayName("회원가입 테스트")
    void joinTest() {
        Member member = new Member();
        member.setName("kim");

        Long savedId = memberService.join(member);

        assertThat(member).isEqualTo(memberService.findOne(savedId));
    }

    @Test
    void joinValidationTest() {
        Member member = new Member();
        member.setName("kim");

        Member member2 = new Member();
        member2.setName("kim");

        memberService.join(member);
        IllegalStateException illegalStateException = assertThrows(IllegalStateException.class, () -> {
            memberService.join(member2);
        });

        assertThat(illegalStateException.getMessage()).isEqualTo("이미 존재하는 회원입니다.");
    }
}