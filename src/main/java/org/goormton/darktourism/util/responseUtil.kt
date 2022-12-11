package org.goormton.darktourism.util

import org.springframework.http.HttpStatus.CREATED
import org.springframework.http.ResponseEntity

fun <T: Any> T.okResponseEntity(): ResponseEntity<T> = ResponseEntity.ok(this)
fun <T : Any> T.createdResponseEntity(): ResponseEntity<T> = ResponseEntity(CREATED)