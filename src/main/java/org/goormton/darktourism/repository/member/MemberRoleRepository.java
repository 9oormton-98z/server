package org.goormton.darktourism.repository.member;

import org.goormton.darktourism.domain.member.MemberRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface MemberRoleRepository extends JpaRepository<MemberRole, Long> {
    
    @Transactional(readOnly = true)
    MemberRole findMemberRoleByRoleName(String roleName);
}
