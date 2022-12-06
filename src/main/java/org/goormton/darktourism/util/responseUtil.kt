package org.goormton.darktourism.util

import org.springframework.http.ResponseEntity

fun <T: Any> T.toResponseEntity(): ResponseEntity<T> = ResponseEntity.ok(this)