package org.goormton.darktourism.controller.place.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

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
}
