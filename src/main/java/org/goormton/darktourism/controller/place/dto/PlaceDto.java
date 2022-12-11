package org.goormton.darktourism.controller.place.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.goormton.darktourism.domain.place.Place;

import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
public class PlaceDto {
    private Long placeId;
    private String name;
    private String description;
    private String shortDescription;
    private String stampImageUrl;
    private Double starSum;
    private int visitorNumber;
    private Double starAvg;
    private Double latitude;
    private Double longitude;
    private String address;
    private String source;
    private boolean isVisited;
    private List<PlaceImageUrlDto> placeImageUrlDtoList;

    public static PlaceDto entityToDto(Place place, boolean check) {
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
                        .map(PlaceImageUrlDto::entityToDto)
                        .collect(Collectors.toList())
        );
    }
}
