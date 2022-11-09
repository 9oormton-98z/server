package org.goormton.darktourism.service.place;

import lombok.RequiredArgsConstructor;
import org.goormton.darktourism.domain.member.Member;
import org.goormton.darktourism.domain.place.Place;
import org.goormton.darktourism.domain.place.PlaceStarMember;
import org.goormton.darktourism.exception.badge.PlaceAlreadyVisitedException;
import org.goormton.darktourism.exception.place.PlaceNotFoundException;
import org.goormton.darktourism.repository.place.PlaceRepository;
import org.goormton.darktourism.repository.place.PlaceStarMemberRepository;
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
        List<PlaceStarMember> visitList = 
                placeStarMemberRepository.findPlaceStarMemberByMemberAndPlace(member, place);

        if (!visitList.isEmpty()) {
            throw new PlaceAlreadyVisitedException();
        }

        PlaceStarMember placeStarMember = placeStarMemberRepository.save(
                PlaceStarMember.createPlaceStarMember(place, member)
        );
        return place.visitPlace(placeStarMember);
    }
}
