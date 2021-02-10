package me.right42.jpastudy.shop.service;

import me.right42.jpastudy.shop.domain.Address;
import me.right42.jpastudy.shop.domain.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MemberServiceTest {

    @Autowired
    MemberService memberService;

    @Test
    void joinTest(){
        Member member = new Member();
        member.setAddress(
                Address.builder()
                    .city("서울")
                    .street("도시")
                    .zipcode("1234")
                .build()
        );
        member.setName("right");

        Long id = memberService.join(member);

        assertThat(id).isEqualTo(member.getId());
    }

    @Test
    void joinFailTest(){

        Member member = new Member();
        member.setAddress(
                Address.builder()
                        .city("서울")
                        .street("도시")
                        .zipcode("1234")
                        .build()
        );
        member.setName("right");

        Member secondMember = new Member();
        secondMember.setAddress(
                Address.builder()
                        .city("서울")
                        .street("도시")
                        .zipcode("1234")
                        .build()
        );
        secondMember.setName("right");

        memberService.join(member);
        assertThrows(IllegalStateException.class, () -> {
            memberService.join(secondMember);
        });

    }
}