package me.right42.jpastudy.shop.service;

import lombok.RequiredArgsConstructor;
import me.right42.jpastudy.shop.domain.Member;
import me.right42.jpastudy.shop.repository.MemberRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    @Transactional
    public Long join(Member member) {
        validatedDuplicateMember(member); // 중복 회원검증
        memberRepository.save(member);
        return member.getId();
    }

    public List<Member> findMembers(){
        return memberRepository.findAll();
    }

    public Member findOne(Long id){
        return memberRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    private void validatedDuplicateMember(Member member) {
        List<Member> findMembers = memberRepository.findByName(member.getName());
        if (!findMembers.isEmpty()) {
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }

    @Transactional
    public void update(Long id, String name) {
        Member findMember = memberRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        findMember.setName(name);
    }
}
