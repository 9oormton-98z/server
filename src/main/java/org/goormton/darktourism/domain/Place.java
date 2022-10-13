package org.goormton.darktourism.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import java.util.ArrayList;
import java.util.List;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Place {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "place_id")
    private Long id;

    private String name;
    private String shortDescription = "";
    @Column(columnDefinition = "LONGTEXT")
    private String description = "";
    private String stampPrevImageUrl;
    private String stampAfterImageUrl;
    private Double starSum = 0.0;
    private int visitorNumber = 0;
    private Double starAvg = 0.0;
    private Double latitude;
    private Double longitude;
    private String address;
    private String source;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "place")
    private final List<PlaceStarMember> placeStarMembers = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "place")
    private final List<PlaceImageUrl> placeImageUrls = new ArrayList<>();

    private Place(
            String name, String shortDescription, String description, String stampPrevImageUrl, String stampAfterImageUrl, Double latitude, Double longitude, String address, String source) {
        this.name = name;
        this.shortDescription = shortDescription;
        this.description = description;
        this.stampPrevImageUrl = stampPrevImageUrl;
        this.stampAfterImageUrl = stampAfterImageUrl;
        this.latitude = latitude;
        this.longitude = longitude;
        this.address = address;
        this.source = source;
    }

    public static Place createPlace(
            String name, String shortDescription, String description, String stampPrevImageUrl, String stampAfterImageUrl, Double latitude, Double longitude, String address, String source) {
        return new Place(name, shortDescription, description, stampPrevImageUrl, stampAfterImageUrl, latitude, longitude, address, source);
    }

    public Place addPlaceImageUrls(PlaceImageUrl placeImageUrl) {
        this.placeImageUrls.add(placeImageUrl);
        return this;
    }
    
    public Place visitPlace(PlaceStarMember placeStarMember) {
        this.placeStarMembers.add(placeStarMember);
        this.visitorNumber += 1;
        this.starSum += placeStarMember.getStar();
        this.starAvg = starSum / (double) visitorNumber;
        return this;
    }
}
