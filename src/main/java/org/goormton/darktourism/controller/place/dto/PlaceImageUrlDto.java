package org.goormton.darktourism.controller.place.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.goormton.darktourism.domain.place.PlaceImageUrl;

@Data
@AllArgsConstructor
public class PlaceImageUrlDto {
    private int orderNum;
    private String imageUrl;

    public static PlaceImageUrlDto entityToDto(PlaceImageUrl placeImageUrl) {
        if (placeImageUrl == null) {
            return null;
        }
        return new PlaceImageUrlDto(
                placeImageUrl.getOrderNum(),
                placeImageUrl.getImageUrl()
        );
    }
}
