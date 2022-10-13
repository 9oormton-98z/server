package org.goormton.darktourism.service.place;

import lombok.RequiredArgsConstructor;
import org.goormton.darktourism.domain.Member;
import org.goormton.darktourism.domain.Place;
import org.goormton.darktourism.domain.PlaceStarMember;
import org.goormton.darktourism.exception.member.MemberNotFoundException;
import org.goormton.darktourism.exception.place.PlaceNotFoundException;
import org.goormton.darktourism.repository.MemberRepository;
import org.goormton.darktourism.repository.PlaceRepository;
import org.goormton.darktourism.repository.PlaceStarMemberRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PlaceServiceImpl implements PlaceService {

    private final PlaceRepository placeRepository;
    private final PlaceStarMemberRepository placeStarMemberRepository;
    private final MemberRepository memberRepository;

    @Override
    public List<Place> findPlaceAll() {
        return placeRepository.findAll();
    }

    @Override
    public Place findPlaceById(Long placeId) {
        return placeRepository.findById(placeId)
                .orElseThrow(PlaceNotFoundException::new);
    }

    @Override
    public Place findPlaceByName(String name) {
        return placeRepository.findPlaceByName(name)
                .orElseThrow(PlaceNotFoundException::new);
    }

    @Override
    public Set<Place> findPlaceByMember(Member member) {
        // TODO: Lazy get -> 최적화 필요함
        return placeStarMemberRepository.findPlaceStarMemberByMember(member).stream()
                .map(PlaceStarMember::getPlace)
                .collect(Collectors.toSet());
    }

    @Override
    @Transactional
    public Place visitPlace(Member member, Place place) {
        PlaceStarMember placeStarMember = 
                PlaceStarMember.createPlaceStarMember(place, member);
        placeStarMemberRepository.save(placeStarMember);
        return place.visitPlace(placeStarMember);
    }
}
