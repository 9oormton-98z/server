package org.goormton.darktourism.controller.admin;

import lombok.RequiredArgsConstructor;
import org.goormton.darktourism.controller.admin.dto.AdminTestDto;
import org.goormton.darktourism.controller.place.dto.CreatePlaceRequestDto;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
@RequiredArgsConstructor
public class AdminControllerImpl implements AdminController {
    
    @Override
    public ResponseEntity adminLogin() {
        String testName = "admin!";
        return ResponseEntity.ok(testName);
    }
    
    @Override
    @PostMapping("/place")
    public ResponseEntity createNewPlace(CreatePlaceRequestDto createPlaceRequestDto) {

        return null;
    }

    @Override
    @PostMapping("/test")
    public ResponseEntity test(AdminTestDto adminTestDto) {
        return ResponseEntity.ok(adminTestDto);
    }
    
    @Override
    @PostMapping("/test2")
    public ResponseEntity test2(@RequestBody AdminTestDto adminTestDto) {
        return ResponseEntity.ok(adminTestDto);
    }
    
}
