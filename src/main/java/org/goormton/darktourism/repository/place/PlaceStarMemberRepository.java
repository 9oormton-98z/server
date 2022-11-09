package org.goormton.darktourism.repository.place;

import org.goormton.darktourism.domain.member.Member;
import org.goormton.darktourism.domain.place.Place;
import org.goormton.darktourism.domain.place.PlaceStarMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlaceStarMemberRepository extends JpaRepository<PlaceStarMember, Long> {

    List<PlaceStarMember> findPlaceStarMemberByMember(Member member);

    List<PlaceStarMember> findPlaceStarMemberByMemberAndPlace(Member member, Place place);
}
