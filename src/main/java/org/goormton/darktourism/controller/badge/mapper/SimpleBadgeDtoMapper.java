package org.goormton.darktourism.controller.badge.mapper;

import org.goormton.darktourism.controller.badge.dto.SimpleBadgeDto;
import org.goormton.darktourism.domain.badge.Badge;
import org.springframework.stereotype.Component;

@Component
public class SimpleBadgeDtoMapper {

    public SimpleBadgeDto entityToDto(Badge badge, boolean check) {
        if (badge == null) {
            return null;
        }

        return new SimpleBadgeDto(
                badge.getId(), 
                badge.getName(),
                badge.getDescription(),
                check ? badge.getBadgeAfterImageUrl() : badge.getBadgePrevImageUrl()
        );
    }
}
