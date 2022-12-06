package org.goormton.darktourism.controller.place.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
public class CreatePlaceRequestDto {
    private String name;
    private String shortDescription;
    private String description;
    private Double latitude;
    private Double longitude;
    private String address;
    private String source;
    // TODO : Place entity 에 mainImage 번호 남기기
    private int mainImage;
    private List<MultipartFile> files;
}
