package org.goormton.darktourism.repository.badge;

import org.goormton.darktourism.domain.badge.BadgeMember;
import org.goormton.darktourism.domain.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BadgeMemberRepository extends JpaRepository<BadgeMember, Long> {
    List<BadgeMember> findBadgeMemberByMember(Member member);

}
