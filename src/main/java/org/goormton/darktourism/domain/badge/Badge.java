package org.goormton.darktourism.domain.badge;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import java.util.ArrayList;
import java.util.List;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Badge {
    
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "badge_id")
    private Long id;
    private String name;
    private String description = "";
    private int orderNum;
    private String badgePrevImageUrl;
    private String badgeAfterImageUrl;

    @Builder
    private Badge(String name, String description, int orderNum, String badgePrevImageUrl, String badgeAfterImageUrl) {
        this.name = name;
        this.description = description;
        this.orderNum = orderNum;
        this.badgePrevImageUrl = badgePrevImageUrl;
        this.badgeAfterImageUrl = badgeAfterImageUrl;
    }

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "badge")
    private final List<BadgeMember> badgeMembers = new ArrayList<>();

    public void addBadgeMember(BadgeMember badgeMember) {
        this.badgeMembers.add(badgeMember);
    }
}
