package org.goormton.darktourism.repository.place;


import org.goormton.darktourism.domain.place.PlaceImageUrl;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlaceImageUrlRepository extends JpaRepository<PlaceImageUrl, Long> {
    
}
