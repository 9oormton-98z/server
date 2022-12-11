package org.goormton.darktourism.controller.place;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.goormton.darktourism.controller.auth.dto.SimpleLoginDto;
import org.goormton.darktourism.controller.place.dto.PlaceDto;
import org.goormton.darktourism.controller.place.dto.SimplePlaceDto;
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

@Slf4j
@RestController
@RequiredArgsConstructor
public class PlaceControllerImpl implements PlaceController {

    private final PlaceService placeService;
    private final MemberService memberService;
    private final BadgeService badgeService;

    @Override
    public ResponseEntity allPlaceList(SimpleLoginDto simpleLoginDto) {
        log.info("nickname : " + simpleLoginDto.getNickname());
        final Member member = memberService.findMemberByNickname(simpleLoginDto.getNickname());

        final Set<Place> placeByMember =
                placeService.findPlaceByMember(member);

        final List<SimplePlaceDto> stampList =
                placeService.findPlaceAll().stream()
                        .map(p -> {
                            boolean contains = placeByMember.contains(p);
                            return SimplePlaceDto.entityToDto(p, contains);
                        })
                        .collect(Collectors.toList());
        
        return ResponseEntity.ok(stampList);
    }

    @Override
    public ResponseEntity showOnePlaceDetail(SimpleLoginDto simpleLoginDto, Long placeId, HttpServletRequest request) {

        final Place place = placeService.findPlaceById(placeId);
        final Member member = memberService.findMemberByNickname(simpleLoginDto.getNickname());
        
        List<Place> isInMyPlace = placeService.findPlaceByMember(member).stream()
                .filter(p -> p.getId().equals(placeId))
                .collect(Collectors.toList());

        return ResponseEntity.ok(
                PlaceDto.entityToDto(place, !isInMyPlace.isEmpty()));
    }

    @Override
    public ResponseEntity visitPlace(SimpleLoginDto simpleLoginDto, Long placeId, HttpServletRequest request) {
        log.info("nickname : " + simpleLoginDto.getNickname());
        final Member member = memberService.findMemberByNickname(simpleLoginDto.getNickname());
        final Place place = placeService.findPlaceById(placeId);
        placeService.visitPlace(member, place);

        Set<Place> placeByMember = placeService.findPlaceByMember(member);

        badgeService.earnNewBadge(member, placeByMember.size());

        return ResponseEntity.ok(place.getStampAfterImageUrl());
    }
}
