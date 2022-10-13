package org.goormton.darktourism.repository;

import org.goormton.darktourism.domain.Badge;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.Option;
import java.util.Optional;

@Repository
public interface BadgeRepository extends JpaRepository<Badge, Long> {

    Optional<Badge> findBadgeByName(String name);

    Optional<Badge> findBadgeByOrderNum(int orderNum);
}
