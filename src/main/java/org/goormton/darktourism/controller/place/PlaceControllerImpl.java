package org.goormton.darktourism.controller.place;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.goormton.darktourism.controller.place.dto.SimplePlaceDto;
import org.goormton.darktourism.controller.place.dto.VisitPlaceDto;
import org.goormton.darktourism.controller.place.mapper.PlaceToDtoMapper;
import org.goormton.darktourism.controller.place.mapper.SimplePlaceDtoMapper;
import org.goormton.darktourism.domain.Member;
import org.goormton.darktourism.domain.Place;
import org.goormton.darktourism.service.member.MemberService;
import org.goormton.darktourism.service.place.PlaceService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequiredArgsConstructor
public class PlaceControllerImpl implements PlaceController {

    private final PlaceService placeService;
    private final MemberService memberService;
    private final SimplePlaceDtoMapper simplePlaceDtoMapper;
    private final PlaceToDtoMapper placeToDtoMapper;

    @Override
    public ResponseEntity allPlaceList() {
        List<SimplePlaceDto> allPlaceList = placeService.findPlaceAll().stream()
                .map(place -> simplePlaceDtoMapper.entityToDto(place, false))
                .collect(Collectors.toList());
        return ResponseEntity.ok(allPlaceList);
    }

    @Override
    public ResponseEntity showOnePlaceDetail(Long placeId, HttpServletRequest request) {
//        Arrays.stream(request.getCookies()).forEach(c ->
//                log.info(c.getName() + " : " + c.getValue())
//        );
        
//        String nicknameToken = request.getHeader("Authorization");
//        String nickname = nicknameToken.split(" ")[1];
//
//        log.info("Nickname header : " + nickname);
        
        final Place place = placeService.findPlaceById(placeId);
//        final Member member = memberService.findMemberByNickname("98즈");

        final Member member = memberService.findMemberBycookie(request.getCookies());
        List<Place> isInMyPlace = placeService.findPlaceByMember(member).stream()
                .filter(p -> p.getId().equals(placeId))
                .collect(Collectors.toList());

        return ResponseEntity.ok(
                placeToDtoMapper.entityToDto(place, !isInMyPlace.isEmpty()));
    }

    @Override
    public ResponseEntity visitPlace(Long placeId, HttpServletRequest request) {
        final Member member = memberService.findMemberBycookie(request.getCookies());
        final Place place = placeService.findPlaceById(placeId);

        placeService.visitPlace(member, place);
        
        return ResponseEntity.ok("VISIT SUCCESS");
    }
}
