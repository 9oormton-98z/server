package org.goormton.darktourism.controller.place.mapper;

import org.goormton.darktourism.controller.place.dto.PlaceImageUrlDto;
import org.goormton.darktourism.domain.place.PlaceImageUrl;
import org.springframework.stereotype.Component;

@Component
public class PlaceImageUrlMapper {
    public PlaceImageUrlDto entityToDto(PlaceImageUrl placeImageUrl) {
        if (placeImageUrl == null) {
            return null;
        }
        return new PlaceImageUrlDto(
                placeImageUrl.getOrderNum(),
                placeImageUrl.getImageUrl()
        );
    }
}
