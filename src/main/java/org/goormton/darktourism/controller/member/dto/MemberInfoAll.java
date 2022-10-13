package org.goormton.darktourism.controller.member.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.goormton.darktourism.controller.badge.dto.SimpleBadgeDto;
import org.goormton.darktourism.controller.place.dto.SimplePlaceDto;

import java.util.List;

@Data
@AllArgsConstructor
public class MemberInfoAll {
    private List<SimplePlaceDto> stampList;
    private List<SimpleBadgeDto> badgeList;
}
