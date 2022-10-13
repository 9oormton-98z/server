package org.goormton.darktourism.service.badge;

import lombok.RequiredArgsConstructor;
import org.goormton.darktourism.domain.Badge;
import org.goormton.darktourism.domain.BadgeMember;
import org.goormton.darktourism.domain.Member;
import org.goormton.darktourism.domain.PlaceStarMember;
import org.goormton.darktourism.repository.BadgeMemberRepository;
import org.goormton.darktourism.repository.BadgeRepository;
import org.springframework.stereotype.Service;

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
    public void earnNewBadge(Member member, String badgeName) {
        return;
//        Badge badge = badgeRepository.findBadgeByName(badgeName)
//                .orElseThrow();
//        BadgeMember.createBadgeMember(member, badge)
//        badgeMemberRepository.save(badge)
    }
}
