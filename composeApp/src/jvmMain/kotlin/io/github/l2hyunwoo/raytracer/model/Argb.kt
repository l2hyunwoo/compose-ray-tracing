package io.github.l2hyunwoo.raytracer.model

data class Argb(
    val a: Int,
    val r: Int,
    val g: Int,
    val b: Int
) {
    fun toInt() = (a shl 24) or (r shl 16) or (g shl 8) or b
    operator fun times(multiplier: Float): Argb {
        return copy(
            a = a,
            r = (this.r * multiplier).coerceIn(0.0f, 255.0f).toInt(),
            g = (this.g * multiplier).coerceIn(0.0f, 255.0f).toInt(),
            b = (this.b * multiplier).coerceIn(0.0f, 255.0f).toInt()
        )
    }

    companion object {
        @JvmStatic
        fun from(argb: Int) = Argb(
            a = (argb shr 24) and 0xFF,
            r = (argb shr 16) and 0xFF,
            g = (argb shr 8) and 0xFF,
            b = argb and 0xFF
        )
    }
}
