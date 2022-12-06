package org.goormton.darktourism.controller.place

import lombok.extern.slf4j.Slf4j
import org.goormton.darktourism.controller.place.mapper.SimplePlaceDtoMapper
import org.goormton.darktourism.service.place.PlaceService
import org.goormton.darktourism.util.toResponseEntity
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RestController

@Slf4j
@RestController
class AdminPlaceControllerImpl(
    private val placeService: PlaceService,
    private val simplePlaceDtoMapper: SimplePlaceDtoMapper
) : AdminPlaceController {
    
    override fun allPlaceList(): ResponseEntity<*> {
        return placeService.findPlaceAll().map {
            simplePlaceDtoMapper.entityToDto(it, true)
        }.toResponseEntity()
    }
}