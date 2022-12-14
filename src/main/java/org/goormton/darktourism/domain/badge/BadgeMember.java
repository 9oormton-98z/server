package org.goormton.darktourism.domain.badge;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.goormton.darktourism.domain.member.Member;

import javax.persistence.*;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BadgeMember {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "badge_number_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "badge_id")
    private Badge badge;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    private BadgeMember(Badge badge, Member member) {
        this.badge = badge;
        this.member = member;
    }

    public static BadgeMember createBadgeMember(Badge badge, Member member) {
        return new BadgeMember(badge, member);
    }
}
