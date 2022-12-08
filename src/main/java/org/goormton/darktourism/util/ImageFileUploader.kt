package org.goormton.darktourism.util

import org.joda.time.DateTime
import org.joda.time.format.DateTimeFormat
import org.slf4j.Logger
import org.springframework.stereotype.Component
import org.springframework.web.multipart.MultipartFile
import java.io.File
import java.nio.file.Path
import java.nio.file.Paths
import java.util.*

@Component
class ImageFileUploader(
    
    private val PLACE_IMAGE_PATH: String = "./src/main/resources/static/placeimage/",
    private val DATE_FOLDER_PATTERN: String = "yyMMdd"
): FileUploader {

    val logger: Logger = logger(this)

    override fun createFilePath(file: MultipartFile): Path {
        val originalFilename = file.originalFilename
        logger.info("New File Name : $originalFilename")
        
        val uuid = UUID.randomUUID().toString()
        val saveFilePath: String = listOf(PLACE_IMAGE_PATH, getTodayString(), uuid + originalFilename)
            .joinToString(File.separator)
        
        logger.info("SaveFilePath : $saveFilePath")
        return Paths.get(saveFilePath)
    }

    private fun getTodayString(): String = DateTimeFormat.forPattern(DATE_FOLDER_PATTERN).print(DateTime())
}