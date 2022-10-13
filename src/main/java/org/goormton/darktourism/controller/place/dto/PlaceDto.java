package org.goormton.darktourism.controller.place.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

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
    private List<PlaceImageUrlDto> placeImageUrlDtoList;
}
