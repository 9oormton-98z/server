package org.goormton.darktourism.service.badge;

import org.goormton.darktourism.domain.Badge;
import org.goormton.darktourism.domain.Member;

import java.util.List;
import java.util.Set;

public interface BadgeService {

    List<Badge> findBadgeAll();

    Set<Badge> findBadgeByMember(Member member);

    void earnNewBadge(Member member, int stampCnt);
}
