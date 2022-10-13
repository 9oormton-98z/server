package org.goormton.darktourism.service.place;

import org.goormton.darktourism.domain.Member;
import org.goormton.darktourism.domain.Place;

import java.util.List;
import java.util.Set;

public interface PlaceService {

    List<Place> findPlaceAll();

    Place findPlaceById(Long id);

    Place findPlaceByName(String name);

    Set<Place> findPlaceByMember(Member member);

    Place visitPlace(Member member, Place place);    
    
}