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
public class Member {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "member_id")
    private Long id;

    private String nickname;

    private Double point = 0.0;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "member")
    private final List<BadgeMember> badgeMembers = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "member")
    private final List<PlaceStarMember> placeStarMembers = new ArrayList<>();

    private Member(String nickname) {
        this.nickname = nickname;
    }

    public static Member createMember(String nickname) {
        return new Member(nickname);
    }

    public void addPoint(Double addPoint) {
        this.point += addPoint;
    }
    
    public void addBadgeMember(BadgeMember badgeMember) {
        this.badgeMembers.add(badgeMember);
    }

    public void addPlaceStarMember(PlaceStarMember placeStarMember) {
        this.placeStarMembers.add(placeStarMember);
    }
}

