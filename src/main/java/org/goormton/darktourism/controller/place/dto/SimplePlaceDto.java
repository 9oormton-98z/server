package org.goormton.darktourism.controller.place.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.goormton.darktourism.domain.place.Place;

import static org.goormton.darktourism.asset.Default.NO_CONTENT_IMAGE_URL;

@Data
@AllArgsConstructor
public class SimplePlaceDto {
    private Long placeId;
    private String name;
    private String shortDescription;
    private String stampImageUrl;
    private String placeImageUrl;
    private Double latitude;
    private Double longitude;

    public static SimplePlaceDto entityToDto(Place place, boolean check) {
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
