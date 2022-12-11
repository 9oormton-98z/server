package org.goormton.darktourism.domain.place;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.goormton.darktourism.controller.place.dto.CreatePlaceRequestDto;

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

    /**
     * 유적지 이름
     */
    private String name;

    /**
     * 간단한 유적지 소개
     */
    private String shortDescription = "";

    /**
     * 자세한 유적지 소개
     */
    @Column(columnDefinition = "LONGTEXT")
    private String description = "";

    /**
     * 스탬프 찍기 전 스탬프 이미지 주소
     */
    private String stampPrevImageUrl = "";

    /**
     * 스탬프 찍은 후 스탬프 이미지 주소 
     */
    private String stampAfterImageUrl = "";

    /**
     * 사람들이 남긴 별점 총 합
     */
    private Double starSum = 0.0;

    /**
     * 방문한 사람 수
     */
    private int visitorNumber = 0;

    /**
     * 평균 별점
     */
    private Double starAvg = 0.0;

    /**
     * 유적지 위도
     */
    private Double latitude;

    /**
     * 유적지 경도
     */
    private Double longitude;

    /**
     * 유적지 실제 주소
     */
    private String address;

    /**
     * 출처
     */
    private String source;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "place")
    private final List<PlaceStarMember> placeStarMembers = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "place")
    private final List<PlaceImageUrl> placeImageUrls = new ArrayList<>();

    @Builder(builderClassName = "defaultBuilder")
    public Place(
            String name, String shortDescription, String description, String stampPrevImageUrl, String stampAfterImageUrl, Double latitude, Double longitude, String address, String source) {
        System.out.println("name = " + name);
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

    @Builder(builderClassName = "dtoBuilderClass", builderMethodName = "dtoBuilder")
    public Place(CreatePlaceRequestDto request) {
        System.out.println("request = " + request);
        this.name = request.getName();
        this.shortDescription = request.getShortDescription();
        this.description = request.getDescription();
        this.latitude = request.getLatitude();
        this.longitude = request.getLongitude();
        this.address = request.getAddress();
        this.source = request.getSource();
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

    @Override
    public String toString() {
        return "Place{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", shortDescription='" + shortDescription + '\'' +
                ", description='" + description + '\'' +
                ", stampPrevImageUrl='" + stampPrevImageUrl + '\'' +
                ", stampAfterImageUrl='" + stampAfterImageUrl + '\'' +
                ", starSum=" + starSum +
                ", visitorNumber=" + visitorNumber +
                ", starAvg=" + starAvg +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", address='" + address + '\'' +
                ", source='" + source + '\'' +
                ", placeStarMembers=" + placeStarMembers +
                ", placeImageUrls=" + placeImageUrls +
                '}';
    }
}
