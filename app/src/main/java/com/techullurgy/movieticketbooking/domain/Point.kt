package com.techullurgy.movieticketbooking.domain

@JvmInline
value class Point private constructor (private val position: String) {
    init {
        if(position.split("|").size != 2) throw IllegalArgumentException()
    }

    companion object {
        operator fun invoke(row: Int, col: Int): Point = Point("$row|$col")
    }

    val row: Int get() = position.split("|")[0].toInt()
    val col: Int get() = position.split("|")[1].toInt()
}

val Point.Companion.Unspecified get() = Point(-1, -1)

val Point.isSpecified get() = !(this.row == -1 || this.col == -1)
val Point.isUnspecified get() = !isSpecified
