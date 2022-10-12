package org.goormton.darktourism.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PlaceStarMember {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    private Double star = 0.0;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "place_id")
    private Place place;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    private PlaceStarMember(Place place, Member member) {
        this.place = place;
        this.member = member;
    }

    public static PlaceStarMember createBadgeMember(Place place, Member member) {
        return new PlaceStarMember(place, member);
    }
}
