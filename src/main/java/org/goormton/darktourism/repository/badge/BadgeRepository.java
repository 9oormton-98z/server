package org.goormton.darktourism.repository.badge;

import org.goormton.darktourism.domain.badge.Badge;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BadgeRepository extends JpaRepository<Badge, Long> {

    Optional<Badge> findBadgeByName(String name);

    Optional<Badge> findBadgeByOrderNum(int orderNum);
}
