package org.goormton.darktourism.domain;

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

    private Badge(String name, String description) {
        this.name = name;
        this.description = description;
    }

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "badge")
    private final List<BadgeMember> badgeMembers = new ArrayList<>();

    
    public static Badge createBadge(String name, String description) {
        return new Badge(name, description);
    }

    public void addBadgeMember(BadgeMember badgeMember) {
        this.badgeMembers.add(badgeMember);
    }
}
