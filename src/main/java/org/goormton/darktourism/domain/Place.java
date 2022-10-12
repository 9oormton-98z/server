package org.goormton.darktourism.domain;

import io.micrometer.core.lang.Nullable;
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

    private String description = "";

    private Double starSum = 0.0;

    private int visitorNumber = 0;

    private Double starAvg = 0.0;

    private Double latitude;

    private Double longitude;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "place")
    private final List<PlaceStarMember> placeStarMembers = new ArrayList<>();

    private Place(String name, String description, Double latitude, Double longitude) {
        this.name = name;
        this.description = description;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public static Place createPlace(String name, String description, Double latitude, Double longitude) {
        return new Place(name, description, latitude, longitude);
    }
    
    public Place visitPlace(PlaceStarMember placeStarMember) {
        this.placeStarMembers.add(placeStarMember);
        this.visitorNumber += 1;
        this.starSum += placeStarMember.getStar();
        this.starAvg = starSum / (double) visitorNumber;
        return this;
    }
}
