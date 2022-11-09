package org.goormton.darktourism.repository.member;

import org.goormton.darktourism.domain.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {

    @Transactional(readOnly = true)
    Optional<Member> findMemberByNickname(String nickname);
    
}
