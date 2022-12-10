package org.goormton.darktourism.controller.place.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CreatePlaceRequestDto {
    private String name;
    private String shortDescription;
    private String description;
    private Double latitude;
    private Double longitude;
    private String address;
    private String source;
}
