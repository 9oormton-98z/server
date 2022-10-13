package org.goormton.darktourism.controller.place;

import org.goormton.darktourism.controller.auth.dto.LoginDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RequestMapping("/api/v1/place")
public interface PlaceController {

    /**
     * 메인 지도 열면 보이는 장소 리스트 정보 전달
     */
    @GetMapping(value = "")
    ResponseEntity allPlaceList();

    /**
     * 장소 id를 통해 세부 정보 전달
     */
    @PostMapping("/{place_id}")
    ResponseEntity showOnePlaceDetail(
            @RequestBody @Valid LoginDto loginDto,
            @PathVariable("place_id") Long placeId, 
            HttpServletRequest request);

    /**
     * 방문 확인 신청
     */
    @PostMapping("/visit/{place_id}")
    ResponseEntity visitPlace(
            @RequestBody @Valid LoginDto loginDto,
            @PathVariable("place_id") Long placeId, 
            HttpServletRequest request);
    
}
