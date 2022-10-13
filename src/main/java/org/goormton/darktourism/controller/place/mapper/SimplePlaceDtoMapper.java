package org.goormton.darktourism.controller.place.mapper;

import org.goormton.darktourism.controller.place.dto.SimplePlaceDto;
import org.goormton.darktourism.domain.Place;
import org.springframework.stereotype.Component;

import static org.goormton.darktourism.asset.Default.NO_CONTENT_IMAGE_URL;

@Component
public class SimplePlaceDtoMapper {

    public SimplePlaceDto entityToDto(Place place, boolean check) {
        if (place == null) {
            return null;
        }

        return new SimplePlaceDto(
                place.getId(), 
                place.getName(),
                place.getShortDescription(),
                check ? place.getStampAfterImageUrl() : place.getStampPrevImageUrl(),
                place.getPlaceImageUrls().isEmpty() ?
                        NO_CONTENT_IMAGE_URL : place.getPlaceImageUrls().get(0).getImageUrl(),
                place.getLatitude(),
                place.getLongitude()
        );
    }
    
}
