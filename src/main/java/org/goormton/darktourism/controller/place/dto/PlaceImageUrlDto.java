package org.goormton.darktourism.controller.place.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PlaceImageUrlDto {
    private int orderNum;
    private String imageUrl;
}
