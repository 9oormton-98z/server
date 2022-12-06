package org.goormton.darktourism.domain.member;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;

import java.util.ArrayList;
import java.util.List;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "member_role")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberRole implements GrantedAuthority {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "role_id")
    private Long id;

    private String roleName;

    @ManyToMany
    private List<Member> members = new ArrayList<>();

    public MemberRole(String roleName) {
        this.roleName = roleName;
    }

    @Override
    public String getAuthority() {
        return roleName;
    }
}
