package org.goormton.darktourism.util

import org.goormton.darktourism.exception.file.FileContentTypeNotAllowedException
import org.goormton.darktourism.util.ImageFileUploader.ImageValidator.Companion.validate
import org.joda.time.DateTime
import org.joda.time.format.DateTimeFormat
import org.slf4j.Logger
import org.springframework.stereotype.Component
import org.springframework.web.multipart.MultipartFile
import java.io.File
import java.nio.file.Path
import java.nio.file.Paths
import java.util.*

const val IMAGE_CONTENT_TYPE: String = "image"

@Component
class ImageFileUploader(
    private val PLACE_IMAGE_PATH: String = "./src/main/resources/static/placeimage/",
    private val DATE_FOLDER_PATTERN: String = "yyMMdd",
    private val logger: Logger = logger<ImageFileUploader>(),
): FileUploader {
    
    override fun createFilePath(file: MultipartFile): Path {
        printFileInformation(file)
        validate(file)
        
        val originalFilename = file.originalFilename
        val uuid = UUID.randomUUID().toString()
        val saveFilePath: String = listOf(PLACE_IMAGE_PATH, getTodayString(), uuid + originalFilename)
            .joinToString(File.separator)
        
        return Paths.get(saveFilePath)
    }
    
    private fun getTodayString(): String = DateTimeFormat.forPattern(DATE_FOLDER_PATTERN).print(DateTime())

    private fun printFileInformation(file: MultipartFile) {
        logger.info("File Name : ${file.originalFilename}")
        logger.info("Content-Type : ${file.contentType}")
        logger.info("Bytes : ${file.bytes}")
    }

    enum class ImageValidator (
        private val validator: Validator<MultipartFile>
    ) {
        CONTENT_TYPE_VALIDATOR({
            if (it.contentType.startsWith(IMAGE_CONTENT_TYPE)) {
                throw FileContentTypeNotAllowedException()
            }
        }),
        ;

        companion object {
            @JvmStatic
            fun validate(file: MultipartFile) = values().forEach { v -> v.validator.validate(file) }
        }
    }
}