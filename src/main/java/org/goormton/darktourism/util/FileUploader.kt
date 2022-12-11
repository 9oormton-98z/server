package org.goormton.darktourism.util

import lombok.extern.slf4j.Slf4j
import org.springframework.web.multipart.MultipartFile
import java.nio.file.Path

@Slf4j
interface FileUploader {
    
    /**
     * MultipartFile을 업로드 후 파일별로 저장된 위치를 반환한다.
     */
    fun upload(file: MultipartFile): String {
        val savePath = createFilePath(file)
        try {
            file.transferTo(savePath)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return savePath.toString()
    }

    /**
     * File이 저장될 경로를 설정한다.
     * 파일 확장자에 맞춰 인터페이스를 구현한다.
     */
    fun createFilePath(file: MultipartFile): Path
}