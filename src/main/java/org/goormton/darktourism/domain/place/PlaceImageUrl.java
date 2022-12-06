package org.goormton.darktourism.domain.place;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PlaceImageUrl {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "place_Id")
    private Place place;

    private String imageUrl;

    private int orderNum;

    private PlaceImageUrl(Place place, String imageUrl, int orderNum) {
        this.place = place;
        this.imageUrl = imageUrl;
        this.orderNum = orderNum;
    }

    public static PlaceImageUrl createPlaceImageUrl(Place place, String imageUrl, int order) {
        return new PlaceImageUrl(place, imageUrl, order);
    }
}
