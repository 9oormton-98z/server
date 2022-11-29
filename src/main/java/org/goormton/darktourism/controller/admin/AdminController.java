package org.goormton.darktourism.controller.admin;

import org.goormton.darktourism.controller.admin.dto.AdminTestDto;
import org.goormton.darktourism.controller.place.dto.CreatePlaceRequestDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/api/v1/admin")
public interface AdminController {
    
    @PostMapping("/login")
    ResponseEntity adminLogin();
    
    @PostMapping("")
    ResponseEntity createNewPlace(@RequestBody CreatePlaceRequestDto createPlaceRequestDto);

    @GetMapping("/test")
    ResponseEntity test(@RequestBody AdminTestDto adminTestDto);

    @GetMapping("/test2")
    ResponseEntity test2(@RequestBody AdminTestDto adminTestDto);
}
