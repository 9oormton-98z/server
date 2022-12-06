package org.goormton.darktourism.controller.place

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping

@RequestMapping("/api/v1/admin/place")
interface AdminPlaceController {
    /**
     * 관리자 페이지 -> 유적지 리스트 선택 시 보이는 데이터 반환
     */
    @GetMapping(value = [""])
    fun allPlaceList(): ResponseEntity<*>?
}