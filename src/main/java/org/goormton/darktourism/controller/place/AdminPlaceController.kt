package org.goormton.darktourism.controller.place

import org.springframework.http.MediaType.APPLICATION_JSON_VALUE
import org.springframework.http.MediaType.MULTIPART_FORM_DATA_VALUE
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestPart
import org.springframework.web.multipart.MultipartFile

@RequestMapping("/api/v1/admin/place")
interface AdminPlaceController {
    /**
     * 관리자 페이지 -> 유적지 리스트 선택 시 보이는 데이터 반환
     */
    @GetMapping(value = [""])
    fun allPlaceList(): ResponseEntity<*>?

    /**
     * 관리자 페이지 -> 유적지 추가 기능
     */
    @PostMapping(value = [""], consumes = [APPLICATION_JSON_VALUE, MULTIPART_FORM_DATA_VALUE, ])
    fun createOnePlace(
        @RequestPart("data") createPlaceRequestString: String,
        @RequestPart("file") files: List<MultipartFile>
    ): ResponseEntity<*>?
    
}