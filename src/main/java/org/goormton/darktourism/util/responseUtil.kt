package org.goormton.darktourism.util

import org.springframework.http.ResponseEntity

fun <T: Any> T.toOKResponseEntity(): ResponseEntity<T> = ResponseEntity.ok(this)