package org.goormton.darktourism.repository;


import org.goormton.darktourism.domain.PlaceImageUrl;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlaceImageUrlRepository extends JpaRepository<PlaceImageUrl, Long> {
    
}
