package org.goormton.darktourism.util

@FunctionalInterface
fun interface Validator<T> {
    fun validate(t: T): Unit
}