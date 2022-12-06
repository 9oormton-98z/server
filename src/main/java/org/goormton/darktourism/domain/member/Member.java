package org.goormton.darktourism.domain.member;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.goormton.darktourism.domain.badge.BadgeMember;
import org.goormton.darktourism.domain.place.PlaceStarMember;
import org.goormton.darktourism.security.auth.PrincipalDetails;

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

    private String username;

    private String password;
    
    private Double point = 0.0;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name="user_roles",
            joinColumns = {@JoinColumn(name = "member_id")},
            inverseJoinColumns = {@JoinColumn(name = "role_id")})
    private List<MemberRole> roles = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "member")
    private final List<BadgeMember> badgeMembers = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "member")
    private final List<PlaceStarMember> placeStarMembers = new ArrayList<>();

    private Member(String username, MemberRole role) {
        this.username = username;
        this.password = "1234";
        roles.add(role);
    }

    public static Member createMember(String nickname, MemberRole role) {
        return new Member(nickname, role);
    }

    public PrincipalDetails toPrincipalDetails() {
        return new PrincipalDetails(this);
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

