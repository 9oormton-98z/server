package org.goormton.darktourism.controller.member;

import lombok.RequiredArgsConstructor;
import org.goormton.darktourism.controller.auth.dto.SimpleLoginDto;
import org.goormton.darktourism.controller.badge.dto.SimpleBadgeDto;
import org.goormton.darktourism.controller.member.dto.MemberInfoAll;
import org.goormton.darktourism.controller.place.dto.SimplePlaceDto;
import org.goormton.darktourism.domain.badge.Badge;
import org.goormton.darktourism.domain.member.Member;
import org.goormton.darktourism.domain.place.Place;
import org.goormton.darktourism.service.badge.BadgeService;
import org.goormton.darktourism.service.member.MemberService;
import org.goormton.darktourism.service.place.PlaceService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class MemberControllerImpl implements MemberController {
    
    private final MemberService memberService;

    private final PlaceService placeService;

    private final BadgeService badgeService;

    @Override
    public ResponseEntity showMemberPage(SimpleLoginDto simpleLoginDto, HttpServletRequest request) {
        
        final Member member = memberService.findMemberByNickname(simpleLoginDto.getNickname());

        final Set<Place> placeByMember = 
                placeService.findPlaceByMember(member);

        final Set<Badge> badgeByMember = 
                badgeService.findBadgeByMember(member);

        final List<SimplePlaceDto> stampList = 
                placeService.findPlaceAll().stream()
                        .map(place -> {
                            boolean contains = placeByMember.contains(place);
                            return SimplePlaceDto.entityToDto(place, contains);
                        })
                        .collect(Collectors.toList());

        final List<SimpleBadgeDto> badgeList = 
                badgeService.findBadgeAll().stream()
                        .map(badge -> {
                            boolean contains = badgeByMember.contains(badge);
                            return SimpleBadgeDto.entityToDto(badge, contains);
                        })
                        .collect(Collectors.toList());

        return ResponseEntity.ok(
                new MemberInfoAll(stampList, badgeList, placeByMember.size(), badgeByMember.size()));
    }
}
