package org.goormton.darktourism.controller.place.mapper;

import lombok.RequiredArgsConstructor;
import org.goormton.darktourism.controller.place.dto.PlaceDto;
import org.goormton.darktourism.domain.place.Place;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class PlaceToDtoMapper {

    private final PlaceImageUrlMapper placeImageUrlMapper;

    public PlaceDto entityToDto(Place place, boolean check) {
        if (place == null) {
            return null;
        }

        return new PlaceDto(
                place.getId(), 
                place.getName(),
                place.getDescription(),
                place.getShortDescription(),
                check ? place.getStampAfterImageUrl() : place.getStampPrevImageUrl(),
                place.getStarSum(),
                place.getVisitorNumber(),
                place.getStarAvg(),
                place.getLatitude(),
                place.getLongitude(),
                place.getAddress(),
                place.getSource(),
                check,
                place.getPlaceImageUrls().stream()
                        .map(placeImageUrlMapper::entityToDto)
                        .collect(Collectors.toList())
        );
    }
    
}
