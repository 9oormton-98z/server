package org.goormton.darktourism.service.badge;

import lombok.RequiredArgsConstructor;
import org.goormton.darktourism.domain.badge.Badge;
import org.goormton.darktourism.domain.badge.BadgeMember;
import org.goormton.darktourism.domain.member.Member;
import org.goormton.darktourism.repository.badge.BadgeMemberRepository;
import org.goormton.darktourism.repository.badge.BadgeRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BadgeServiceImpl implements BadgeService {

    private final BadgeRepository badgeRepository;

    private final BadgeMemberRepository badgeMemberRepository;
    
    @Override
    public List<Badge> findBadgeAll() {
        return badgeRepository.findAll();
    }

    @Override
    public Set<Badge> findBadgeByMember(Member member) {
        
        // TODO: Lazy get -> 최적화 필요함
        return badgeMemberRepository.findBadgeMemberByMember(member).stream()
                .map(BadgeMember::getBadge)
                .collect(Collectors.toSet());
    }

    @Override
    @Transactional
    public void earnNewBadge(Member member, int stampCnt) {
        int idx = stampCnt / 3;
        if (stampCnt % 3 != 0) {
            return;
        }
        Badge badge = badgeRepository.findBadgeByOrderNum(idx)
                .orElseThrow();
        BadgeMember badgeMember = BadgeMember.createBadgeMember(badge, member);
        badgeMemberRepository.save(badgeMember);
    }
}
