package org.goormton.darktourism.controller.admin;

import org.goormton.darktourism.controller.admin.dto.AdminTestDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/api/v1/admin")
public interface AdminController {
    
    @PostMapping("/test")
    ResponseEntity test(@RequestBody AdminTestDto adminTestDto);

    @PostMapping("/test2")
    ResponseEntity test2(@RequestBody AdminTestDto adminTestDto);
}
