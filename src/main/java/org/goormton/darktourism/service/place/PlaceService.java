package org.goormton.darktourism.service.place;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.goormton.darktourism.controller.place.dto.CreatePlaceRequestDto;
import org.goormton.darktourism.domain.member.Member;
import org.goormton.darktourism.domain.place.Place;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Set;

public interface PlaceService {

    List<Place> findPlaceAll();

    Place findPlaceById(Long id);

    Place findPlaceByName(String name);

    Set<Place> findPlaceByMember(Member member);

    Place visitPlace(Member member, Place place);

    void createOnePlace(String request, List<MultipartFile> files) throws JsonProcessingException;
    
}
