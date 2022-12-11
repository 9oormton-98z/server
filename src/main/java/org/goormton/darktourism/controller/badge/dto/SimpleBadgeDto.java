package org.goormton.darktourism.controller.badge.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.goormton.darktourism.domain.badge.Badge;

@Data
@AllArgsConstructor
public class SimpleBadgeDto {
    private Long badgeId;
    private String name;
    private String description;
    private String badgeImageUrl;

    public static SimpleBadgeDto entityToDto(Badge badge, boolean check) {
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
