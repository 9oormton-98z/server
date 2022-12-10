package org.goormton.darktourism.service.place;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.goormton.darktourism.controller.place.dto.CreatePlaceRequestDto;
import org.goormton.darktourism.domain.member.Member;
import org.goormton.darktourism.domain.place.Place;
import org.goormton.darktourism.domain.place.PlaceImageUrl;
import org.goormton.darktourism.domain.place.PlaceStarMember;
import org.goormton.darktourism.exception.badge.PlaceAlreadyVisitedException;
import org.goormton.darktourism.exception.place.PlaceNotFoundException;
import org.goormton.darktourism.repository.place.PlaceRepository;
import org.goormton.darktourism.repository.place.PlaceStarMemberRepository;
import org.goormton.darktourism.util.FileUploader;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class PlaceServiceImpl implements PlaceService {

    private final PlaceRepository placeRepository;
    private final PlaceStarMemberRepository placeStarMemberRepository;
    private final ObjectMapper objectMapper;
    private final FileUploader imageFileUploader;

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

    @Override
    @Transactional
    public void createOnePlace(String request, List<MultipartFile> files) throws JsonProcessingException {
        CreatePlaceRequestDto createPlaceRequestDto = objectMapper.readValue(request, CreatePlaceRequestDto.class);
        System.out.println("createPlaceRequestDto = " + createPlaceRequestDto);
        
        final List<String> filePathList = files.stream()
                .filter(f -> f.getSize() != 0)
                .map(imageFileUploader::upload)
                .collect(Collectors.toList());

        Place newPlace = Place.dtoBuilder()
                .request(createPlaceRequestDto)
                .build();

        System.out.println("newPlace = " + newPlace);

        for(int i = 0; i < filePathList.size(); i++){
            PlaceImageUrl placeImageUrl = PlaceImageUrl.builder()
                    .place(newPlace)
                    .imageUrl(filePathList.get(i))
                    .orderNum(i)
                    .build();
            newPlace.addPlaceImageUrls(placeImageUrl);
        }

        System.out.println("newPlace2 = " + newPlace);

        placeRepository.save(newPlace);
    }
}
