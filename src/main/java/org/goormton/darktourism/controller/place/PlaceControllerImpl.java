package org.goormton.darktourism.controller.place;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.goormton.darktourism.controller.auth.dto.LoginDto;
import org.goormton.darktourism.controller.place.dto.SimplePlaceDto;
import org.goormton.darktourism.controller.place.dto.VisitPlaceDto;
import org.goormton.darktourism.controller.place.mapper.PlaceToDtoMapper;
import org.goormton.darktourism.controller.place.mapper.SimplePlaceDtoMapper;
import org.goormton.darktourism.domain.Member;
import org.goormton.darktourism.domain.Place;
import org.goormton.darktourism.service.badge.BadgeService;
import org.goormton.darktourism.service.member.MemberService;
import org.goormton.darktourism.service.place.PlaceService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.Arrays;
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
    private final SimplePlaceDtoMapper simplePlaceDtoMapper;
    private final PlaceToDtoMapper placeToDtoMapper;

    @Override
    public ResponseEntity allPlaceList(LoginDto loginDto) {
        log.info("nickname : " + loginDto.getNickname());
        final Member member = memberService.findMemberByNickname(loginDto.getNickname());

        final Set<Place> placeByMember =
                placeService.findPlaceByMember(member);

        final List<SimplePlaceDto> stampList =
                placeService.findPlaceAll().stream()
                        .map(p -> {
                            boolean contains = placeByMember.contains(p);
                            return simplePlaceDtoMapper.entityToDto(p, contains);
                        })
                        .collect(Collectors.toList());
        
        return ResponseEntity.ok(stampList);
    }

    @Override
    public ResponseEntity showOnePlaceDetail(LoginDto loginDto, Long placeId, HttpServletRequest request) {

        final Place place = placeService.findPlaceById(placeId);
        final Member member = memberService.findMemberByNickname(loginDto.getNickname());
        
        List<Place> isInMyPlace = placeService.findPlaceByMember(member).stream()
                .filter(p -> p.getId().equals(placeId))
                .collect(Collectors.toList());

        return ResponseEntity.ok(
                placeToDtoMapper.entityToDto(place, !isInMyPlace.isEmpty()));
    }

    @Override
    public ResponseEntity visitPlace(LoginDto loginDto, Long placeId, HttpServletRequest request) {
        log.info("nickname : " + loginDto.getNickname());
        final Member member = memberService.findMemberByNickname(loginDto.getNickname());
        final Place place = placeService.findPlaceById(placeId);
        placeService.visitPlace(member, place);

        Set<Place> placeByMember = placeService.findPlaceByMember(member);

        badgeService.earnNewBadge(member, placeByMember.size());

        return ResponseEntity.ok(place.getStampAfterImageUrl());
    }
}
