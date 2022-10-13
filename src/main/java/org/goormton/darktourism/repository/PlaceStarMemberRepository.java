package org.goormton.darktourism.repository;

import org.goormton.darktourism.domain.Member;
import org.goormton.darktourism.domain.PlaceStarMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlaceStarMemberRepository extends JpaRepository<PlaceStarMember, Long> {

    List<PlaceStarMember> findPlaceStarMemberByMember(Member member); 
}
