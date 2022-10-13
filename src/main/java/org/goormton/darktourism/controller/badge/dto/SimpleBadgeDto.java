package org.goormton.darktourism.controller.badge.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SimpleBadgeDto {
    private Long badgeId;
    private String name;
    private String description;
    private String badgeImageUrl;
}
