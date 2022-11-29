package org.goormton.darktourism.controller.admin;

import lombok.RequiredArgsConstructor;
import org.goormton.darktourism.controller.admin.dto.AdminTestDto;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
@RequiredArgsConstructor
public class AdminControllerImpl implements AdminController {

    @Override
    public ResponseEntity test(AdminTestDto adminTestDto) {
        return ResponseEntity.ok(adminTestDto);
    }
    
    @Override
    public ResponseEntity test2(@RequestBody AdminTestDto adminTestDto) {
        return ResponseEntity.ok(adminTestDto);
    }
    
}
